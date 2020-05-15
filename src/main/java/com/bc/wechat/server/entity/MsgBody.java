package com.bc.wechat.server.entity;


import java.util.Map;

/**
 * @author zhou
 */
public class MsgBody {
    /**
     * msg_type_text
     * 消息内容
     */
    private String text;

    /**
     * 选填的json对象 开发者可以自定义extras里面的key value
     */
    private Map<String, String> extras;

    private String imageUrl;

    /**
     * msg_type_image(独立)
     * 图片原始宽度
     */
    private Integer width;

    /**
     * 图片原始高度
     */
    private Integer height;

    /**
     * 图片格式,如"jpg"
     */
    private String format;

    /**
     * msg_type_voice(独立)
     * 音频时长
     */
    private Integer duration;

    /**
     * msg_type_image和msg_type_voice(共有)
     * 文件上传之后服务器端所返回的key，用于之后生成下载的url（必填）
     */
    private String mediaId;

    /**
     * 文件的crc32校验码，用于下载大图的校验 （必填）
     */
    private Long mediaCrc32;

    /**
     * 图片或者音频hash值
     */
    private String hash;

    /**
     * 文件大小（字节数）
     */
    private Integer fsize;


    // 位置相关
    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 地址
     */
    private String address;

    /**
     * 详细地址
     */
    private String addressDetail;

    /**
     * 地图缩率图http路径
     */
    private String path;


    public MsgBody() {

    }

    public MsgBody(String text, Map<String, String> extras, Integer width, Integer height,
                   String format, Integer duration, String mediaId, Long mediaCrc32, String hash, Integer fsize) {
        this.text = text;
        this.extras = extras;
        this.width = width;
        this.height = height;
        this.format = format;
        this.duration = duration;
        this.mediaId = mediaId;
        this.mediaCrc32 = mediaCrc32;
        this.hash = hash;
        this.fsize = fsize;
    }

    public static MsgBody.Builder newBuilder() {
        return new MsgBody.Builder();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Long getMediaCrc32() {
        return mediaCrc32;
    }

    public void setMediaCrc32(Long mediaCrc32) {
        this.mediaCrc32 = mediaCrc32;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class Builder {
        private String text;
        private Map<String, String> extras;
        private Integer width;
        private Integer height;
        private String format;
        private Integer duration;
        private String mediaId;
        private Long mediaCrc32;
        private String hash;
        private Integer fsize;

        public Builder() {
        }

        public MsgBody.Builder setText(String text) {
            this.text = text.trim();
            return this;
        }

        public MsgBody.Builder setExtras(Map<String, String> extras) {
            this.extras = extras;
            return this;
        }

        public MsgBody.Builder setWidth(Integer width) {
            this.width = width;
            return this;
        }

        public MsgBody.Builder setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public MsgBody.Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        public MsgBody.Builder setDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public MsgBody.Builder setMediaId(String mediaId) {
            this.mediaId = mediaId;
            return this;
        }

        public MsgBody.Builder setMediaCrc32(Long mediaCrc32) {
            this.mediaCrc32 = mediaCrc32;
            return this;
        }

        public MsgBody.Builder setHash(String hash) {
            this.hash = hash;
            return this;
        }

        public MsgBody.Builder setFsize(Integer fsize) {
            this.fsize = fsize;
            return this;
        }

        public MsgBody build() {
            return new MsgBody(this.text, this.extras, this.width, this.height,
                    this.format, this.duration, this.mediaId, this.mediaCrc32, this.hash, this.fsize);
        }
    }
}
