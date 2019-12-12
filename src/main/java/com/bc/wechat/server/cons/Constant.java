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

    public static final String OS_SHORT_NAME_WINDOWS = "win";

    /**
     * 文件存放路径-windows
     */
    public static final String FILE_UPLOAD_PATH_WINDOWS = "D://tool//apache-tomcat-8.5.33//webapps//wechat_file";

    /**
     * 文件存放路径-linux
     */
    public static final String FILE_UPLOAD_PATH_LINUX = "/usr/local/nginx/html/wechat_file";

    /**
     * 文件服务器地址-windows
     */
    public static final String FILE_SERVER_WINDOWS = "http://192.168.0.153:8080/wechat_file";


    /**
     * 文件服务器地址-linux
     */
    public static final String FILE_SERVER_LINUX = "http://49.4.25.11:9999/wechat_file";

    // 用于推送的业务类型
    /**
     * 好友申请
     */
    public static final String PUSH_SERVICE_TYPE_ADD_FRIENDS_APPLY = "ADD_FRIENDS_APPLY";

    // 消息类型
    /**
     * 文字
     */
    public static final String MSG_TYPE_TEXT = "text";

    /**
     * 图片
     */
    public static final String MSG_TYPE_IMAGE = "image";

    /**
     * 位置
     */
    public static final String MSG_TYPE_LOCATION = "location";

    /**
     * 语音
     */
    public static final String MSG_TYPE_VOICE = "voice";

    /**
     * 自定义消息
     */
    public static final String MSG_TYPE_CUSTOM = "custom";

    public static final String TARGET_TYPE_SINGLE = "single";
    public static final String TARGET_TYPE_GROUP = "group";
    public static final String TARGET_TYPE_CHATROOM = "chatroom";

    //是否群主
    /**
     * 群主
     */
    public static final int IM_GROUP_OWNER = 1;

    /**
     * 普通成员
     */
    public static final int IM_GROUP_NOT_OWNER = 0;

    /**
     * 高危操作密码
     */
    public static final String HIGH_RISK_OPER_PASSWORD = "1qaz2wsx";
}
