package com.bc.wechat.server.controller.admin;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Region;
import com.bc.wechat.server.entity.area.Province;
import com.bc.wechat.server.service.AreaService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * "地区" - "省"
 *
 * @author zhou
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/province")
public class ProvinceController {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Resource
    AreaService areaService;

    /**
     * 新增省
     *
     * @param name 省名
     * @return 新增结果
     */
    @ApiOperation(value = "新增省", notes = "新增省")
    @PostMapping(value = "")
    public ResponseEntity<Province> addProvince(
            @RequestParam String name) {
        ResponseEntity<Province> responseEntity;
        try {
            Province province = new Province(name, null);
            long provinceCount = areaService.getProvinceCount();
            province.setSeq(provinceCount + 1.0f);
            areaService.addProvince(province);
            responseEntity = new ResponseEntity<>(province, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new Province(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询省分页信息
     *
     * @param page  当前分页数
     * @param limit 分页大小
     * @return 省分页信息
     */
    @ApiOperation(value = "查询省分页信息", notes = "查询省分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<Province>> getProvincePageInfo(
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        ResponseEntity<PageInfo<Province>> responseEntity;
        try {
            PageInfo<Province> pageInfo = areaService.getProvincePageInfo(page, limit);
            responseEntity = new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}