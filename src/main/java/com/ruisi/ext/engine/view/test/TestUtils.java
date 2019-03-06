//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.test;

import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class TestUtils {
    public static final String equalTest = "==";
    public static final String notEqualTest = "!=";
    public static final String dsDataType = "ds";
    public static final String stringDataType = "string";
    public static final String intDataType = "int";

    public TestUtils() {
    }

    public static TestAdapter createTest(String var0) throws ExtConfigException {
        Object var1 = null;
        String var2 = null;
        String var3 = null;
        boolean var4 = true;
        int var6;
        if ((var6 = var0.indexOf("==")) > 0) {
            var2 = var0.substring(0, var6).trim();
            var3 = var0.substring(var6 + 2).trim();
            var1 = new TestEqualAdapterImpl();
        } else {
            if ((var6 = var0.indexOf("!=")) <= 0) {
                throw new ExtConfigException("test 表达式有误.");
            }

            var2 = var0.substring(0, var6).trim();
            var3 = var0.substring(var6 + 2).trim();
            var1 = new TestNotEqualAdapterImpl();
        }

        String var5;
        if ((var5 = findVar(var2)) != null) {
            ((TestAdapter)var1).setPrefix(var5);
            ((TestAdapter)var1).setPrefixType("ds");
        } else if ((var5 = findString(var2)) != null) {
            ((TestAdapter)var1).setPrefix(var5);
            ((TestAdapter)var1).setPrefixType("string");
        } else {
            ((TestAdapter)var1).setPrefix(var2);
            ((TestAdapter)var1).setPrefixType("int");
        }

        if ((var5 = findVar(var3)) != null) {
            ((TestAdapter)var1).setSuffix(var5);
            ((TestAdapter)var1).setSuffixType("ds");
        } else if ((var5 = findString(var3)) != null) {
            ((TestAdapter)var1).setSuffix(var5);
            ((TestAdapter)var1).setSuffixType("string");
        } else {
            ((TestAdapter)var1).setSuffix(var3);
            ((TestAdapter)var1).setSuffixType("int");
        }

        return (TestAdapter)var1;
    }

    public static Object findData(Map var0, ExtEnvirContext var1, ExtRequest var2, String var3, String var4) {
        Object var5;
        if (var4.equals("ds")) {
            Object var6 = null;
            if (var0 != null) {
                var6 = var0.get(var3);
            }

            if (var6 == null) {
                String[] var7 = findProperty(var3);
                if (var7[1] == null) {
                    var6 = var1.get(var3);
                } else {
                    try {
                        var6 = PropertyUtils.getProperty(var1.get(var7[0]), var7[1]);
                    } catch (Exception var9) {
                        throw new ExtRuntimeException(var9);
                    }
                }
            }

            var5 = var6;
        } else if (var4.equals("string")) {
            var5 = var3;
        } else {
            if (!var4.equals("int")) {
                throw new ExtRuntimeException("test出错，未被识别的数据类型");
            }

            var5 = new Integer(var3);
        }

        return var5;
    }

    public static String findVar(String var0) {
        String var1 = "\\$\\{\\s*([\\w\\.]+)\\s*\\}";
        Pattern var2 = Pattern.compile(var1);
        Matcher var3 = var2.matcher(var0);
        return var3.find() ? var3.group(1) : null;
    }

    public static String[] findProperty(String var0) {
        int var1;
        if ((var1 = var0.indexOf(46)) > 0) {
            String var2 = var0.substring(0, var1);
            String var3 = var0.substring(var1 + 1);
            return new String[]{var2, var3};
        } else {
            return new String[]{var0, null};
        }
    }

    public static String findString(String var0) {
        String var1 = "\\'(\\s*\\w*\\s*)\\'";
        Pattern var2 = Pattern.compile(var1);
        Matcher var3 = var2.matcher(var0);
        return var3.find() ? var3.group(1) : null;
    }

    public static String findParams(String var0) {
        String var1 = "\\((\\s*\\w*\\s*)\\)";
        Pattern var2 = Pattern.compile(var1);
        Matcher var3 = var2.matcher(var0);
        return var3.find() ? var3.group(1) : null;
    }

    public static boolean isFunc(String var0) {
        String var1 = "\\((\\s*\\w*,\\s*\\w*\\s*)\\)";
        Pattern var2 = Pattern.compile(var1);
        Matcher var3 = var2.matcher(var0);
        if (var3.find()) {
            System.out.println(var3.group(1));
        }

        return var3.find();
    }

    public static String findValue(String var0, ExtRequest var1, ExtEnvirContext var2) {
        if (var0 == null) {
            return null;
        } else {
            String var3;
            if ((var3 = findVar(var0)) != null) {
                String[] var4 = findProperty(var3);
                Object var5;
                if (var4[1] == null) {
                    var5 = var2.get(var4[0]);
                    return var5 == null ? "" : var5.toString();
                } else {
                    try {
                        var5 = var2.get(var4[0]);
                        return var5 == null ? null : BeanUtils.getProperty(var5, var4[1]);
                    } catch (Exception var6) {
                        throw new ExtRuntimeException("取值出错.", var6);
                    }
                }
            } else {
                return var0;
            }
        }
    }
}
