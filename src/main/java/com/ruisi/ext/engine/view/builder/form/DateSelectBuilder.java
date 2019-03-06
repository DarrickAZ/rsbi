//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.test.TestUtils;

import java.lang.reflect.InvocationTargetException;

public class DateSelectBuilder extends AbstractBuilder {
    private DateSelectContext a;

    public DateSelectBuilder(DateSelectContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() {
        String var1 = this.a.getValue();
        if (var1 != null && var1.length() > 0) {
            var1 = TestUtils.findValue(var1, this.request, this.veloContext);
        }

        this.a.setOutValue(var1);
        try {
            this.emitter.startDateSelect(this.a);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
