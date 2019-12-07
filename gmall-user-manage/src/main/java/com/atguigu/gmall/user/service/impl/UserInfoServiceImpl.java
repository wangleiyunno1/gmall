package com.atguigu.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserInfoService;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserInfo> findAll() {
        return userInfoMapper.selectAll();
    }

    @Override
    public List<UserInfo> findUserInfo(UserInfo userInfo) {
        return null;
    }

    @Override
    public List<UserInfo> findByNickeName(String nickName) {
        return null;
    }

    @Override
    public void addUser(UserInfo userInfo) {

    }

    @Override
    public void uptUser(UserInfo userInfo) {

    }

    @Override
    public void delUser(UserInfo userInfo) {

    }

    //根据用户id查询用户地址
    @Override
    public List<UserAddress> findAddressByUserId(String userId) {
//        Example example = new Example(UserAddress.class);
//        example.createCriteria().andEqualTo("userId", userId);
////        return userAddressMapper.selectByExample(example);
//        return userAddressMapper.selectByExample(example);
        //select * from userAddress where userId = ?
        //查询哪张表，就用哪张表的mapper
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Override
    public List<UserAddress> findAddressByUserId(UserAddress userAddress) {
        return userAddressMapper.select(userAddress);
    }
}
