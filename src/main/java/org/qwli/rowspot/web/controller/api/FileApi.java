package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.Constants;
import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.UploadedFile;
import org.qwli.rowspot.service.file.FileDelete;
import org.qwli.rowspot.service.file.FileService;
import org.qwli.rowspot.service.file.FileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * FileApi
 * @author qwli7
 */
@RestController
@RequestMapping("api")
public class FileApi extends AbstractApi {


    private final FileService fileService;

    public FileApi(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("files/upload")
    public ResponseEntity<UploadedFile> fileUpload(FileUpload fileUpload, HttpServletRequest request) {
        final LoggedUser loggedUser = (LoggedUser) request.getAttribute(Constants.USER);
        fileUpload.setUserId(loggedUser.getId());
        return ResponseEntity.ok(fileService.uploadFile(fileUpload));
    }
    
    @DeleteMapping("file/{id}/delete")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") Long fid, HttpServletRequest request) {
        final LoggedUser loggedUser = (LoggedUser) request.getAttribute(Constants.USER);
        FileDelete fileDelete = new FileDelete(loggedUser.getId(), fid);
        fileService.deleteFile(fileDelete);
        return ResponseEntity.ok().build();
    }
}
