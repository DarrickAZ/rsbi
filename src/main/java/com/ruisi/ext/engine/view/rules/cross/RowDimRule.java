//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RowDimRule extends Rule {
    public RowDimRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("name");
        String var5 = var3.getValue("code");
        String var6 = var3.getValue("type");
        String var7 = var3.getValue("useLink");
        String var8 = var3.getValue("cascade");
        String var9 = var3.getValue("codeDesc");
        String var10 = var3.getValue("ref");
        String var11 = var3.getValue("value");
        String var12 = var3.getValue("testFunc");
        String var13 = var3.getValue("refDataCenter");
        String var14 = var3.getValue("drillTest");
        RowDimContext var15 = new RowDimContext();
        var15.setName(var4);
        var15.setCode(var5);
        var15.setType(var6);
        var15.setUseLink("true".equalsIgnoreCase(var7));
        var15.setCodeDesc(var9);
        var15.setCascade(var8);
        var15.setRef(var10);
        var15.setValue(var11);
        var15.setTestFunc(var12);
        var15.setRefDataCenter(var13);
        if (var14 != null && var14.length() > 0) {
            var15.setDrillTest(TestUtils.createTest(var14));
        }

        CrossReportContext var16 = (CrossReportContext)this.digester.peek();
        if (var16.getDims() == null) {
            var16.setDims(new ArrayList());
        }

        var16.getDims().add(var15);
        var15.setCrossReport(var16);
        this.digester.push(var15);
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
