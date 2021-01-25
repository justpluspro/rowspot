create database rowspot default charset utf8 collate utf8_unicode_ci;

drop table if exists t_article;
create table t_article
(
    id int auto_increment,
    title varchar(512) character set utf8mb4 collate utf8_unicode_ci default '' not null comment '标题',
    content text character set utf8mb4 collate utf8_unicode_ci null comment '正文部分',
    click_count int not null default 0 comment '点击量',
    subscribe_count int not null default 0 comment '订阅量',
    like_count int not null default 0 comment '点赞',
    create_at datetime not null default current_timestamp() comment '创建时间',
    modify_at datetime not null default current_timestamp() on update current_timestamp() comment '修改时间',
    post_at datetime comment '',
    index_unique boolean default false comment '是否首页唯一，默认为 false',
    solved boolean default false comment '问题是否已解决',
    editor_type tinyint(1) comment '编辑器类型',
    state tinyint(1) comment '状态',
    fk_article_type int not null comment '文章类型',
    fk_user_id int not null comment '发布人',
    fk_column_id int not null comment '一级分类编号',
    fk_column_id2 int null comment '二级分类编号',
    constraint t_article_pk primary key (id)
) engine=InnoDB default character set utf8mb4;

create table t_comment (
       id int auto_increment primary key comment '评论主键',
       content varchar(1024) character set utf8mb4 collate utf8mb4_unicode_ci default '' not null comment '内容',
       state tinyint(1) not null default 0 comment '评论状态',
       create_at datetime not null default current_timestamp() comment '创建时间',
       modify_at datetime not null default current_timestamp() on update current_timestamp() comment '修改时间',
       fk_article_id int not null comment '文章 id',
       fk_user_id int not null comment '用户 id'
) engine=InnoDB default character set utf8mb4;


create table t_tag (
       id int auto_increment primary key comment '标签主键',
       tag_name varchar(32) character set utf8mb4 collate utf8mb4_unicode_ci default '' not null comment '标签名称',
       tag_alias varchar(32) not null default 0 comment '标签别名',
       description varchar(256) not null comment '描述',
       tag_icon varchar(128) null comment '标签 icon',
        create_at datetime not null default current_timestamp() comment '创建时间',
       modify_at datetime not null default current_timestamp() on update current_timestamp() comment '修改时间'
) engine=InnoDB default character set utf8mb4;

create table t_article_tag (
    fk_article_id int not null comment '文章 id',
    fk_tag_id int not null comment '标签 id',
    create_at datetime not null default current_timestamp() comment '创建时间',
    modify_at datetime not null default current_timestamp() on update current_timestamp() comment '修改时间'
) engine=InnoDB default character set utf8mb4;