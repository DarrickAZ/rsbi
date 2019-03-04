//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class VerifyCodeService {
    private static Map<String, String> yzmMap = Collections.synchronizedMap(new HashMap());
    private int width = 60;
    private int height = 20;
    private int codeCount = 4;
    private int x = 0;
    private int fontHeight;
    private int codeY;
    char[] codeSequence = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public VerifyCodeService() {
    }

    public void initxuan() {
        String strWidth = "100";
        String strHeight = "25";
        String strCodeCount = "4";

        try {
            if (strWidth != null && strWidth.length() != 0) {
                this.width = Integer.parseInt(strWidth);
            }

            if (strHeight != null && strHeight.length() != 0) {
                this.height = Integer.parseInt(strHeight);
            }

            if (strCodeCount != null && strCodeCount.length() != 0) {
                this.codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException var5) {
        }

        this.x = this.width / (this.codeCount + 1);
        this.fontHeight = this.height - 2;
        this.codeY = this.height - 4;
    }

    public void createPic(String guid, HttpServletResponse resp) throws IOException {
        this.initxuan();
        BufferedImage buffImg = new BufferedImage(this.width, this.height, 1);
        Graphics2D g = buffImg.createGraphics();
        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.width, this.height);
        Font font = new Font("Fixedsys", 0, this.fontHeight);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.width - 1, this.height - 1);
        g.setColor(Color.BLACK);

        int red;
        int green;
        int blue;
        int i;
        for(int i = 0; i < 10; ++i) {
            red = random.nextInt(this.width);
            green = random.nextInt(this.height);
            blue = random.nextInt(12);
            i = random.nextInt(12);
            g.drawLine(red, green, red + blue, green + i);
        }

        StringBuffer randomCode = new StringBuffer();
        int red = false;
        int green = false;
        int blue = false;

        for(i = 0; i < this.codeCount; ++i) {
            String strRand = String.valueOf(this.codeSequence[random.nextInt(36)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, i * this.x + 10, this.codeY);
            randomCode.append(strRand);
        }

        yzmMap.put(guid, randomCode.toString());
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0L);
        resp.setContentType("image/jpeg");
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

    public Map<String, String> getYzmMap() {
        return yzmMap;
    }
}
