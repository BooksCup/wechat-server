package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.Message;

/**
 * 消息
 *
 * @author zhou
 */
public interface MessageMapper {

    /**
     * 新增消息
     *
     * @param message 消息
     */
    void addMessage(Message message);
}
