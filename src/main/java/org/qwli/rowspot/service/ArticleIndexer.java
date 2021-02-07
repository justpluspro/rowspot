package org.qwli.rowspot.service;

import org.qwli.rowspot.util.FileUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author qwli7
 * @date 2021/2/7 8:13
 * 功能：ArticleIndexer
 **/
public class ArticleIndexer implements InitializingBean {

    protected static final String TITLE = "title";
    protected static final String CONTENT = "content";
    protected static final String ID = "id";

    /**
     * 索引目录
     */
    private static final Path INDEX_PATH = Paths.get(System.getProperty("user.home")).resolve("index");

    public ArticleIndexer() {


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            FileUtil.forceMkdir(INDEX_PATH);
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
