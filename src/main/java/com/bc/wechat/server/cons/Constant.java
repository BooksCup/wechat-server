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
    public static final String CONTACTS_FROM_PHONE = "1";

    /**
     * 来自微信号搜索
     */
    public static final String CONTACTS_FROM_WX_ID = "2";

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
     */
    public static final String PRIVACY_CHATS_MOMENTS_WERUN_ETC = "0";

    /**
     * 朋友权限（仅聊天）
     */
    public static final String PRIVACY_CHATS_ONLY = "1";

    /**
     * 朋友圈和视频动态-可以看我
     */
    public static final String SHOW_MY_POSTS = "0";

    /**
     * 朋友圈时视频动态-不让他看我
     */
    public static final String HIDE_MY_POSTS = "1";

    /**
     * 朋友圈和视频动态-可以看他
     */
    public static final String SHOW_HIS_POSTS = "0";

    /**
     * 朋友圈时视频动态-不看他
     */
    public static final String HIDE_HIS_POSTS = "1";


    // 系统日志类型
    /**
     * 登录
     */
    public static final String SYS_LOG_TYPE_LOG_IN = "LOG_IN";

    /**
     * 登出
     */
    public static final String SYS_LOG_TYPE_LOG_OUT = "LOG_OUT";

    /**
     * 用户微信号修改标记
     */
    public static final String USER_WX_ID_MODIFY_FLAG_TRUE = "1";

    /**
     * 协议-"http"
     */
    public static final String PROTOCOL_HTTP_PREFIX = "http://";

    /**
     * 协议-"https"
     */
    public static final String PROTOCOL_HTTPS_PREFIX = "https://";

    /**
     * 排序-升序
     */
    public static final String SORT_DIRECTION_ASC = "asc";

    /**
     * 排序-倒序
     */
    public static final String SORT_DIRECTION_DESC = "desc";

    /**
     * 高亮-开启
     */
    public static final String HIGHLIGHT_FLAG_OPEN = "1";

    /**
     * 高亮-关闭
     */
    public static final String HIGHLIGHT_FLAG_CLOSE = "0";

    /**
     * 字段类型-拼音
     */
    public static final String FIELD_TYPE_PINYIN = ".pinyin";

    /**
     * index-'file_item'
     */
    public static final String INDEX_NAME_FILE_ITEM = "file_item";

    /**
     * type-'file_item'
     */
    public static final String TYPE_NAME_FILE_ITEM = "file_item";

    // 登录方式
    /**
     * 手机号/密码登录
     */
    public static final String LOGIN_TYPE_PHONE_AND_PASSWORD = "0";

    /**
     * 手机号/验证码登录
     */
    public static final String LOGIN_TYPE_PHONE_AND_VERIFICATION_CODE = "1";

    /**
     * 微信号/QQ/邮箱登录
     */
    public static final String LOGIN_TYPE_OTHER_ACCOUNTS_AND_PASSWORD = "2";

    /**
     * 验证码业务类型-"登录"
     */
    public static final String VERIFICATION_CODE_SERVICE_TYPE_LOGIN = "0";

    /**
     * QQ号验证
     */
    /**
     * 未绑定
     */
    public static final String QQ_ID_NOT_LINK = "0";

    /**
     * 已绑定
     */
    public static final String QQ_ID_LINKED = "1";


    /**
     * 邮箱验证
     */
    /**
     * 未绑定
     */
    public static final String EMAIL_NOT_LINK = "0";

    /**
     * 未验证
     */
    public static final String EMAIL_NOT_VERIFIED = "1";

    /**
     * 已验证
     */
    public static final String EMAIL_VERIFIED = "2";

    public static final String HOT_SEARCH_TOP_SIZE = "5";

    public static final String SPECIAL_USER_ID_WEIXIN = "00000000000000000000000000000000";
    public static final String SPECIAL_USER_ID_FILEHELPER = "00000000000000000000000000000001";

    // 用户类型
    /**
     * 普通注册用户
     */
    public static final String USER_TYPE_REG = "REG";

    /**
     * 微信团队
     */
    public static final String USER_TYPE_WEIXIN = "WEIXIN";

    /**
     * 文件传输助手
     */
    public static final String USER_TYPE_FILEHELPER = "FILEHELPER";
}
