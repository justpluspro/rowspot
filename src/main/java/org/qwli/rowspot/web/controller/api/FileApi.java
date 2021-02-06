package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.UploadedFile;
import org.qwli.rowspot.service.FileService;
import org.qwli.rowspot.service.FileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api")
public class FileApi extends AbstractApi {


    private final FileService fileService;

    public FileApi(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("files/upload")
    public ResponseEntity<UploadedFile> fileUpload(FileUpload fileUpload, HttpServletRequest request) {
        final LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");
        fileUpload.setUserId(loggedUser.getId());
        return ResponseEntity.ok(fileService.uploadFile(fileUpload));

    }

}