package com.rongdu.cashloan.cl.util;

import java.util.Random;

public class SeqUtil {

    public static String randomInvitationCode(int len) {
        while (true) {
            return randomNumAlph(len);
        }
    }

    private static String randomNumAlph(int len) {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        byte[][] list = {
                {48, 57},
                {97, 122},
                {65, 90}
        };
        for (int i = 0; i < len; i++) {
            byte[] o = list[random.nextInt(list.length)];
            byte value = (byte) (random.nextInt(o[1] - o[0] + 1) + o[0]);
            sb.append((char) value);
        }
        return sb.toString();
    }
}
