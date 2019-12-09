package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface ManageService {
    /**
     * 查询所有的一级分类数据
     *
     * @return
     */
    public List<BaseCatalog1> getCatalog1();


    /**
     * 根据一级分类Id，查询二级分类数据
     *
     * @param catalog1Id
     * @return
     */
    public List<BaseCatalog2> getCatalog2(String catalog1Id);


    /**
     * 根据
     *
     * @param catalog2Id
     * @return
     */
    public List<BaseCatalog3> getCatalog3(String catalog2Id);


    /**
     * 根据三级分类id获取平台属性
     * @param catalog3Id
     * @return
     */
    List<BaseAttrInfo> getAttrInfoList(String catalog3Id);
    List<BaseAttrInfo> getAttrInfoList(BaseAttrInfo baseAttrInfo);
    //保存属性信息
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 根据attrId查询平台属性值集合
     *
     * @param attrId
     * @return
     */
    List<BaseAttrValue> getAttrValueList(String attrId);

    /**
     * 通过平台属性attrId查询平台属性对象
     *
     * @param attrId
     * @return
     */
    BaseAttrInfo getAttrInfo(String attrId);

    /**
     * 根据spuInfo属性查询spuInfo集合
     *
     * @param spuInfo
     * @return
     */
    List<SpuInfo> getSpuInfoList(SpuInfo spuInfo);

    //查询基本销售属性表
    List<BaseSaleAttr> getBaseSaleAttrList();

    //保存属性信息，属性值，照片
    //http://localhost:8082/saveSpuInfo
    public void saveSpuInfo(SpuInfo spuInfo);

    //http://localhost:8082/spuSaleAttrList?spuId=59
    //根据商品 Id查询销售属性集合
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);


    //http://localhost:8082/spuImageList?spuId=59
    //根据商品spuId获取spuImage中的所有图片列表
    List<SpuImage> getSpuImageList(String spuId);

    //保存Sku信息
    void saveSkuInfo(SkuInfo skuInfo);


}
