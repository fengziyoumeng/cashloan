package com.rongdu.cashloan.manage.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;

public class SftpUtils {

    private static final Logger LOG = Logger.getLogger(SftpUtils.class);

    public static void main(String[] args) {
        listFileNames("fca-vm-rds-prod1.xxx.org", 22, "applog", "xxx","","");
    }

    public static List<String> listFileNames(String host, int port, String username, final String password,final String localFile,final String remoteDir) {
        List<String> list = new ArrayList<String>();
        ChannelSftp sftp = null;
        Channel channel = null;
        Session sshSession = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            LOG.info("Session connected!");
            channel = sshSession.openChannel("sftp");
            channel.connect();
            LOG.info("Channel connected!");
            sftp = (ChannelSftp) channel;
            LOG.info(String.format("上传本地文件为【%s】,远程目录为【%s】",localFile,remoteDir));
            sftp.put(localFile,remoteDir);//上传本地文件到远程指定目录
            LOG.info(String.format("【%s】上传成功",localFile));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeChannel(sftp);
            closeChannel(channel);
            closeSession(sshSession);
        }
        return list;
    }

    private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    private static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
