package com.bc.wechat.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.QrCodeContent;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.mapper.UserMapper;
import com.bc.wechat.server.service.OssService;
import com.bc.wechat.server.service.UserService;
import com.bc.wechat.server.utils.CommonUtil;
import com.bc.wechat.server.utils.FileUtil;
import com.bc.wechat.server.utils.QrCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户业务类实现类
 *
 * @author zhou
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private OssService ossService;

    @Resource
    private UserMapper userMapper;

    /**
     * 通过用户名和密码获取用户列表(用于登录)
     *
     * @param paramMap 参数map
     * @return 用户列表
     */
    @Override
    public List<User> getUserByLogin(Map<String, String> paramMap) {
        return userMapper.getUserByLogin(paramMap);
    }

    /**
     * 新增用户
     *
     * @param user 用户
     */
    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    /**
     * 修改用户昵称
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserNickName(Map<String, String> paramMap) {
        userMapper.updateUserNickName(paramMap);
    }

    /**
     * 修改用户微信号
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserWxId(Map<String, String> paramMap) {
        userMapper.updateUserWxId(paramMap);
    }


    /**
     * 修改用户性别
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserSex(Map<String, String> paramMap) {
        userMapper.updateUserSex(paramMap);
    }

    /**
     * 修改用户头像
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserAvatar(Map<String, String> paramMap) {
        userMapper.updateUserAvatar(paramMap);
    }

    /**
     * 修改用户签名
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserSign(Map<String, String> paramMap) {
        userMapper.updateUserSign(paramMap);
    }

    /**
     * 修改朋友圈最新图片
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserLastestCirclePhotos(Map<String, String> paramMap) {
        userMapper.updateUserLastestCirclePhotos(paramMap);
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字  手机号/微信号
     * @return 用户列表
     */
    @Override
    public List<User> getUserByKeyword(String keyword) {
        return userMapper.getUserByKeyword(keyword);
    }

    /**
     * 根据用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Override
    public User getUserByUserId(String userId) {
        List<User> userList = userMapper.getUserByUserId(userId);
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        }
        return new User();
    }

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @Override
    public List<User> getAllUserList() {
        return userMapper.getAllUserList();
    }

    /**
     * 根据用户手机号检查用户是否存在
     *
     * @param userPhone 用户手机号
     * @return true: 存在   false: 不存在
     */
    @Override
    public boolean checkUserExistsByUserPhone(String userPhone) {
        List<User> userList = userMapper.getUserByUserPhone(userPhone);
        if (CollectionUtils.isEmpty(userList)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 刷新用户二维码
     *
     * @param user 用户
     * @return true: 刷新成功  false: 刷新失败
     */
    @Override
    public boolean refreshUserQrCode(User user) {
        boolean result;
        try {
            String downloadPath;
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith(Constant.OS_SHORT_NAME_WINDOWS)) {
                downloadPath = Constant.FILE_UPLOAD_PATH_WINDOWS;
            } else {
                downloadPath = Constant.FILE_UPLOAD_PATH_LINUX;
            }


            String qrCodeFileName = CommonUtil.generateId() + ".png";

            QrCodeContent qrCodeContent = new QrCodeContent();
            qrCodeContent.setType(QrCodeContent.QR_CODE_TYPE_USER);
            Map<String, Object> contentMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            contentMap.put("userId", user.getUserId());
            qrCodeContent.setContentMap(contentMap);

            File qrCodeFile = new File(downloadPath + "/" + qrCodeFileName);

            if (StringUtils.isEmpty(user.getUserAvatar())) {
                QrCodeUtil.genQrCode(JSON.toJSONString(qrCodeContent), 400, 400, qrCodeFile);
            } else {
                String avatarFileName = FileUtil.downLoadFromUrl(user.getUserAvatar(), downloadPath);
                String logoPath = downloadPath + "/" + avatarFileName;
                BufferedImage image = QrCodeUtil.genQrCodeWithAvatar(JSON.toJSONString(qrCodeContent), 400, 400, logoPath);
                if (!ImageIO.write(image, "png", qrCodeFile)) {
                    logger.error("could not write an image of format");
                    return false;
                }
            }


            // 上传至OSS
            String qrcode = ossService.putObject("erp-wd-com", qrCodeFileName, qrCodeFile);
//            String qrCode = fileServer + "/" + qrCodeFileName;
            logger.info("qrcode: " + qrcode);

            // 更新db字段
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", user.getUserId());
            paramMap.put("userQrCode", qrcode);
            userMapper.updateUserQrCode(paramMap);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
