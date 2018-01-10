package com.rongdu.cashloan.core.common.util;

/**
 * 工具类-错误字符编码处理
 *
 * @author pantheon
 * @since 2017-06-15
 */
public final class ErrorStringUtil {

    /**
     * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案 😁
     * @param content 内容
     * @param replaceStr 被替换的字符
     * @return
     */
    public static String replaceErrorString(String content,String replaceStr) {
        byte[] conbyte = content.getBytes();
        for (int i = 0; i < conbyte.length; i++) {
            if ((conbyte[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    conbyte[i+j]=0x30;
                }
                i += 3;
            }
        }
        content = new String(conbyte);
        return content.replaceAll("0000", replaceStr);
    }

    /**
     * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F） 替换为 *
     * @param content
     * @return
     */
    public static String replaceErrorStringToStar(String content) {
        return replaceErrorString(content, "*");
    }

    public static void main(String[] args) {
        String str = "\uD83D\uDE01...测试字符串";
        System.out.println(ErrorStringUtil.replaceErrorStringToStar(str));
    }
}
