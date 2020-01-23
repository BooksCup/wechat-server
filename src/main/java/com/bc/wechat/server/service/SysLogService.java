package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.SysLog;

/**
 * 系统日志业务类接口
 *
 * @author zhou
 */
public interface SysLogService {

    /**
     * 新增系统日志
     *
     * @param sysLog 系统日志
     */
    void addSysLog(SysLog sysLog);
}
