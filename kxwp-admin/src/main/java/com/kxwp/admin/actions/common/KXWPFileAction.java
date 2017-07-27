package com.kxwp.admin.actions.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.model.OSSObject;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.form.file.UploadFileForm;
import com.kxwp.common.model.oss.FileInfo;
import com.kxwp.common.query.file.OSSFileQuery;
import com.kxwp.common.service.core.KXWPFileService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.common.utils.PropertiesValidatorUtil;

/**
 * 总站文件相关操作
 *
 **/
@Controller
@RequestMapping("/common/file")
public class KXWPFileAction {


  @Autowired
  private KXWPFileService kxwpFileService;


  /**
   * 
   * uploadFile:(文件上传).
   *
   * 2016年8月10日 上午10:05:18
   * 
   * @author lou jian wen
   * @param file
   * @param fileForm
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @RequestMapping(value = "/ajax/uploadFile.do", produces = {"application/json"})
  public @ResponseBody ExchangeData uploadFile(@RequestParam("file") MultipartFile file,
      UploadFileForm fileForm, HttpServletRequest request, HttpServletResponse response) {
    ExchangeData exDataBase = new ExchangeData();
    try {
      if (file == null) {
        exDataBase.markFail("请选择文件");
        return exDataBase;
      }
      InputStream fileContent = file.getInputStream();
      fileForm.setFileName(file.getOriginalFilename());
      fileForm.setContent(fileContent);
      fileForm.setFile_key("");
      List<FileInfo> fileList = kxwpFileService.uploadFile(fileForm);
      exDataBase.setData(fileList);
    } catch (Exception e) {
      exDataBase.markException(e);
    }
    return exDataBase;
  }

  /**
   * 
   * deleteFile:(删除文件).
   *
   * 2016年8月12日 下午4:30:04
   * 
   * @author lou jian wen
   * @param sysFileQuery
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/ajax/deleteFile.do", produces = {"application/json"})
  public @ResponseBody ExchangeData deleteFile(@RequestBody OSSFileQuery sysFileQuery,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData exDataBase = new ExchangeData();
    try {
      kxwpFileService.deleteFile(sysFileQuery);
    } catch (Exception e) {
      exDataBase.markException(e);
    }
    return exDataBase;
  }


  /**
   * 
   * readFile:(读取不允许公开访问的图片).
   *
   * 2016年8月12日 下午4:30:36
   * 
   * @author lou jian wen
   * @param sysFileQuery
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = {"/ajax/readFile.do"},
      produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}, method = {RequestMethod.GET})
  public @ResponseBody ResponseEntity<byte[]> readFile(OSSFileQuery sysFileQuery,
      HttpServletRequest request, HttpServletResponse response) {
    final HttpHeaders headers = new HttpHeaders();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Content-disposition", "inline; filename=" + sysFileQuery.getFile_key());
    response.setDateHeader("Expires", 0L);
    String errorMsg = PropertiesValidatorUtil.validator(sysFileQuery);

    if (StringUtils.isNotBlank(errorMsg)) {
      String msg = "读文件条件非法" + errorMsg;
      headers.setContentType(MediaType.TEXT_PLAIN);
      return new ResponseEntity<byte[]>(msg.getBytes(), headers, HttpStatus.OK);
    }
    try {
      OSSObject ossFile = kxwpFileService.readFile(sysFileQuery);
      if (ossFile != null) {
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = ossFile.getObjectContent();
        writeFile(inputStream, outputStream);
      }

    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "readFile exception", e);
      InputStream file_404 =
          request.getSession().getServletContext().getResourceAsStream("/images/share/404.png");
      try {
        OutputStream outputStream = response.getOutputStream();;
        writeFile(file_404, outputStream);
      } catch (IOException e1) {
        KXWPLogUtils.logException(this.getClass(), "读取404图片异常", e1);
      }
      return new ResponseEntity<byte[]>(new byte[] {}, headers, HttpStatus.OK);
    }
    return new ResponseEntity<byte[]>(new byte[] {}, headers, HttpStatus.OK);
  }


  void writeFile(InputStream inputStream, OutputStream outputStream) throws IOException {
    byte[] buffer = new byte[1024];
    int read = 0;
    while ((read = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, read);
    }
    // close the streams to prevent memory leaks
    outputStream.flush();
    outputStream.close();
    inputStream.close();
  }

  /**
   * 
   * readFileListSummary:(模糊查询文件的object key,再根据object key查出授权访问url).
   *
   * 2016年8月12日 下午4:32:19
   * 
   * @author lou jian wen
   * @param sysFileQuery
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = {"/ajax/getFilekeyAccessURL.do"},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public @ResponseBody ExchangeData getFilekeyAccessURL(@RequestBody OSSFileQuery sysFileQuery,
      HttpServletRequest request, HttpServletResponse response) {

    ExchangeData fileListSummaryData = new ExchangeData();

    String errorMsg = PropertiesValidatorUtil.validator(sysFileQuery);

    if (StringUtils.isNotBlank(errorMsg)) {
      fileListSummaryData.markFail(errorMsg);
      return fileListSummaryData;
    }
    try {
      List<String> fileUrlList = kxwpFileService.getBatchOSSFileAuthURL(sysFileQuery);
      fileListSummaryData.setData(fileUrlList);
    } catch (Exception e) {
      fileListSummaryData.markException(e);
      KXWPLogUtils.logException(this.getClass(), "readFileListSummary exception", e);
    }
    return fileListSummaryData;
  }


}
