//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.ImageContext;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class ImageBuilder extends AbstractBuilder {
    private ImageContext a;

    public ImageBuilder(ImageContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
        try {
            this.emitter.startImage(this.a);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void processStart() {
    }
}
