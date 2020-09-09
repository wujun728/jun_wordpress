package cn.jeeweb.web.modules.oss.helper;

import cn.jeeweb.common.oss.OSSUploadHelper;
import cn.jeeweb.common.oss.config.OssConfig;
import cn.jeeweb.common.oss.exception.FileNameLengthLimitExceededException;
import cn.jeeweb.common.oss.exception.InvalidExtensionException;
import cn.jeeweb.common.utils.IpUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.modules.oss.entity.Attachment;
import cn.jeeweb.web.modules.oss.service.IAttachmentService;
import cn.jeeweb.web.utils.UserUtils;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.gov.gzst.oss.controller
 * @title: 附件上传助手
 * @description: 附件上传助手
 * @author: 王存见
 * @date: 2018-04-25 14:25:55
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Component("attachmentHelper")
@EnableConfigurationProperties({OssConfig.class})
public class AttachmentHelper {

    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private OssConfig ossConfig;

    private OSSUploadHelper uploadHelper;

    @PostConstruct
    public void initHelper() {
        uploadHelper = new OSSUploadHelper();
        uploadHelper.init(ossConfig);
    }


    public Attachment upload(HttpServletRequest request, MultipartFile file) throws FileSizeLimitExceededException,
            IOException, FileNameLengthLimitExceededException, InvalidExtensionException {
        String basePath=request.getParameter("base_path");
        String url = uploadHelper.upload(request, file, basePath);
        String filename = file.getOriginalFilename();
        long size = file.getSize();
        //file.getContentType();
        String realFileName = StringUtils.getFileNameNoEx(filename);
        String fileExtension = StringUtils.getExtensionName(filename);
        // 保存上传的信息
        Attachment attachment = new Attachment();
        attachment.setFileExtension(fileExtension);
        attachment.setFileName(realFileName);
        attachment.setContentType(file.getContentType());
        attachment.setFilePath(url);
        attachment.setFileSize(size);
        attachment.setStatus("1");
        attachment.setUploadIp(IpUtils.getIpAddr(request));
        attachment.setUploadTime(new Date());
        attachment.setUserId(UserUtils.getUser().getId());
        attachment.setBasePath(basePath);
        attachmentService.insert(attachment);
        return attachment;
    }

    public Attachment remote(HttpServletRequest request,String remoteUrl) throws FileSizeLimitExceededException,
            IOException, FileNameLengthLimitExceededException, InvalidExtensionException {
        String basePath=request.getParameter("base_path");
        String url = uploadHelper.remote(request, remoteUrl, basePath);
        String filename =  remoteUrl.substring(remoteUrl.lastIndexOf('/')+1);
        URL remoteUri = new URL(remoteUrl);
        HttpURLConnection conn = (HttpURLConnection) remoteUri.openConnection();
        conn.setConnectTimeout(10 * 1000);
        long size = Integer.parseInt(conn.getHeaderField("Content-Length"));
        String contentType =  conn.getHeaderField("Content-Type");
        String realFileName = StringUtils.getFileNameNoEx(filename);
        String fileExtension = StringUtils.getExtensionName(filename);
        // 保存上传的信息
        Attachment attachment = new Attachment();
        attachment.setFileExtension(fileExtension);
        attachment.setFileName(realFileName);
        attachment.setFilePath(url);
        attachment.setFileSize(size);
        attachment.setStatus("1");
        attachment.setUploadIp(IpUtils.getIpAddr(request));
        attachment.setUploadTime(new Date());
        attachment.setContentType(contentType);
        attachment.setUserId(UserUtils.getUser().getId());
        attachment.setBasePath(basePath);
        attachmentService.insert(attachment);
        return attachment;
    }

}
