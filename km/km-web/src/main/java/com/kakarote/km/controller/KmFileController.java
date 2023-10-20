package com.kakarote.km.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.feign.admin.service.AdminFileService;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.service.IKmAuthService;
import com.kakarote.km.service.IKmDocumentService;
import feign.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author: admin
 * @version: v1.0
 * @date:2023/7/11
 */
@RestController
@RequestMapping("/kmFile")
@Api(tags = "文件操作")
public class KmFileController {
    @Autowired
    private AdminFileService fileService;

    @Autowired
    private IKmDocumentService documentService;

    @Autowired
    private IKmAuthService kmAuthService;


    @RequestMapping("/download/{fileId}/{documentId}")
    public void download(@PathVariable("fileId") Long fileId, @PathVariable(value = "documentId") Long documentId, HttpServletResponse response) {
        KmDocument document = documentService.getById(documentId);
        Integer role = kmAuthService.getFolderOrDocumentAuth(document.getAuthId(), document.getLibraryId());
        if (role == 3) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        }

        try {
            Response dataResponse = fileService.downloadFile(fileId);
            Map<String, Collection<String>> headers = dataResponse.headers();
            Collection<String> contentType = headers.get("content-type");
            Collection<String> contentDisposition = headers.get("content-disposition");
            if (CollectionUtil.isNotEmpty(contentType) && CollectionUtil.isNotEmpty(contentDisposition)) {
                String contentTypeValue = contentType.stream().findFirst().get();
                String contentDispositionValue = contentDisposition.stream().findFirst().get();

                String fileType = StrUtil.subAfter(contentDispositionValue, ".", true);

                String title = document.getTitle();

                response.setHeader("Content-Disposition", StrUtil.format("attachment;filename={}",
                        URLUtil.encode(title + "." + fileType, CharsetUtil.CHARSET_UTF_8)));

                response.setContentType(contentTypeValue);
            }
            InputStream inputStream = dataResponse.body().asInputStream();
            if (inputStream != null) {
                ServletUtil.write(response, inputStream);
            }
        } catch (Exception exception) {
            throw new RuntimeException("文件下载失败", exception);
        }

    }

}
