package com.bc.wechat.server.controller.admin;

import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Region;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.RegionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 地区
 *
 * @author zhou
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/region")
public class RegionController {

    @Resource
    private RegionService regionService;

    /**
     * 添加地区
     *
     * @param name     地区名
     * @param code     区号
     * @param level    层级
     * @param parentId 父地区ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "添加地区", notes = "添加地区")
    @PostMapping(value = "")
    public ResponseEntity<Region> addRegion(
            @RequestParam String name,
            @RequestParam String code,
            @RequestParam String level,
            @RequestParam String parentId) {
        ResponseEntity<Region> responseEntity;
        try {
            Region region = new Region(parentId, level, name, code);

            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("level", level);
            paramMap.put("parentId", parentId);
            long regionCount = regionService.getRegionCount(paramMap);

            region.setSeq(regionCount + 1);

            regionService.addRegion(region);
            responseEntity = new ResponseEntity<>(region, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new Region(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询地区分页信息
     *
     * @param page     当前分页数
     * @param limit    分页大小
     * @param level    层级
     * @param parentId 父地区ID
     * @return 地区分页信息
     */
    @ApiOperation(value = "查询地区分页信息", notes = "查询地区分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<Region>> getRegionPageInfo(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @RequestParam String level,
            @RequestParam String parentId) {
        ResponseEntity<PageInfo<Region>> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("level", level);
            paramMap.put("parentId", parentId);
            PageInfo<Region> regionPageInfo = regionService.getRegionPageInfo(page, limit, paramMap);
            responseEntity = new ResponseEntity<>(regionPageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除地区
     *
     * @param regionId 地区ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "删除地区", notes = "删除地区")
    @DeleteMapping(value = "/{regionId}")
    public ResponseEntity<String> deleteRegion(@PathVariable String regionId) {

        ResponseEntity<String> responseEntity;
        try {
            regionService.deleteRegion(regionId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_REGION_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_REGION_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
