package org.qwli.rowspot.model.enums;

public enum ArticleType {
    A("article", 0),
    Q("question", 1),
    N("news", 2),
    T("tools", 3),
    I("index_unique", 0);

    String code;
    Integer sort;

    ArticleType(String code, Integer sort) {
        this.code = code;
        this.sort = sort;
    }


    public static boolean isArticle(String name) {
        return A.name().equals(name);
    }

    public static boolean isQuestion(String name) {
        return Q.name().equals(name);
    }

    public static boolean isIndexUnique(String name) {
        return I.name().equals(name);
    }

    public static boolean isNews(String name) {
        return N.name().equals(name);
    }

    public static boolean isTools(String name) {
        return T.name().equals(name);
    }
       
    /**
     * 获取所有的文章类型，并排序
     **/
    public List<Map<String, Object>> findAll() {
        for(ArticleType[] type : ArticleType.values()) {
            
        }
        
        return new ArrayList();
    }
    
}
