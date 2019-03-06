//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.Template;

public interface TextContext extends Element, Template {
    void setText(String var1);

    String getText();

    TextProperty getTextProperty();

    void setTextProperty(TextProperty var1);

    Boolean getFormatEnter();

    void setFormatEnter(Boolean var1);

    Boolean getFormatHtml();

    void setFormatHtml(Boolean var1);
}
