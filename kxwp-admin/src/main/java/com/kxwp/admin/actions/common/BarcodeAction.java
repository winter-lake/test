package com.kxwp.admin.actions.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kxwp.admin.form.common.BarcodeForm;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.utils.KXWPLogUtils;


/**
 * 条形码生成生成 date: 2016年8月28日 下午4:02:00
 *
 * @author janwen
 */
@Controller
@RequestMapping("/common/ajax")
public class BarcodeAction {



  @RequestMapping("/generateBarcode.do")
  public void generateBarcode(BarcodeForm barcodeForm, HttpServletRequest request,
      HttpServletResponse response) {
    response.setHeader("Content-Type", "image/png");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    try {
      byte[] barcode = assmbleBarcode(barcodeForm);
      response.setContentType(MediaType.IMAGE_PNG_VALUE);
      response.setContentLength(barcode.length);
      OutputStream output = response.getOutputStream();
      IOUtils.write(barcode, output);
      output.close();
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "generateBarcode exception", e);
    }
  }



  byte[] assmbleBarcode(BarcodeForm form) throws SYSException {
    // Code39Bean barcode = new Code39Bean();

    Code128Bean barcode = new Code128Bean();
    final int dpi = 300;
    // Configure the barcode generator
    // barcode.setModuleWidth(form.getWidth()/dpi);
    barcode.setModuleWidth(0.254);
    // barcode.doQuietZone(false);
    // barcode.setBarHeight(7);
    barcode.setHeight(form.getBarcodeType().getHeight());
    // Set up the canvas provider for monochrome PNG output
    BitmapCanvasProvider canvas =
        new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
    // Generate the barcode
    barcode.generateBarcode(canvas, form.getCode());
    BufferedImage bufferedImage = canvas.getBufferedImage();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, "png", baos);
      byte[] bytes = baos.toByteArray();
      canvas.finish();
      return bytes;
    } catch (IOException e) {
      KXWPLogUtils.logException(this.getClass(), "assmbleBarcode exception", e);
    }
    throw new SYSException("assmbleBarcode exception");
  }

}
