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
