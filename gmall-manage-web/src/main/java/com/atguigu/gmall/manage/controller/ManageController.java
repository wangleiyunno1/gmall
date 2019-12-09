package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.ManageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ManageController {

    @Reference
    private ManageService manageService;

    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1() {
        return manageService.getCatalog1();
    }

    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        return manageService.getCatalog2(catalog1Id);
    }

    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        return manageService.getCatalog3(catalog2Id);
    }

    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(BaseAttrInfo baseAttrInfo,String catalog3Id) {
//        return manageService.getAttrList(catalog3Id);
        return manageService.getAttrInfoList(catalog3Id);
    }

    @RequestMapping("saveAttrInfo")
    public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo) {
        //调用服务处做保存方法
        manageService.saveAttrInfo(baseAttrInfo);
    }

    //数据回显+保存
    // http://localhost:8082/getAttrValueList?attrId=86
    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        //业务角度来讲：应该先查询平台属性，从平台属性对象中获取平台属性值集合
        //select * from baseAttrInfo where id = attrId;
        BaseAttrInfo baseAttrInfo = manageService.getAttrInfo(attrId);
        return baseAttrInfo.getAttrValueList();
        //return manageService.getAttrValueList(attrId);
    }

    //添加平台属性
    //http://localhost:8082/baseSaleAttrList
    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return manageService.getBaseSaleAttrList();
    }

    //保存
    //http://localhost:8082/saveSpuInfo
    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody SpuInfo spuInfo) {

        manageService.saveSpuInfo(spuInfo);
        return "OK";
    }
    //http://localhost:8082/spuSaleAttrList?spuId=59
    //根据商品 Id查询销售属性集合
    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId){

        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrList(spuId);
        return spuSaleAttrList;
    }

    //http://localhost:8082/spuImageList?spuId=59
    //根据商品spuId获取spuImage中的所有图片列表
    @RequestMapping("spuImageList")
    public List<SpuImage> getSpuImageList(String spuId){
        return manageService.getSpuImageList(spuId);
    }
    //保存SKU信息
    //http://localhost:8082/saveSkuInfo
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
    }
}
