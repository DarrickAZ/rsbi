//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IsModel {
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static Pattern phonePat;
    static Pattern tablePat;

    public IsModel() {
    }

    public static boolean check(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }

        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        return matcherPhone.find() || matcherTable.find();
    }

    public static boolean check(HttpServletRequest request) {
        boolean isFromMobile = false;

        try {
            String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (null == userAgent) {
                userAgent = "";
            }

            isFromMobile = check(userAgent);
            return isFromMobile;
        } catch (Exception var3) {
            var3.printStackTrace();
            return isFromMobile;
        }
    }

    public static void setHeader(HttpServletResponse resp) {
        resp.setHeader("Vary", "User-Agent");
    }

    static {
        phonePat = Pattern.compile(phoneReg, 2);
        tablePat = Pattern.compile(tableReg, 2);
    }
}
