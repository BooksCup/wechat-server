package com.bc.wechat.server.controller.search;

import com.bc.wechat.server.entity.search.SearchHistory;
import com.bc.wechat.server.service.SearchHistoryService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史
 *
 * @author zhou
 */
@RestController
@RequestMapping("/searchHistory")
public class SearchHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(SearchHistoryController.class);

    @Resource
    private SearchHistoryService searchHistoryService;

    /**
     * 获取热门搜索列表
     *
     * @return 热门搜索列表
     */
    @ApiOperation(value = "获取热门搜索列表", notes = "获取热门搜索列表")
    @GetMapping(value = "/hot")
    public ResponseEntity<List<SearchHistory>> getHotSearchHistoryList() {
        ResponseEntity<List<SearchHistory>> responseEntity;
        try {
            List<SearchHistory> searchHistoryList = searchHistoryService.getHotSearchHistoryList();
            responseEntity = new ResponseEntity<>(searchHistoryList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getHotSearchHistoryList] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
