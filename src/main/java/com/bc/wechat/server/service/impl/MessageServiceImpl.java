package com.bc.wechat.server.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;
import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Message;
import com.bc.wechat.server.entity.MsgBody;
import com.bc.wechat.server.service.MessageService;
import com.bc.wechat.server.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息业务类实现类
 *
 * @author zhou
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private JMessageClient jMessageClient;

    /**
     * 发送single消息
     *
     * @param targetType  发送目标类型
     * @param targetId    目标id single填username group 填Group id chatroom 填chatroomid（必填）
     * @param fromId      发送者的username （必填）
     * @param messageType 消息类型
     * @param body        消息体
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<String> sendMessage(String targetType,
                                              String targetId, String fromId, String messageType, String body) {
        ResponseEntity<String> responseEntity;
        try {
            // version 版本号 目前是1 （必填）
            // target_type 发送目标类型 single - 个人，group - 群组 chatroom - 聊天室（必填）
            // target_id 目标id single填username group 填Group id chatroom
            // 填chatroomid（必填）
            // from_type 发送消息者身份 当前只限admin用户，必须先注册admin用户 （必填）
            // fromId 发送者的username （必填）
            MsgBody msgBody = JSON.parseObject(body, MsgBody.class);
            Message message = new Message();
            SendMessageResult sendMessageResult = null;

            String jimTargetId = targetId;

            if (Constant.MSG_TYPE_TEXT.equals(messageType)) {
                MessagePayload payload = MessagePayload.newBuilder().setVersion(1)
                        .setTargetType(targetType).setTargetId(jimTargetId).setFromType("admin")
                        .setFromId(fromId).setMessageType(MessageType.TEXT)
                        .setMessageBody(CommonUtil.generateMessageBody(msgBody, messageType))
                        // App不接收通知
                        .setNoNotification(true)
                        .build();

                // 文字消息
                sendMessageResult = jMessageClient.sendMessage(payload);

                message.setjId(sendMessageResult.getMsg_id());
                message.setjCreateTime(sendMessageResult.getMsgCtime());
            } else if (Constant.MSG_TYPE_IMAGE.equals(messageType)) {
                MessagePayload payload = MessagePayload.newBuilder().setVersion(1)
                        .setTargetType(targetType).setTargetId(jimTargetId).setFromType("admin")
                        .setFromId(fromId).setMessageType(MessageType.IMAGE)
                        .setMessageBody(CommonUtil.generateMessageBody(msgBody, messageType))
                        // App不接收通知
                        .setNoNotification(true)
                        .build();

                sendMessageResult = jMessageClient.sendMessage(payload);

                message.setjId(sendMessageResult.getMsg_id());
                message.setjCreateTime(sendMessageResult.getMsgCtime());
                // 极光发送图片如果通过服务器发,会获取不到messageId
                // 格式类似于下面这种
                // "msgId_1538963584988297"
                // 只能通过客户端发,发送完图片后将这个ID发送给服务器做持久化
                // 服务器不再调极光发送图片接口
                // 接收极光回调的消息ID和消息时间戳
                // 废弃
            } else if (Constant.MSG_TYPE_VOICE.equals(messageType)) {
                // 语音消息
            }

            message.setMsgType(messageType);
            message.setFromId(fromId);
            message.setTargetId(targetId);
            message.setBody(body);
            message.setTargetType(targetType);
            // 防止消息乱序
            message.setCreateTime(CommonUtil.now());


            responseEntity = new ResponseEntity<>(
                    sendMessageResult.getOriginalContent(), HttpStatus.OK);

        } catch (APIConnectionException e) {
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (APIRequestException e) {
            logger.error("status: " + e.getStatus() + ",errorCode: "
                    + e.getErrorCode() + ",errorMessage: "
                    + e.getErrorMessage());
            responseEntity = new ResponseEntity<>(e.getErrorMessage(),
                    HttpStatus.valueOf(e.getStatus()));
        }
        return responseEntity;
    }
}
