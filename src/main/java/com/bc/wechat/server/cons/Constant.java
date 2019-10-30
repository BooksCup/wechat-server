package com.bc.wechat.server.cons;


/**
 * 常量类
 *
 * @author zhou
 */
public class Constant {
    /**
     * 初始化hashmap容量
     */
    public static final int DEFAULT_HASH_MAP_CAPACITY = 16;

    public static final String IS_NOT_FRIEND = "0";
    public static final String IS_FRIEND = "1";

    /**
     * 文件存放路径
     */
    public static final String FILE_UPLOAD_PATH = "D://tool//apache-tomcat-8.5.33//webapps//wechat_file";

    // 用于推送的业务类型
    /**
     * 好友申请
     */
    public static final String PUSH_SERVICE_TYPE_ADD_FRIENDS_APPLY = "ADD_FRIENDS_APPLY";

    public static final String MSG_TYPE_TEXT = "text";
    public static final String MSG_TYPE_IMAGE = "image";
    public static final String MSG_TYPE_VOICE = "voice";
    public static final String MSG_TYPE_CUSTOM = "custom";

    public static final String TARGET_TYPE_SINGLE = "single";
    public static final String TARGET_TYPE_GROUP = "group";
    public static final String TARGET_TYPE_CHATROOM = "chatroom";
}
