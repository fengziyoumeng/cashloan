package com.rongdu.cashloan.cl.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * java to excel 导入appache的poi 3.15 jar 包
 */
public class MakeExcel{

    // 产生要储存的集合
    protected static List<Map> getUsers() {
        List<Map> users = new ArrayList<>();
        Map user1 = new HashMap();
        user1.put("a","admin");
        user1.put("b","admin");

        Map user2 = new HashMap();
        user2.put("a","admin111");
        user2.put("b","admin111");

        Map user3 = new HashMap();
        user3.put("a","admin222");
        user3.put("b","admin222");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }


    public static void main(String[] args) {
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("用户表一");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell = row.createCell(1);
        cell.setCellValue("密码");

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        List<Map> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            Map user = users.get(i);
            //创建单元格设值
            row1.createCell(0).setCellValue(user.get("a").toString());
            row1.createCell(1).setCellValue(user.get("b").toString());
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("E:\\user1.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
