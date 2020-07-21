package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.search.SearchHistory;
import com.bc.wechat.server.mapper.SearchHistoryMapper;
import com.bc.wechat.server.service.SearchHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 搜索历史
 *
 * @author zhou
 */
@Service("searchHistoryService")
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    /**
     * 获取热门搜索列表
     *
     * @param topSize 列表条数
     * @return 热门搜索列表
     */
    @Override
    public List<SearchHistory> getHotSearchHistoryList(Integer topSize) {
        return searchHistoryMapper.getHotSearchHistoryList(topSize);
    }
}
