package com.bc.wechat.server.service;

import org.springframework.http.ResponseEntity;

public interface MessageService {

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
    ResponseEntity<String> sendMessage(String targetType,
                                              String targetId, String fromId, String messageType, String body);
}
