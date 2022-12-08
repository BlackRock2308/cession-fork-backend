package sn.modelsis.cdmp.util;


import java.awt.image.BufferedImage;
import java.io.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

public class Qrcode {
    private static Logger logger = LoggerFactory.getLogger(Qrcode.class);
    public Qrcode() {
    }

    public static String generateQRCode(String qrContent, String filename) {
         int width=100;
         int height=100;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            ByteArrayInputStream inStreambj = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            BufferedImage newImage = ImageIO.read(inStreambj);
            ImageIO.write(newImage, "jpg", new File(filename));
            return  filename;
        } catch (WriterException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
