package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.PeopleNearby;
import com.bc.wechat.server.service.PeopleNearbyService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @ApiOperation(value = "上传位置信息并获取附近的人列表", notes = "上传位置信息并获取附近的人列表")
    @PostMapping(value = "")
    public ResponseEntity<String> getPeopleNearbyList(
            @RequestParam String userId,
            @RequestParam String longitude,
            @RequestParam String latitude,
            @RequestParam(required = false) String district) {
        ResponseEntity<String> responseEntity;
        try {
            PeopleNearby peopleNearby = new PeopleNearby(userId, longitude, latitude, district);
            peopleNearbyService.getPeopleNearbyList(peopleNearby);
            responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[getPeopleNearbyList] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
