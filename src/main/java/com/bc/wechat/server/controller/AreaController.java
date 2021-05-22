package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.entity.area.Province;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.AreaService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * 地区
 *
 * @author zhou
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    /**
     * 初始化省市区
     *
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "初始化省市区", notes = "初始化省市区")
    @PostMapping(value = "")
    public ResponseEntity<String> initArea() {
        logger.info("[initArea] begin... ");
        long beginTimeStamp = System.currentTimeMillis();
        ResponseEntity<String> responseEntity;
        try {
            File file = ResourceUtils.getFile("classpath:area.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            List<Province> provinceList = JSONArray.parseArray(sb.toString(), Province.class);
            int provinceSeq = 0;
            for (Province province : provinceList) {
                provinceSeq++;
                Province p = new Province(province.getName(), Float.valueOf(provinceSeq));
                areaService.addProvince(p);

                int citySeq = 0;
                List<City> cityList = province.getCity();
                for (City city : cityList) {
                    citySeq++;
                    City c = new City(p.getId(), city.getName(), Float.valueOf(citySeq));
                    areaService.saveCity(c);
//                    List<String> districtList = city.getArea();
//                    int districtSeq = 0;
//                    for (String district : districtList) {
//                        districtSeq++;
//                        District d = new District(c.getId(), district, "", districtSeq);
//                        areaService.saveDistrict(d);
//                    }
                }

            }
            long endTimeStamp = System.currentTimeMillis();
            logger.info("[initArea] finish. cost: " + (endTimeStamp - beginTimeStamp) + "ms.");
            responseEntity = new ResponseEntity<>(ResponseMsg.INIT_AREA_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[initArea] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.INIT_AREA_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 初始化省市区JSON
     *
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "初始化省市区JSON", notes = "初始化省市区JSON")
    @PostMapping(value = "/json")
    public ResponseEntity<String> initAreaJson() {
        logger.info("[initArea] begin... ");
        long beginTimeStamp = System.currentTimeMillis();
        ResponseEntity<String> responseEntity;
        try {
            List<Province> provinceList = areaService.getProvinceList();
            for (Province province : provinceList) {
                List<City> cityList = areaService.getCityListByProvinceId(province.getId());
                province.setCity(cityList);
                for (City city : cityList) {
                    List<District> districtList = areaService.getDistrictListByCityId(city.getId());
                    city.setDistrict(districtList);
                }
            }
            File file = ResourceUtils.getFile("classpath:area-wx.json");
            logger.info("json file's path: " + file.getPath());
            FileWriter fw = new FileWriter(file);
            String jsonContent = JSON.toJSONString(provinceList);
            fw.write(jsonContent);
            fw.flush();
            fw.close();

            long endTimeStamp = System.currentTimeMillis();
            logger.info("[initAreaJson] finish. cost: " + (endTimeStamp - beginTimeStamp) + "ms.");
            responseEntity = new ResponseEntity<>(ResponseMsg.INIT_AREA_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[initAreaJson] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.INIT_AREA_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
