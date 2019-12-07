package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

//业务逻辑层
public interface UserInfoService {
    /**
     * 查询所有的用户数据
     * @return
     */
    List<UserInfo> findAll();

    List<UserInfo> findUserInfo(UserInfo userInfo);

    List<UserInfo> findByNickeName(String nickName);

    void addUser(UserInfo userInfo);

    void uptUser(UserInfo userInfo);

    void delUser(UserInfo userInfo);

    /**
     * 根据用户Id查询用户地址
     * @param userId
     * @return
     */

    List<UserAddress> findAddressByUserId(String userId);

    List<UserAddress> findAddressByUserId(UserAddress userAddress);
}
