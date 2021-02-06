package org.qwli.rowspot.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qwli7
 * @date 2021/2/6 8:34
 * 功能：FileResourceResolver
 **/
public class FileResourceResolver implements ResourceResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final FileService fileService;

    public FileResourceResolver(FileService fileService) {
        super();
        this.fileService = fileService;
        logger.info("FileResourceResolver registered!");
    }

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath,
                                    List<? extends Resource> locations, ResourceResolverChain chain) {
        if(request == null) {
            return null;
        }
        String accept = request.getHeader("Accept");
        boolean supportWebp = accept != null && accept.contains("image/webp");

        logger.info("FileResourceResolver resolverResource:[{}]", requestPath);

        return fileService.getProcessedFile(requestPath, supportWebp)
                .map(ReadablePathFileResource::new).orElse(null);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations,
                                 ResourceResolverChain chain) {
        return null;
    }
}
