package com.bc.wechat.server.controller;

import com.bc.wechat.server.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/messages")
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * 发送消息
     * @param targetType 发送目标类型 single:个人 group:群组 chatroom:聊天室
     * @param targetId 目标id single填username group填Groupid chatroom填chatroomid
     * @param fromId 发送者的userId
     * @param msgType 发消息类型 text:文本，image:图片, custom:自定义消息(msg_body为json对象即可，服务端不做校验) voice:语音
     * @param body Json对象的消息体 限制为4096byte
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "发送消息", notes = "发送消息")
    @PostMapping(value = "")
    public ResponseEntity<String> addIMMessages(
            @RequestParam String targetType,
            @RequestParam String targetId,
            @RequestParam String fromId,
            @RequestParam String msgType,
            @RequestParam String body) {
        return messageService.sendMessage(targetType, targetId, fromId,
                msgType, body);
    }
}
