//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.util.P;
import com.ruisi.ext.engine.view.exception.AuthException;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Decoder;

class a {
    private static String a;
    private static String b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;

    private static void e() {
        if (a == null) {
            a = "user[1]cnt";
        } else {
            Pattern var0 = Pattern.compile("\\[([0-9]+)\\]");
            Matcher var1 = var0.matcher(a);
            if (var1.find()) {
                String var2 = var1.group(1);
                Integer var3 = Integer.parseInt(var2);
                var3 = var3 + 1;
                a = "user[" + var3 + "]";
            }
        }

    }

    private static void f() {
        if (a != null) {
            Pattern var0 = Pattern.compile("\\[([0-9]+)\\]");
            Matcher var1 = var0.matcher(a);
            if (var1.find()) {
                String var2 = var1.group(1);
                Integer var3 = Integer.parseInt(var2);
                var3 = var3 - 1;
                a = "user[" + var3 + "]cnt";
            }

        }
    }


    public static void main(String[] args) {
        //测试lic文件
        if (b == null || c == null) {
            String var1 = "E:\\2018\\rsbi\\src\\main\\webapp\\WEB-INF\\lic";
            File var2 = new File(var1);
            if (!var2.exists()) {
                var2 = new File(var1 + ".txt");
                if (!var2.exists()) {
                    //throw new AuthException("lic 文件不存在.");
                }
            }

            try {
                FileInputStream var3 = new FileInputStream(var2);
                String var4 = IOUtils.toString(var3, "UTF-8");
                var3.close();
                var4 = P.decode(var4);
                BASE64Decoder var5 = new BASE64Decoder();
                var4 = new String(var5.decodeBuffer(var4));
                String[] var6 = var4.split("\n");
                if (var6 == null || var6.length != 11) {
                    throw new AuthException();
                }

                b = P.decode(var6[3]);
                c = P.decode(var6[4]);
                d = P.decode(var6[6]);
                e = P.decode(var6[7]);
                f = P.decode(var6[8]);
                g = var6[9];
                System.out.println("");
            } catch (Exception var7) {
                var7.printStackTrace();
                //throw new AuthException("lic 文件解析错误.");
            }
        }
    }



    private static void b(ServletContext var0) throws AuthException {
        if (b == null || c == null) {
            String var1 = var0.getRealPath("/WEB-INF/lic");
            File var2 = new File(var1);
            if (!var2.exists()) {
                var2 = new File(var1 + ".txt");
                if (!var2.exists()) {
                    //throw new AuthException("lic 文件不存在.");
                }
            }

            try {
                FileInputStream var3 = new FileInputStream(var2);
                String var4 = IOUtils.toString(var3, "UTF-8");
                var3.close();
                var4 = P.decode(var4);
                BASE64Decoder var5 = new BASE64Decoder();
                var4 = new String(var5.decodeBuffer(var4));
                String[] var6 = var4.split("\n");
                if (var6 == null || var6.length != 11) {
                    //throw new AuthException();
                }

                b = P.decode(var6[3]);
                c = P.decode(var6[4]);
                d = P.decode(var6[6]);
                e = P.decode(var6[7]);
                f = P.decode(var6[8]);
                g = var6[9];
            } catch (Exception var7) {
                var7.printStackTrace();
                //throw new AuthException("lic 文件解析错误.");
            }
        }

    }

    private static void g() throws AuthException {
        Date var0 = new Date();
        SimpleDateFormat var1 = new SimpleDateFormat("yyyyMMdd");

        try {
            Date var2 = var1.parse(c);
            Date var3 = var1.parse(b);
            if (!var0.after(var2) && !var0.before(var3)) {
                "y".equals(e);
            } else {
                //throw new AuthException("lic 文件已经失效.");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            //throw new AuthException(var4.getMessage());
        }
    }

    public static String d() {
        return null;
    }

    public static void a(ServletContext var0) {

    }

    public static void a() {

    }

    public static void b() {

    }

    public static void c() {
    }
}
