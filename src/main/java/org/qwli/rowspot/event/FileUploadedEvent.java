package org.qwli.rowspot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author qwli7
 * @date 2021/2/6 8:03
 * 功能：FileUploadedEvent 文件上传事件
 **/
public class FileUploadedEvent extends ApplicationEvent {



    public FileUploadedEvent(Object source) {
        super(source);
    }
}
