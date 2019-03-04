//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.runtime.tag.CalendarTag;
import com.ruisitech.bi.entity.frame.User;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class RSBIUtils {
    public RSBIUtils() {
    }

    public static String getEncodedStr(String str) {
        return str == null ? null : getMD5(str.getBytes());
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            char[] str = new char[32];
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
        }

        return s;
    }

    public static String getConstant(String name) {
        return ExtContext.getInstance().getConstant(name);
    }

    public static String getUUIDStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String dealStringParam(String vals) {
        String[] vls = vals.split(",");
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < vls.length; ++i) {
            String v = vls[i];
            sb.append("'" + v + "'");
            if (i != vls.length - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public static Map<String, String> getAllParams(HttpServletRequest req) {
        Map<String, String> dt = new HashMap();
        Enumeration enu = req.getParameterNames();

        while(enu.hasMoreElements()) {
            String key = (String)enu.nextElement();
            dt.put(key, req.getParameter(key));
        }

        return dt;
    }

    public static boolean isShowMenu(String name, HttpServletRequest req) {
        JSONObject obj = (JSONObject)req.getAttribute("menuDisp");
        if (obj == null) {
            return true;
        } else {
            Object r = obj.get(name);
            if (r == null) {
                return true;
            } else {
                return !(r instanceof Integer) || (Integer)r != 0;
            }
        }
    }

    public static Object getBean(ServletContext sctx, Class clazz) {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
        return ctx.getBean(clazz);
    }

    public static SqlSession getSqlSession(ServletContext sctx) {
        SqlSession sqlSession = null;
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)ctx.getBean("sqlSessionFactory");
        sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    public static void closeSqlSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }

    }

    public static User getLoginUserInfo() {
        Subject us = SecurityUtils.getSubject();
        User u = (User)us.getSession().getAttribute("session.user.key");
        return u;
    }

    public static String htmlPage(String body, String host, String type) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"en\">");
        sb.append("<head>");
        sb.append("<title>睿思BI</title>");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\">");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        sb.append("<script type=\"text/javascript\" src=\"" + host + "/ext-res/js/jquery.min.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"" + host + "/ext-res/js/ext-base.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"" + host + "/ext-res/js/echarts.min.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"" + host + "/ext-res/js/sortabletable.js\"></script>");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + host + "/ext-res/css/bootstrap.min.css\" />");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + host + "/resource/css/animate.css\" />");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + host + "/resource/css/style.css\" />");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + host + "/resource/css/font-awesome.css?v=4.4.0\" />");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + host + "/resource/jquery-easyui-1.4.4/themes/gray/easyui.css\">");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + host + "/resource/jquery-easyui-1.4.4/themes/icon.css\">");
        sb.append("<script type=\"text/javascript\" src=\"" + host + "/resource/jquery-easyui-1.4.4/jquery.easyui.min.js\"></script>");
        sb.append("</head>");
        sb.append("<style>");
        sb.append("table.r_layout {table-layout:fixed;width:100%;}");
        sb.append("table.r_layout td.layouttd {padding:10px;}");
        sb.append(".inputform2 {width:120px;}");
        sb.append(".inputtext {width:90px;}");
        sb.append("</style>");
        sb.append("<body class=\"gray-bg\">");
        sb.append(body);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    public static boolean exist(String id, String[] ids) {
        boolean exist = false;
        String[] var3 = ids;
        int var4 = ids.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String tid = var3[var5];
            if (tid.equals(id)) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    public static String getFestival(Object key, HttpServletRequest req) {
        String df = (String)req.getAttribute("dateformat");
        String ret = CalendarTag.getFestival((String)key, df);
        return ret;
    }

    public static String getUploadFilePath() {
        String upFilePath = getConstant("upFilePath");
        if (upFilePath == null || upFilePath.length() == 0) {
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext sctx = webApplicationContext.getServletContext();
            String path = sctx.getRealPath("/") + "/files/";
            File fpath = new File(path);
            if (!fpath.exists()) {
                fpath.mkdirs();
            }

            upFilePath = path;
        }

        return upFilePath;
    }

    public static String htmlEscape(String value) {
        if (value != null && value.length() != 0) {
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
            value = value.replaceAll("script", "");
            return value;
        } else {
            return value;
        }
    }

    public static String escape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);

        for(int i = 0; i < src.length(); ++i) {
            char j = src.charAt(i);
            if (!Character.isDigit(j) && !Character.isLowerCase(j) && !Character.isUpperCase(j)) {
                if (j < 256) {
                    tmp.append("%");
                    if (j < 16) {
                        tmp.append("0");
                    }

                    tmp.append(Integer.toString(j, 16));
                } else {
                    tmp.append("%u");
                    tmp.append(Integer.toString(j, 16));
                }
            } else {
                tmp.append(j);
            }
        }

        return tmp.toString();
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0;
        boolean var3 = false;

        while(lastPos < src.length()) {
            int pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                char ch;
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else if (pos == -1) {
                tmp.append(src.substring(lastPos));
                lastPos = src.length();
            } else {
                tmp.append(src.substring(lastPos, pos));
                lastPos = pos;
            }
        }

        return tmp.toString();
    }
}
