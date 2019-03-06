//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine;

import java.io.*;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

public class Test {
    private static int a = 0;

    public Test() {
    }

    public static void main(String[] var0) {
        double var1 = 909267.0D;
        double var3 = 867372.0D;
        double var5 = 869111.0D;
        double var7 = 45.0D / (var1 - var3) * (var5 - var3) + 5.0D;
        System.out.print(var7);
    }

    public static void mergeCellsHorizontal(XWPFTable var0, int var1, int var2, int var3) {
        for(int var4 = var2; var4 <= var3; ++var4) {
            XWPFTableCell var5 = var0.getRow(var1).getCell(var4);
            if (var4 == var2) {
                var5.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                var5.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }

    }

    public static void mergeCellsVertically(XWPFTable var0, int var1, int var2, int var3) {
        for(int var4 = var2; var4 <= var3; ++var4) {
            XWPFTableCell var5 = var0.getRow(var4).getCell(var1);
            if (var4 == var2) {
                var5.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                var5.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }

    }

    public static boolean isChinese(char var0) {
        if (var0 >= 19968 && var0 <= 171941) {
            return true;
        } else if (var0 == 12289) {
            return true;
        } else {
            return var0 == 12290;
        }
    }

    public static int loopfile(File var0, StringBuffer var1) throws IOException {
        int var2 = 0;
        File[] var3 = var0.listFiles();
        File[] var7 = var3;
        int var6 = var3.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            File var4 = var7[var5];
            if (var4.isDirectory()) {
                var2 += loopfile(var4, var1);
            }

            if (var4.getName().endsWith(".jsp")) {
                var2 += computeCodeLineCount(var4, var1);
            }
        }

        return var2;
    }

    public static int computeCodeLineCount(File var0, StringBuffer var1) throws IOException {
        BufferedReader var2 = new BufferedReader(new FileReader(var0));
        String var3 = "";
        int var4 = 0;

        while((var3 = var2.readLine()) != null) {
            String var5 = var3.replaceAll("\t", "");
            if (var5.length() > 0) {
                var1.append(var3 + "\n");
                ++var4;
            }
        }

        ++a;
        return var4;
    }
}
