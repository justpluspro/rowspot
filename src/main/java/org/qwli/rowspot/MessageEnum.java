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

    /**
     * 注册用户失败
     */
    Message REGISTER_FAILED = new Message("register.failed", "注册用户失败");


    Message USER_LOCKED = new Message("user.locked", "用户已被锁定，请联系管理员");
    Message AUTH_FAILED = new Message("auth.failed", "认证失败");
    Message USER_UNACTIVATED = new Message("user.unactivated", "用户未激活，请先去邮箱激活");


    Message RESOURCE_NOT_FOUND = new Message("resource.notFound", "资源未找到");
}

