package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.Address;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.AddressService;
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
 * 用户地址
 *
 * @author zhou
 */
@RestController
@RequestMapping("/users")
public class UserAddressController {

    private static final Logger logger = LoggerFactory.getLogger(UserAddressController.class);

    @Resource
    AddressService addressService;

    /**
     * 获取用户的地址列表
     *
     * @param userId 用户ID
     * @return 地址列表
     */
    @ApiOperation(value = "获取用户的地址列表", notes = "获取用户的地址列表")
    @GetMapping(value = "/{userId}/address")
    public ResponseEntity<List<Address>> getAddressListByUserId(
            @PathVariable String userId) {
        ResponseEntity<List<Address>> responseEntity;
        try {
            List<Address> addressList = addressService.getAddressListByUserId(userId);
            responseEntity = new ResponseEntity<>(addressList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getAddressListByUserId] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 保存地址
     *
     * @param userId   用户ID
     * @param name     收货人
     * @param phone    手机号码
     * @param province 地区信息-省
     * @param city     地区信息-市
     * @param district 地区信息-区
     * @param detail   详细地址
     * @param postCode 邮政编码
     * @return
     */
    @ApiOperation(value = "保存地址", notes = "保存地址")
    @PostMapping(value = "/{userId}/address")
    public ResponseEntity<String> addAddress(
            @PathVariable String userId,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String province,
            @RequestParam String city,
            @RequestParam String district,
            @RequestParam String detail,
            @RequestParam(required = false) String postCode) {
        ResponseEntity<String> responseEntity;
        try {
            Address address = new Address(userId, name, phone, province,
                    city, district, detail, postCode);
            addressService.addAddress(address);
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[addAddress] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改地址
     *
     * @param userId    用户ID
     * @param addressId 地址ID
     * @param name      收货人
     * @param phone     手机号码
     * @param province  地区信息-省
     * @param city      地区信息-市
     * @param district  地区信息-区
     * @param detail    详细地址
     * @param postCode  邮政编码
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改地址", notes = "修改地址")
    @PutMapping(value = "/{userId}/address/{addressId}")
    public ResponseEntity<String> modifyAddress(
            @PathVariable String userId,
            @PathVariable String addressId,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String province,
            @RequestParam String city,
            @RequestParam String district,
            @RequestParam String detail,
            @RequestParam(required = false) String postCode) {
        ResponseEntity<String> responseEntity;
        try {
            Address address = new Address(userId, addressId, name, phone, province,
                    city, district, detail, postCode);
            addressService.modifyAddress(address);
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[modifyAddress] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改地址
     *
     * @param addressId 地址ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @DeleteMapping(value = "/{userId}/address/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable String addressId) {
        ResponseEntity<String> responseEntity;
        try {
            addressService.deleteAddress(addressId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[deleteAddress] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}