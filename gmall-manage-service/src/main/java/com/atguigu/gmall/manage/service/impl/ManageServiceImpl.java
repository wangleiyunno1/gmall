package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.*;
import com.atguigu.gmall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;


    @Override
    public List<BaseCatalog1> getCatalog1() {
        List<BaseCatalog1> baseCatalog1List = baseCatalog1Mapper.selectAll();
        return baseCatalog1List;
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        List<BaseCatalog3> baseCatalog3List = baseCatalog3Mapper.select(baseCatalog3);
        return baseCatalog3List;
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(String catalog3Id) {
//        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
//        baseAttrInfo.setCatalog3Id(catalog3Id);
//
//        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.select(baseAttrInfo);
//        return baseAttrInfoList;
        /*
        SELECT * FROM base_attr_info bai INNER JOIN base_attr_value bav ON bai.id = bav.attr_id WHERE bai.catalog3_id=61;
         */
        return baseAttrInfoMapper.selectBaseAttrInfoListByCatalog3Id(catalog3Id);
    }
    @Override
    public List<BaseAttrInfo> getAttrInfoList(BaseAttrInfo baseAttrInfo){
        return baseAttrInfoMapper.select(baseAttrInfo);
    }


//    @Override
//    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
//        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
//        baseAttrInfo.setCatalog3Id(catalog3Id);
//
//        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.select(baseAttrInfo);
//        return baseAttrInfoList;
//
//    }

    //保存属性信息
    @Transactional
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        // 修改！ baseAttrInfo
        if (baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0) {
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        } else {
            // 保存！
            // baseAttrInfo 代表页面传递过来的数据！
            // 分别插入到两张表 baseAttrInfo ，baseAttrValue
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }
        //创建一个属性值的表，
        //保存属性值
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        //先删除，再插入
        baseAttrValueMapper.delete(baseAttrValue);
        //存放属性值的集合
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList != null && attrValueList.size() > 0) {
            for (BaseAttrValue attrValue : attrValueList) {
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }
    }

    //数据回显
    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //赋值
        baseAttrInfo.setAttrValueList(getAttrValueList(attrId));
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {

        return spuInfoMapper.select(spuInfo);
    }

    //查询基本销售属性表
    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    //保存属性信息，属性值，照片
    @Transactional
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //spuInfo   里面含有从前台页面传过来的数据
        spuInfoMapper.insertSelective(spuInfo);
        //spuImage
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (spuImageList != null && spuImageList.size() > 0) {
            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }
        //spuSaleAttr
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if (spuSaleAttrList != null && spuSaleAttrList.size() > 0) {
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);

                //spuSaleAttrValue
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if (spuSaleAttrValueList != null && spuSaleAttrValueList.size() > 0) {
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }
            }
        }
    }

    //http://localhost:8082/spuSaleAttrList?spuId=59
    //根据商品 Id查询销售属性集合
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {
        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }


    //http://localhost:8082/spuImageList?spuId=59
    //根据商品spuId获取spuImage中的所有图片列表
    @Override
    public List<SpuImage> getSpuImageList(String spuId) {
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    //保存Sku信息
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //skuInfo
        skuInfoMapper.insertSelective(skuInfo);
        //skuAttrValue
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if(checkListIsEmpty(skuAttrValueList)){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insertSelective(skuAttrValue);
            }
        }
        //skuSaleAttrValue
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if(checkListIsEmpty(skuSaleAttrValueList)){
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }
        //skuImage
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(checkListIsEmpty(skuImageList)){
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insertSelective(skuImage);
            }
        }

    }

    public <T> boolean checkListIsEmpty(List<T> list){
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }
}
