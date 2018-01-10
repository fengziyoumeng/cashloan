package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.PageHelper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rongdu.cashloan.cl.domain.Channel;
import com.rongdu.cashloan.cl.domain.ClFlowUV;
import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.cl.service.ChannelService;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.cl.service.IClickTrackService;
import com.rongdu.cashloan.cl.threads.SingleThreadPool;
import com.rongdu.cashloan.cl.util.DateTools;
import com.rongdu.cashloan.cl.util.FileUtil;
import com.rongdu.cashloan.cl.util.SeqUtil;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.core.service.CloanUserService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Scope("prototype")
@Controller
public class MakeExcelController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(MakeExcelController.class);

    private static String fileName = "";

    @Resource
    private CloanUserService cloanUserService;

    @Resource
    private ChannelService channelService;

    @Resource
    private ClFlowUVService clFlowUVService;

    @Autowired
    private ShardedJedisClient redisClient;

    @Resource
    private IClickTrackService clickTrackService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取渠道注册数量--pdf
     * @throws Exception
     */
    @RequestMapping(value = "/act/count/pdf/RegisterPlat.htm")
    public String getInfoManagePdf(HttpServletRequest httpServletRequest) throws Exception {
        String dest = httpServletRequest.getSession().getServletContext().getRealPath("");
        String filePath = dest+"\\inputFiles\\"+ SeqUtil.randomInvitationCode(6)+".pdf";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        BaseFont baseFont = BaseFont.createFont( "c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.EMBEDDED);
        Font font = new Font(baseFont);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        PdfPTable table = new PdfPTable(15);
        String sTrDate = "2017-12-15";
        String eTrDate = "2017-12-20";
        List<String> lsr = DateTools.getSdateToEdate(sTrDate,eTrDate);
        //添加表头
        table.addCell(new Paragraph("渠道名称",font));
        for(String str:lsr){
            table.addCell(new Paragraph(str,font));
        }

        List<Channel> channelList = channelService.listChannel();
        Map<String,Object> paraMap = null;
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        if(channelList!=null && channelList.size()>0){
            for(Channel channel:channelList){
                logger.info(String.format("渠道id【%s】",channel.getId()));
                paraMap = new HashMap<String,Object>();
                paraMap.put("id",channel.getId());
                paraMap.put("name",channel.getName());
                for(String str:lsr){
                    paraMap.put("time",str);
                    long number = cloanUserService.queryNumberPlat(paraMap);
                    paraMap.put(str,number);
                }
                mapList.add(paraMap);
            }
        }
        logger.info(String.format("mapList【%s】",mapList.toString()));
        //输出数据
        for(Map map:mapList){
            table.addCell((String) map.get("name"));
            for(String str:lsr){
                table.addCell(String.valueOf(map.get(str)));
            }
        }
        document.add(table);
        document.close();
        return file.getAbsolutePath();
    }

    /**
     * 获取渠道注册数量--excel
     * @throws Exception
     * 先查出所有的渠道，然后循环渠道id和日期查出那一天的注册数，所有日期当做属性，注册数当做值放在map对象中，最后得到map对象的集合
     */
    @RequestMapping(value = "/act/count/excel/registerPlat.htm")
    public void getChannelInfoExcel(HttpServletRequest request,HttpServletResponse response,String beginTime,String endTime) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        if(StringUtil.isBlank(beginTime)||StringUtil.isBlank(endTime)){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "起始时间或结束时间都不能为空，请输入后重试！");
            ServletUtils.writeToResponse(response,result);
            logger.info("registerPlat：起始时间或结束时间都不能为空，请输入后重试！");
            return;
        }
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("渠道注册数");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0); //表头第一列
        cell.setCellValue("渠道名称"); //表头第一列名称

        String sTrDate = beginTime;
        String eTrDate = endTime;
        List<String> lsr = DateTools.getSdateToEdate(sTrDate,eTrDate);
        int a = 0;
        for(String str:lsr){
            a++;
            cell = row.createCell(a); //表头第a列
            cell.setCellValue(str); ////表头第a列名称
        }

        List<Channel> channelList = channelService.listChannel();
        Map<String,Object> paraMap = null;
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        if(channelList!=null && channelList.size()>0){
            for(Channel channel:channelList){
                logger.info(String.format("渠道id【%s】",channel.getId()));
                paraMap = new HashMap<String,Object>();
                paraMap.put("id",channel.getId());
                paraMap.put("name",channel.getName());
                for(String str:lsr){
                    paraMap.put("time",str);
                    Long number = cloanUserService.queryNumberPlat(paraMap)==null?0:cloanUserService.queryNumberPlat(paraMap);
                    paraMap.put(str,number);
                }
                mapList.add(paraMap);
            }
        }
        logger.info(String.format("mapList【%s】",mapList.toString()));

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < mapList.size(); i++) {
            HSSFRow hrow = sheet.createRow(i + 1);
            Map map = mapList.get(i);
            //创建单元格设值
            int b = 0;
            hrow.createCell(0).setCellValue(map.get("name").toString()); //单元格第一列的值
            for(String str:lsr){
                b++;
                hrow.createCell(b).setCellValue(map.get(str).toString()); //单元格第b列的值
            }
        }

        //将文件保存到指定的位置
        try {
            String filePath = request.getSession().getServletContext().getRealPath("excel");// 获取真实路径
            String fileName = DateUtil.getNowDate()+"_channel.xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");//对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
            String targetFile = filePath+System.getProperty("file.separator")+ fileName;
            FileOutputStream fos = new FileOutputStream(targetFile);
            workbook.write(fos);
            logger.info(fileName+"写入成功");
            fos.close();

            //下载
            FileUtil.downloadByte(targetFile,fileName,response);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "生成EXCEL成功");
            ServletUtils.writeToResponse(response,result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/act/count/excel/flowUvInfo.htm")
    public void getFlowInfoExcel(HttpServletRequest request,HttpServletResponse response,String beginTime,String endTime) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        if(StringUtil.isBlank(beginTime)||StringUtil.isBlank(endTime)){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "起始时间或结束时间都不能为空，请输入后重试！");
            ServletUtils.writeToResponse(response,result);
            logger.info("flowUvInfo：起始时间或结束时间都不能为空，请输入后重试！");
            return;
        }
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("流量平台产品");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0); //表头第一列
        cell.setCellValue("产品编码"); //表头第一列名称
        cell = row.createCell(1); //表头第二列
        cell.setCellValue("产品名称"); ////表头第二列名称

        String sTrDate = beginTime;
        String eTrDate = endTime;
        List<String> lsr = DateTools.getSdateToEdate(sTrDate,eTrDate);
        int a = 1;
        for(String str:lsr){
            a++;
            cell = row.createCell(a); //表头第a列
            cell.setCellValue(str); ////表头第a列名称
        }

        List<ClFlowUV> clFlowUVList = clFlowUVService.listFlowUv();
        Map<String,Object> paraMap = null;
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        if(clFlowUVList!=null && clFlowUVList.size()>0){
            for(ClFlowUV clFlowUV:clFlowUVList){
                logger.info(String.format("产品编码【%s】",clFlowUV.getPCode()));
                paraMap = new HashMap<String,Object>();
                paraMap.put("pCode",clFlowUV.getPCode());
                paraMap.put("name",clFlowUV.getpName());
                for(String str:lsr){
                    String numString = redisClient.get(clFlowUV.getPCode()+":excel_uv:"+str);
                    if(StringUtil.isNotBlank(numString)){
                        logger.info(clFlowUV.getPCode()+":excel_uv:"+str+"_numString："+numString+"=======");
                        paraMap.put(str,Integer.valueOf(numString));
                    }else{
                        paraMap.put("time",str);
                        Integer number = clFlowUVService.queryNumberPlat(paraMap);
                        int num = number==null ? 0 : number;
                        if(num!=0){
                            redisClient.set(clFlowUV.getPCode()+":excel_uv:"+str,String.valueOf(num));
                        }
                        paraMap.put(str,num);
                    }
                }
                mapList.add(paraMap);
            }
        }
        logger.info(String.format("mapList【%s】",mapList.toString()));

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < mapList.size(); i++) {
            HSSFRow hrow = sheet.createRow(i + 1);
            Map map = mapList.get(i);
            //创建单元格设值
            int b = 1;
            hrow.createCell(0).setCellValue(map.get("pCode").toString()); //单元格第一列的值
            hrow.createCell(1).setCellValue(map.get("name").toString()); //单元格第二列的值
            for(String str:lsr){
                b++;
                hrow.createCell(b).setCellValue(map.get(str).toString()); //单元格第b列的值
            }
        }

        //将文件保存到指定的位置
        try {
            String filePath = request.getSession().getServletContext().getRealPath("excel");// 获取真实路径
            String fileName = DateUtil.getNowDate()+"_flowinfo.xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");//对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
            String targetFile = filePath+System.getProperty("file.separator")+ fileName;
            FileOutputStream fos = new FileOutputStream(targetFile);
            workbook.write(fos);
            logger.info(fileName+"写入成功");
            fos.close();

            //下载
            FileUtil.downloadByte(targetFile,fileName,response);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "生成EXCEL成功");
            ServletUtils.writeToResponse(response,result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/act/count/excel/track.htm")
    public void getTrackExcel( HttpServletRequest request, HttpServletResponse response, String beginTime, String endTime,String userId,String channelName) throws Exception {
        logger.info("开始导出EXCEL...");
        Map<String,Object> result = new HashMap<String,Object>();
        if(StringUtil.isBlank(beginTime)||StringUtil.isBlank(endTime)){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "起始时间或结束时间都不能为空，请输入后重试！");
            ServletUtils.writeToResponse(response,result);
            logger.info("track：起始时间或结束时间都不能为空，请输入后重试！");
            return;
        }
        if("undefined".equals(userId)){
            userId = "";
        }
        if("undefined".equals(channelName)){
            channelName = "";
        }
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("用户浏览轨迹");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0); //表头第一列
        cell.setCellValue("用户编号"); //表头第一列名称
        cell = row.createCell(1); //表头第二列
        cell.setCellValue("访问页面"); //表头第二列名称
        cell = row.createCell(2); //表头第三列
        cell.setCellValue("访问时间"); //表头第三列名称
        cell = row.createCell(3); //表头第四列
        cell.setCellValue("渠道名称"); //表头第四列名称

        List<ClickTrack> clickTrackList = null;
        try {
            clickTrackList = clickTrackService.makeTrackExcel(beginTime,endTime,userId,channelName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < clickTrackList.size(); i++) {
            HSSFRow hrow = sheet.createRow(i + 1);
            ClickTrack clickTrack = clickTrackList.get(i);
            //创建单元格设值
            int b = 1;
            hrow.createCell(0).setCellValue(clickTrack.getUserId()); //单元格第一列的值
            hrow.createCell(1).setCellValue(clickTrack.getName()); //单元格第二列的值
            hrow.createCell(2).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clickTrack.getClickTime())); //单元格第三列的值
            hrow.createCell(3).setCellValue(clickTrack.getChannelName()); //单元格第四列的值
        }

        //将文件保存到指定的位置
        try {
            String filePath = request.getSession().getServletContext().getRealPath("excel");// 获取真实路径
            String fileName = DateUtil.getNowDate()+"_userTrack.xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");//对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
            String targetFile = filePath+System.getProperty("file.separator")+ fileName;
            FileOutputStream fos = new FileOutputStream(targetFile);
            workbook.write(fos);
            logger.info(fileName+"写入成功");
            fos.close();

            //下载
            FileUtil.downloadByte(targetFile,fileName,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "生成EXCEL成功");
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 获取渠道用户轨迹概况--excel
     * @throws Exception
     */
    @RequestMapping(value = "/act/count/excel/channel_survey.htm")
    public void getChannelSurveyExcel(HttpServletRequest request,HttpServletResponse response,String beginTime,String endTime) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        if(StringUtil.isBlank(beginTime)||StringUtil.isBlank(endTime)){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "起始时间或结束时间都不能为空，请输入后重试！");
            ServletUtils.writeToResponse(response,result);
            logger.info("channel_survey：起始时间或结束时间都不能为空，请输入后重试！");
            return;
        }
        if(!beginTime.equals(endTime)){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "起始时间必须等于结束时间，请输入后重试！");
            ServletUtils.writeToResponse(response,result);
            logger.info("channel_survey：起始时间必须等于结束时间，请输入后重试！");
            return;
        }
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("渠道用户每日浏览概况");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0); //表头第一列
        cell.setCellValue("渠道名称"); //表头第一列名称
        cell = row.createCell(1); //表头第二列
        cell.setCellValue("注册量"); //表头第二列名称
        cell = row.createCell(2); //表头第三列
        cell.setCellValue("总UV"); //表头第三列名称
        cell = row.createCell(3); //表头第四列
        cell.setCellValue("倍率（总UV/注册量）"); //表头第四列名称
        cell = row.createCell(4); //表头第五列
        cell.setCellValue("去重后UV"); //表头第五列名称
        cell = row.createCell(5); //表头第六列
        cell.setCellValue("去重率"); //表头第六列名称
        cell = row.createCell(6); //表头第七列
        cell.setCellValue("去重后倍率（%）"); //表头第七列名称
        cell = row.createCell(7); //表头第八列
        cell.setCellValue("去重后注册UV"); //表头第八列名称
        cell = row.createCell(8); //表头第九列
        cell.setCellValue("详情注册转化（%）"); //表头第九列名称
        cell = row.createCell(9); //表头第十列
        cell.setCellValue("最终转化（%）"); //表头第十列名称

        List<Map<String,Object>> mapList = (List<Map<String,Object>>)redisClient.getObject("channel_survey_list:"+beginTime);
        if(mapList==null){
            mapList = new ArrayList<Map<String,Object>>();
            List<Channel> channelList = channelService.listChannel();
            Map<String,Object> paraMap = null;
            if(channelList!=null && channelList.size()>0){
                for(Channel channel:channelList){
                    logger.info(String.format("渠道id【%s】",channel.getId()));
                    paraMap = new HashMap<String,Object>();
                    paraMap.put("id",channel.getId());
                    paraMap.put("name",channel.getName());//渠道名称
                    paraMap.put("time",beginTime);//日期
                    Long number = cloanUserService.queryNumberPlat(paraMap)==null?0:cloanUserService.queryNumberPlat(paraMap);
                    Map<String,Object> mm = cloanUserService.queryMaxMinUserId(paraMap);
                    if(mm==null){
                        paraMap.put("totalUV",0);
                        paraMap.put("duplicateUV",0);
                        paraMap.put("registerUV",0);
                        paraMap.put("bl",0);
                        paraMap.put("qcl",0+"%");
                        paraMap.put("qch_bl",0);
                        paraMap.put("xq_zc",0+"%");
                        paraMap.put("zc_zh",0+"%");
                    }else{
                        paraMap.put("sUserId",(Long)mm.get("sUserId"));
                        paraMap.put("eUserId",(Long)mm.get("eUserId"));
                    }
                    paraMap.put("zcl",number);//每日注册量
                    paraMap = clickTrackService.channelSurvey(paraMap);
                    mapList.add(paraMap);
                }
            }
            redisClient.setObject("channel_survey_list:"+beginTime,mapList);
            logger.info("channel_survey_list:"+beginTime+":设置缓存成功");
        }else{
            logger.info("channel_survey_list:"+beginTime+":缓存中有数据");
        }
        logger.info(String.format("mapList【%s】",mapList.toString()));

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < mapList.size(); i++) {
            HSSFRow hrow = sheet.createRow(i + 1);
            Map map = mapList.get(i);
            //创建单元格设值
            hrow.createCell(0).setCellValue(map.get("name").toString()); //单元格第一列的值 渠道名称
            hrow.createCell(1).setCellValue(map.get("zcl").toString()); //单元格第二列的值 注册量
            hrow.createCell(2).setCellValue(map.get("totalUV").toString()); //单元格第三列的值 总UV
            hrow.createCell(3).setCellValue(map.get("bl").toString()); //单元格第四列的值 倍率（总UV/注册量）
            hrow.createCell(4).setCellValue(map.get("duplicateUV").toString()); //单元格第五列的值 去重后UV
            hrow.createCell(5).setCellValue(map.get("qcl").toString()); //单元格第六列的值 去重率
            hrow.createCell(6).setCellValue(map.get("qch_bl").toString()); //单元格第七列的值 去重后倍率（%）
            hrow.createCell(7).setCellValue(map.get("registerUV").toString()); //单元格第八列的值 去重后注册UV
            hrow.createCell(8).setCellValue(map.get("xq_zc").toString()); //单元格第九列的值 详情注册转化（%）
            hrow.createCell(9).setCellValue(map.get("zc_zh").toString()); //单元格第十列的值 最终转化（%）
        }

        //将文件保存到指定的位置
        try {
            String filePath = request.getSession().getServletContext().getRealPath("excel");// 获取真实路径
            String fileName = beginTime+"_channel_survey.xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");//对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
            String targetFile = filePath+System.getProperty("file.separator")+ fileName;
            FileOutputStream fos = new FileOutputStream(targetFile);
            workbook.write(fos);
            logger.info(fileName+"写入成功");
            fos.close();

            //下载
            FileUtil.downloadByte(targetFile,fileName,response);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "生成EXCEL成功");
            ServletUtils.writeToResponse(response,result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
