package com.bc.wechat.server.controller.admin;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.AreaService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * "地区" - "区县"
 *
 * @author zhou
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/district")
public class DistrictController {

    private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

    @Resource
    AreaService areaService;

    /**
     * 新增区县
     *
     * @param cityId 市ID
     * @param name   区县名
     * @return 新增结果
     */
    @ApiOperation(value = "新增区县", notes = "新增区县")
    @PostMapping(value = "")
    public ResponseEntity<District> addDistrict(
            @RequestParam String cityId,
            @RequestParam(required = false) String postCode,
            @RequestParam String name) {
        ResponseEntity<District> responseEntity;
        try {
            District district = new District(cityId, name, postCode, null);
            long districtCount = areaService.getDistrictCountByCityId(cityId);
            district.setSeq(districtCount + 1.0f);
            areaService.addDistrict(district);
            responseEntity = new ResponseEntity<>(district, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new District(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改区县
     *
     * @param districtId 区县ID
     * @param name       区县名
     * @param seq        排序
     * @return 修改结果
     */
    @ApiOperation(value = "修改区县", notes = "修改区县")
    @PutMapping(value = "/{districtId}")
    public ResponseEntity<String> updateDistrict(
            @PathVariable String districtId,
            @RequestParam String name,
            @RequestParam String postCode,
            @RequestParam Float seq) {
        ResponseEntity<String> responseEntity;
        try {
            District district = new District(name, postCode, seq);
            district.setId(districtId);
            areaService.updateDistrict(district);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询区县分页信息
     *
     * @param cityId 市ID
     * @param page   当前分页数
     * @param limit  分页大小
     * @return 区县分页信息
     */
    @ApiOperation(value = "查询区县分页信息", notes = "查询区县分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<District>> getDistrictPageInfo(
            @RequestParam String cityId,
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        ResponseEntity<PageInfo<District>> responseEntity;
        try {
            PageInfo<District> pageInfo = areaService.getDistrictPageInfo(page, limit, cityId);
            responseEntity = new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除区县
     *
     * @param districtId 区县ID
     * @return 删除结果
     */
    @ApiOperation(value = "删除区县", notes = "删除区县")
    @DeleteMapping(value = "/{districtId}")
    public ResponseEntity<String> deleteDistrict(@PathVariable String districtId) {
        ResponseEntity<String> responseEntity;
        try {
            areaService.deleteDistrict(districtId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 刷新区县排序
     *
     * @param cityId 市ID
     * @return 刷新结果
     */
    @ApiOperation(value = "刷新区县排序", notes = "刷新区县排序")
    @PostMapping(value = "/refresh")
    public ResponseEntity<String> refreshCity(@RequestParam String cityId) {
        ResponseEntity<String> responseEntity;
        try {
            List<District> districtList = areaService.getDistrictListByCityId(cityId);
            List<District> refreshDistrictList = new ArrayList<>();
            for (int i = 0; i < districtList.size(); i++) {
                District district = districtList.get(i);
                district.setSeq(i + 1.0f);
                refreshDistrictList.add(district);
            }
            areaService.batchUpdateDistrictSeq(refreshDistrictList);
            responseEntity = new ResponseEntity<>(ResponseMsg.REFRESH_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.REFRESH_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}