//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html.parser;

import com.ruisi.ext.engine.view.context.html.TextProperty;
import java.util.ArrayList;
import java.util.List;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class ParserUtils {
    public ParserUtils() {
    }

    public static List parserHtml(String var0) throws ParserException {
        ArrayList var1 = new ArrayList();
        Parser var2 = Parser.createParser(var0, "UTF-8");
        PrototypicalNodeFactory var3 = new PrototypicalNodeFactory();
        var3.registerTag(new Font());
        var2.setNodeFactory(var3);
        NodeIterator var4 = null;
        try {
            var4 = var2.elements();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        while(var4.hasMoreNodes()) {
            Node var5 = var4.nextNode();
            TextProperty var6 = new TextProperty();
            if (var5 instanceof Font) {
                Font var7 = (Font)var5;
                var6.setText(var7.toPlainTextString());
                var6.setColor(var7.getAttribute("color"));
                var6.setSize(var7.getAttribute("size"));
                var1.add(var6);
            } else if (var5 instanceof TextNode) {
                var6.setText(var5.toPlainTextString());
                var1.add(var6);
            }
        }

        return var1;
    }
}
