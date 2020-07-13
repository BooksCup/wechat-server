package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.search.FileItem;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

/**
 * 文件
 *
 * @author zhou
 */
public interface FileItemService {
    /**
     * 保存文件文档
     *
     * @param fileItem 文件文档
     */
    void save(FileItem fileItem);

    /**
     * 根据磁盘名删除文件文档
     *
     * @param diskName 磁盘名
     */
    void deleteFileItemByDiskName(String diskName);

    /**
     * 删除文件索引
     */
    void deleteFileItemIndex();

    /**
     * 搜索文件
     *
     * @param queryBuilder 请求参数
     * @param pageable     分页参数
     * @return 搜索结果
     */
    Page<FileItem> search(QueryBuilder queryBuilder, Pageable pageable);

    /**
     * 复杂查询(高亮 + 拼音)
     *
     * @param searchQuery        请求参数
     * @param highLightFieldList 高亮字段
     * @return 搜索结果
     */
    Page<FileItem> complexSearch(SearchQuery searchQuery, List<String> highLightFieldList);

    /**
     * 补齐搜索
     *
     * @param field  补齐域
     * @param prefix 搜索前缀
     * @param size   搜索结果数量
     * @return 搜索结果列表
     */
    List<String> suggestSearch(String field, String prefix, Integer size);

    /**
     * 获取磁盘名列表
     *
     * @return 磁盘名列表
     */
    List<String> getDiskNameList();
}
