package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.search.SearchHistory;

import java.util.List;

/**
 * 搜索历史
 *
 * @author zhou
 */
public interface SearchHistoryMapper {

    /**
     * 获取热门搜索列表
     *
     * @return 热门搜索列表
     */
    List<SearchHistory> getHotSearchHistoryList();
}
