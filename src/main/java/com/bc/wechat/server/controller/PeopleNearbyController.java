package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.PeopleNearby;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.PeopleNearbyService;
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
 * 附近的人
 *
 * @author zhou
 */
@RestController
@RequestMapping("/peopleNearby")
public class PeopleNearbyController {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PeopleNearbyController.class);

    @Resource
    private PeopleNearbyService peopleNearbyService;

    /**
     * 上传位置信息并获取附近的人列表
     *
     * @param userId    用户ID
     * @param longitude 经度
     * @param latitude  纬度
     * @param district  区县信息
     * @return 附近的人列表
     */
    @ApiOperation(value = "上传位置信息并获取附近的人列表", notes = "上传位置信息并获取附近的人列表")
    @PostMapping(value = "")
    public ResponseEntity<List<PeopleNearby>> getPeopleNearbyList(
            @RequestParam String userId,
            @RequestParam String longitude,
            @RequestParam String latitude,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String userSex) {
        ResponseEntity<List<PeopleNearby>> responseEntity;
        try {
            PeopleNearby peopleNearby = new PeopleNearby(userId, longitude, latitude, district, userSex);
            List<PeopleNearby> peopleNearbyList = peopleNearbyService.getPeopleNearbyList(peopleNearby);
            responseEntity = new ResponseEntity<>(peopleNearbyList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[getPeopleNearbyList] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 清除某个用户的位置信息
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "清除位置信息", notes = "清除位置信息")
    @DeleteMapping(value = "")
    public ResponseEntity<String> deletePositionInfo(@RequestParam String userId) {
        ResponseEntity<String> responseEntity;
        try {
            peopleNearbyService.deletePositionInfo(userId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_POSITION_INFO_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[deletePositionInfo] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_POSITION_INFO_SUCCESS.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
