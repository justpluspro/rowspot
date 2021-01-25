package org.qwli.rowspot.model.enums;

public enum ArticleType {
    A("article"),
    Q("question"),
    N("news"),
    T("tools"),
    I("index_unique");

    String code;

    ArticleType(String code) {
        this.code = code;
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
}
