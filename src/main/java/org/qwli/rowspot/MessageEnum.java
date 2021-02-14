package org.qwli.rowspot;



public interface MessageEnum {

    Message SERVER_ERROR = new Message("server.error", "服务器错误");


    /**
     * 用户不存在
     */
    Message USER_NOT_EXISTS = new Message("user.notExists", "用户名或密码错误");

    /**
     * 邮箱已被注册
     */
    Message EMAIL_REGISTERED = new Message("email.registered", "邮箱已被注册");

    Message USER_LOGIN = new Message("user.normal", "状态正常，去登录");
    Message REGISTER_FAILED = new Message("register.failed", "注册用户失败");
    Message USER_STATE_INVALID = new Message("userState.invalid", "无效用户状态");

    Message USER_LOCKED = new Message("user.locked", "用户已被锁定，请联系管理员");
    Message AUTH_FAILED = new Message("auth.failed", "认证失败");
    Message USER_UNACTIVATED = new Message("user.unactivated", "用户未激活，请先去邮箱激活");
    Message ARTICLE_INDEX_UNIQUE = new Message("indexUnique.exists", "分类仅允许存在一篇首页唯一的文章");

    Message MENU_NOT_EXISTS = new Message("menu.notExists", "菜单不存在");
    Message MENU_HAS_EXISTS = new Message("menu.hasExists", "菜单已经存在");
    Message CATEGORY_NOT_EXISTS = new Message("category.notExists", "分类不存在");
    Message CATEGORY_HAS_EXISTS = new Message("category.hasExists", "分类已经存在");



    Message RESOURCE_NOT_FOUND = new Message("resource.notFound", "资源未找到");
    Message ARTICLE_STATE_ERROR = new Message("articleState.error", "无效的文章状态");
    Message ARTICLE_NOT_EXISTS = new Message("article.notExists", "文章不存在");

    Message TAG_NOT_EXISTS = new Message("tag.notExists", "标签不存在");
    Message NAME_EXISTS = new Message("name.exists", "名称存在");
    Message ALIAS_EXISTS = new Message("alias.exists", "别名已经存在");
}

