package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.SysLog;

/**
 * 系统日志
 *
 * @author zhou
 */
public interface SysLogMapper {

    /**
     * 新增系统日志
     *
     * @param sysLog 系统日志
     */
    void addSysLog(SysLog sysLog);

}
