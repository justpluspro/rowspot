    package org.qwli.rowspot.util;

import org.qwli.rowspot.model.enums.FileType;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

    /**
 * @author qwli7
 * @date 2021/2/7 12:44
 * 功能：MediaUtil
 **/
public class MediaUtil {

    private MediaUtil(){
        super();
    }

    public static boolean allowFormat(String fileExtension) {
        if(!StringUtils.hasText(fileExtension)){
            return false;
        }

        String upperCaseFileExtension = fileExtension.toUpperCase();

        FileType[] values = FileType.values();
        return Arrays.stream(values).anyMatch(fileType ->
                fileType.name().equals(upperCaseFileExtension));
    }

    public static Optional<FileType> getFileType(String fileExtension) {
        if(!StringUtils.hasText(fileExtension)) {
            return Optional.empty();
        }
        String upperCaseFileExtension = fileExtension.toUpperCase();

        return Arrays.stream(FileType.values()).filter(e ->
                upperCaseFileExtension.equals(e.name())).findFirst();
    }
}
