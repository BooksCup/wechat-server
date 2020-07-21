package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.search.SearchHistory;

import java.util.List;

/**
 * 搜索历史
 *
 * @author zhou
 */
public interface SearchHistoryService {
    /**
     * 获取热门搜索列表
     *
     * @param topSize 列表条数
     * @return 热门搜索列表
     */
    List<SearchHistory> getHotSearchHistoryList(Integer topSize);
}
