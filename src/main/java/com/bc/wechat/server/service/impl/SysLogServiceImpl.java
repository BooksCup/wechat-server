package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.SysLog;
import com.bc.wechat.server.mapper.SysLogMapper;
import com.bc.wechat.server.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统日志业务类实现类
 *
 * @author zhou
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 新增系统日志
     *
     * @param sysLog 系统日志
     */
    @Override
    public void addSysLog(SysLog sysLog) {
        sysLogMapper.addSysLog(sysLog);
    }
}
