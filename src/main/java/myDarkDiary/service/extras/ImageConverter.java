/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.extras;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import myDarkDiary.service.model.Image;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class ImageConverter {
    
        
    public Image convertImage(Image imageToConvert,int width,int height) throws IOException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageToConvert.getData());
        BufferedImage originalImage=ImageIO.read(bais);
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizeImageJpg = resizeImage(originalImage,width,height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizeImageJpg, "jpg", baos);
        imageToConvert.setData(baos.toByteArray());
        baos.close();
        bais.close();
        return imageToConvert;
    }
    
    private static BufferedImage resizeImage (BufferedImage image, int areaWidth, int areaHeight) {
    float scaleX = (float) areaWidth / image.getWidth();
    float scaleY = (float) areaHeight / image.getHeight();
    float scale = Math.min(scaleX, scaleY);
    int w = Math.round(image.getWidth() * scale);
    int h = Math.round(image.getHeight() * scale);

    int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

    boolean scaleDown = scale < 1;

    if (scaleDown) {
        // multi-pass bilinear div 2
        int currentW = image.getWidth();
        int currentH = image.getHeight();
        BufferedImage resized = image;
        while (currentW > w || currentH > h) {
            currentW = Math.max(w, currentW / 2);
            currentH = Math.max(h, currentH / 2);

            BufferedImage temp = new BufferedImage(currentW, currentH, type);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(resized, 0, 0, currentW, currentH, null);
            g2.dispose();
            resized = temp;
        }
        return resized;
    } else {
        Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC : RenderingHints.VALUE_INTERPOLATION_BILINEAR;

        BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
        g2.drawImage(image, 0, 0, w, h, null);
        g2.dispose();
        return resized;
    }
}
}
