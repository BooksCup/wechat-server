package com.bc.wechat.server.controller.admin;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.Province;
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
 * "地区" - "市"
 *
 * @author zhou
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/city")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Resource
    AreaService areaService;

    /**
     * 新增市
     *
     * @param provinceId 省ID
     * @param name       市名
     * @return 新增结果
     */
    @ApiOperation(value = "新增市", notes = "新增市")
    @PostMapping(value = "")
    public ResponseEntity<City> addCity(
            @RequestParam String provinceId,
            @RequestParam String name) {
        ResponseEntity<City> responseEntity;
        try {
            City city = new City(provinceId, name, null);
            long cityCount = areaService.getCityCountByProvinceId(provinceId);
            city.setSeq(cityCount + 1.0f);
            areaService.saveCity(city);
            responseEntity = new ResponseEntity<>(city, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new City(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改省
     *
     * @param provinceId 省ID
     * @param name       省名
     * @param seq        排序
     * @return 修改结果
     */
    @ApiOperation(value = "修改省", notes = "修改省")
    @PutMapping(value = "/{provinceId}")
    public ResponseEntity<String> updateProvince(
            @PathVariable String provinceId,
            @RequestParam String name,
            @RequestParam Float seq) {
        ResponseEntity<String> responseEntity;
        try {
            Province province = new Province(name, seq);
            province.setId(provinceId);
            areaService.updateProvince(province);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询市分页信息
     *
     * @param page  当前分页数
     * @param limit 分页大小
     * @return 省分页信息
     */
    @ApiOperation(value = "查询市分页信息", notes = "查询市分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<City>> getProvincePageInfo(
            @RequestParam String provinceId,
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        ResponseEntity<PageInfo<City>> responseEntity;
        try {
            PageInfo<City> pageInfo = areaService.getCityPageInfo(page, limit, provinceId);
            responseEntity = new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除省
     *
     * @param provinceId 省ID
     * @return 删除结果
     */
    @ApiOperation(value = "删除省", notes = "删除省")
    @DeleteMapping(value = "/{provinceId}")
    public ResponseEntity<String> deleteProvince(@PathVariable String provinceId) {
        ResponseEntity<String> responseEntity;
        try {
            areaService.deleteProvince(provinceId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 刷新省排序
     *
     * @return 刷新结果
     */
    @ApiOperation(value = "刷新省排序", notes = "刷新省排序")
    @PostMapping(value = "/refresh")
    public ResponseEntity<String> refreshProvince() {
        ResponseEntity<String> responseEntity;
        try {
            List<Province> provinceList = areaService.getProvinceList();
            List<Province> refreshProvinceList = new ArrayList<>();
            for (int i = 0; i < provinceList.size(); i++) {
                Province province = provinceList.get(i);
                province.setSeq(i + 1.0f);
                refreshProvinceList.add(province);
            }
            areaService.batchUpdateProvinceSeq(refreshProvinceList);
            responseEntity = new ResponseEntity<>(ResponseMsg.REFRESH_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.REFRESH_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}