package org.qwli.rowspot.service;

import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.UploadedFile;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserUploadFile;
import org.qwli.rowspot.model.enums.FileType;
import org.qwli.rowspot.model.enums.UserState;
import org.qwli.rowspot.repository.FileRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final String rootContainer = "/Users/liqiwen/Desktop/upload/";

    private final List<String> allowMediaTypes = Arrays.asList("jpg", "png", "webp", "gif", "jpeg", "mp4", "mov", "rmvb", "avi", "flv");


    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    public FileService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public UploadedFile uploadFile(FileUpload fileUpload) throws BizException {
        final User dbUser = userRepository.findById(fileUpload.getUserId()).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        final UserState userState = dbUser.getState();
        if(userState != UserState.NORMAL) {
            throw new BizException(MessageEnum.USER_STATE_INVALID);
        }

        //check user container size
        //from db or container
        final Long totalSize = fileRepository.findTotalSizeByUser(dbUser.getId()).orElse(0L);
        if(totalSize > 500000) {
            throw new BizException(new Message("max.uploadSize", "用户超过最大限制"));
        }

        //create user container
        Path containerPath = Paths.get(rootContainer, String.valueOf(dbUser.getId()));
        if(!containerPath.toFile().exists()) {
            try {
                Files.createDirectories(containerPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException(new Message("contaienr.createFailed", "容器创建失败"));
            }
        }

        // valid file
        final MultipartFile file = fileUpload.getFile();
        Assert.notNull(file, "file not null.");

        String originalFilename = file.getOriginalFilename();
        final long size = file.getSize();
        logger.info("fileSize:[{}]", size);

        if(!StringUtils.hasText(originalFilename)) {
            throw new BizException(new Message("invalid.file", "无效的 file"));
        }


        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        logger.info("fileExtension:[{}]", extension);

        if(!allowMediaTypes.contains(extension) && !allowMediaTypes.contains(extension.toLowerCase())) {
            throw new BizException(new Message("unsupport.file", "不支持的文件"));
        }


        try {
            File destFile = new File(containerPath.toFile(), originalFilename);
            if(destFile.exists()) {
                originalFilename = originalFilename+"副本."+extension;
                destFile = new File(containerPath.toFile(), originalFilename);
                Files.createFile(destFile.toPath());
            }
            Files.copy(file.getInputStream(), destFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException(new Message("uploadFile.failed", "上传文件失败"));
        }

        final UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(originalFilename);
        uploadedFile.setUrl("/" + dbUser.getId() + "/" + originalFilename);

        //upload success

        UserUploadFile userUploadFile = new UserUploadFile();
        userUploadFile.setUserId(dbUser.getId());
        userUploadFile.setFileName(originalFilename);
        userUploadFile.setCreateAt(new Date());
        userUploadFile.setModifyAt(new Date());
        userUploadFile.setFileType(FileType.getTypeType(extension));
        userUploadFile.setFileSize(size);
        userUploadFile.setUrl("/" + dbUser.getId() + "/" + originalFilename);

        fileRepository.save(userUploadFile);
        //        applicationEventPublisher.publishEvent(new FileUploaded);


        return uploadedFile;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
