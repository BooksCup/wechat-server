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

    /**
     * 好友接收
     */
    public static final String PUSH_SERVICE_TYPE_ADD_FRIENDS_ACCEPT = "ADD_FRIENDS_ACCEPT";

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

    // 好友来源
    /**
     * 来自手机号搜索
     */
    public static final String FRIENDS_SOURCE_BY_PHONE = "1";

    /**
     * 来自微信号搜索
     */
    public static final String FRIENDS_SOURCE_BY_WX_ID = "2";

    // 关系状态
    /**
     * 路人
     */
    public static final String RELA_STATUS_STRANGER = "0";

    /**
     * 好友
     */
    public static final String RELA_STATUS_FRIEND = "1";

    /**
     * 黑名单
     */
    public static final String RELA_STATUS_BLACK_LIST = "2";


    /**
     * 朋友权限（所有权限：聊天、朋友圈、微信运动等）
     * default
     */
    public static final String RELA_AUTH_ALL = "0";

    /**
     * 朋友权限（仅聊天）
     */
    public static final String RELA_AUTH_ONLY_CHAT = "1";

    /**
     * 朋友圈和视频动态-可以看我
     * default
     */
    public static final String RELA_CAN_SEE_ME = "0";

    /**
     * 朋友圈时视频动态-不让他看我
     */
    public static final String RELA_NOT_SEE_ME = "1";

    /**
     * 朋友圈和视频动态-可以看他
     * default
     */
    public static final String RELA_CAN_SEE_HIM = "0";

    /**
     * 朋友圈时视频动态-不看他
     */
    public static final String RELA_NOT_SEE_HIM = "1";
}
