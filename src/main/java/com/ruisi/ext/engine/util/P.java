//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class P {
    private static final String[] a = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "-", "*", "/", "'", ":", ";", ">", "<", "~", "!", "@", "#", "$", "%", "^", "&", "(", ")", "{", "}", "[", "]", "|", "_", "=", ","};
    private static final String[] b = new String[]{"T", "l", "m", ">", "S", "&", "J", "G", "/", "!", "p", "h", "Z", "7", "q", "_", "U", "(", "0", "a", "X", "f", "^", "@", "D", "~", "M", "t", "O", "6", "9", "C", "N", "K", "H", "g", "w", "4", "F", "<", "e", "2", "R", "o", "$", "d", "V", "v", "]", "#", "[", "L", "r", "b", "Y", "{", "z", "k", "x", "n", "5", "B", "W", "-", "A", "P", "1", "j", "E", "u", "s", "'", "I", "c", "%", ".", "i", "y", "Q", "*", ")", "3", "8", "}", ";", ":", "|", "=", ","};

    public P() {
    }

    public static int pos(String var0, String[] var1) {
        int var2 = -1;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var0.equals(var1[var3])) {
                var2 = var3;
                break;
            }
        }

        return var2;
    }

    public static String encode(String var0) {
        String var1 = "";

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var3 = var0.charAt(var2);
            if (var3 != '\r' && var3 != '\n') {
                var1 = var1 + b[pos(String.valueOf(var3), a)];
            } else {
                var1 = var1 + String.valueOf(var3);
            }
        }

        return var1;
    }

    public static String decode(String var0) {
        String var1 = "";

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var3 = var0.charAt(var2);
            if (var3 != '\r' && var3 != '\n') {
                var1 = var1 + a[pos(String.valueOf(var3), b)];
            } else {
                var1 = var1 + String.valueOf(var3);
            }
        }

        return var1;
    }

    public static void createMapARR() {
        ArrayList var0 = new ArrayList();

        int var1;
        for(var1 = 0; var1 < a.length; ++var1) {
            var0.add(a[var1]);
        }

        for(var1 = 0; var1 < a.length; ++var1) {
            int var2 = a.length - 1 - var1;
            if (var2 == 0) {
                b[var1] = (String)var0.get(0);
            } else {
                int var3 = (new Random()).nextInt(var2);
                b[var1] = (String)var0.get(var3);
                var0.remove(var3);
            }
        }

        for(var1 = 0; var1 < b.length; ++var1) {
            System.out.print("\"" + b[var1] + "\",");
        }

    }

    public static String getLocalMac(InetAddress var0) {
        try {
            byte[] var1 = NetworkInterface.getByInetAddress(var0).getHardwareAddress();
            StringBuffer var2 = new StringBuffer("");

            for(int var3 = 0; var3 < var1.length; ++var3) {
                if (var3 != 0) {
                    var2.append("-");
                }

                int var4 = var1[var3] & 255;
                String var5 = Integer.toHexString(var4);
                if (var5.length() == 1) {
                    var2.append("0" + var5);
                } else {
                    var2.append(var5);
                }
            }

            return var2.toString().toUpperCase();
        } catch (Exception var6) {
            return null;
        }
    }

    public static String getMD5(byte[] var0) {
        String var1 = null;
        char[] var2 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest var3 = MessageDigest.getInstance("MD5");
            var3.update(var0);
            byte[] var4 = var3.digest();
            char[] var5 = new char[32];
            int var6 = 0;

            for(int var7 = 0; var7 < 16; ++var7) {
                byte var8 = var4[var7];
                var5[var6++] = var2[var8 >>> 4 & 15];
                var5[var6++] = var2[var8 & 15];
            }

            var1 = new String(var5);
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
        }

        return var1;
    }
}
