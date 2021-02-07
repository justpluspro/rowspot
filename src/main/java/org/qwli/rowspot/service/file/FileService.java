package org.qwli.rowspot.service.file;

import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.event.FileUploadedEvent;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.UploadedFile;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserUploadFile;
import org.qwli.rowspot.model.enums.FileType;
import org.qwli.rowspot.model.enums.UserState;
import org.qwli.rowspot.repository.FileRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.qwli.rowspot.service.FileUpload;
import org.qwli.rowspot.service.condition.FileCondition;
import org.qwli.rowspot.util.FileUtil;
import org.qwli.rowspot.util.MediaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author qwli7
 */
@Service
@Conditional(value = FileCondition.class)
public class FileService implements InitializingBean, ApplicationEventPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 普通用户允许上传的最大文件容量 100M
     * 管理员无限大
     */
    private static final Long MAX_UPLOAD_SIZE = 100 * 1024 * 1000L;

    private ApplicationEventPublisher applicationEventPublisher;

    private final Path rootUploadPath;

    private final static String UPLOAD = "upload";

    private final List<String> allowMediaTypes = Arrays.asList("jpg", "png", "webp", "gif", "jpeg", "mp4", "mov", "rmvb", "avi", "flv");

    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    public FileService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.rootUploadPath = Paths.get(Paths.get(System.getProperty("user.dir")).getParent().toString()).resolve(UPLOAD);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public UploadedFile uploadFile(FileUpload fileUpload) throws BizException {
        final User dbUser = userRepository.findById(fileUpload.getUserId()).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        final UserState userState = dbUser.getState();
        if (userState != UserState.NORMAL) {
            throw new BizException(MessageEnum.USER_STATE_INVALID);
        }

        //check user container size
        //from db or container
        final Long totalSize = fileRepository.findTotalSizeByUser(dbUser.getId()).orElse(0L);
        if (totalSize > MAX_UPLOAD_SIZE) {
            throw new BizException(new Message("max.uploadSize", "用户超过最大限制"));
        }

        //create user container
        Path containerPath = rootUploadPath.resolve(String.valueOf(dbUser.getId()));
        if (!containerPath.toFile().exists()) {
            try {
                Files.createDirectories(containerPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException(new Message("container.createFailed", "容器创建失败"));
            }
        }

        // valid file
        final MultipartFile file = fileUpload.getFile();
        Assert.notNull(file, "file not null.");

        String originalFilename = file.getOriginalFilename();
        final long size = file.getSize();
        logger.info("fileSize:[{}]", size);

        if (!StringUtils.hasText(originalFilename)) {
            throw new BizException(new Message("invalid.file", "无效的 file"));
        }

        String fileExtension = FileUtil.getFileExtension(originalFilename);
//        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        logger.info("fileExtension:[{}]", fileExtension);


        if (!MediaUtil.allowFormat(fileExtension)) {
            throw new BizException(new Message("unsupport.file", "不支持的文件"));
        }

        try {
            File destFile = new File(containerPath.toFile(), originalFilename);
            if (destFile.exists()) {
                originalFilename = FileUtil.getFileNameWithoutExtension(originalFilename) + "副本." + fileExtension;
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
        userUploadFile.setFileType(MediaUtil.getFileType(fileExtension).orElse(null));
        userUploadFile.setFileSize(size);
        userUploadFile.setUrl("/" + dbUser.getId() + "/" + originalFilename);

        fileRepository.save(userUploadFile);
        applicationEventPublisher.publishEvent(new FileUploadedEvent(this));

        return uploadedFile;
    }

    public Optional<ReadablePath> getProcessedFile(String requestPath, boolean supportWebp) {
        logger.info("getProcessedFile:[{}]", requestPath);

        PathParser pathParser = new PathParser(requestPath);
//        if(pathParser.isValid()){
//            return Optional.empty();
//        }
//        Optional<Path> pathOptional = lookupFile(Lookup.newLookup(pathParser.getResourcePath()).setMustRegularFile(true));
        Optional<Path> pathOptional = lookupFile(Lookup.newLookup(pathParser.getPath()).setMustRegularFile(true));
        if (!pathOptional.isPresent()) {
            return Optional.empty();
        }
        Path file = pathOptional.get();

        String fileExtension = FileUtil.getFileExtension(file);
        Resize resize = pathParser.getResize();

//        if (resize == null || resize.isInvalid()) {
//            //this resource can handle
//
//        } else {
//            return Optional.empty();
//        }

        return Optional.of(new NormalFileReadablePath(file));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    private Optional<Path> lookupFile(Lookup lookup) {
        Path p;
        try {
            p = resolve(rootUploadPath, lookup.getPath());
        } catch (InvalidPathException ex) {
            return Optional.empty();
        }
        return Optional.of(p);
    }

    private Path resolve(Path root, String path) {
        Path resolve;
        if (!StringUtils.hasText(path)) {
            return root;
        } else {
            resolve = root.resolve(path);
        }
        return resolve;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            FileUtil.forceMkdir(rootUploadPath);
        } catch (IOException ex){
            logger.error("FileService initial uploadPath error:[{}]",ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
