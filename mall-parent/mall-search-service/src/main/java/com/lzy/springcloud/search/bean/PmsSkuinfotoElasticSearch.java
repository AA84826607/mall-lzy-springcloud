package com.lzy.springcloud.search.bean;

import com.lzy.springcloud.manage.bean.PmsSkuAttrValue;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;


public class PmsSkuinfotoElasticSearch {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String skuName;
    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String skuDesc;
    @Field(type = FieldType.Keyword)
    private String catalog3Id;
    @Field(type = FieldType.Double)
    private BigDecimal price;
    @Field(type = FieldType.Keyword)
    private String skuDefaultImg;
    @Field(type = FieldType.Keyword)
    private String productId;
    @Field
    private List<PmsSkuAttrValue> skuAttrValueList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    @Override
    public String toString() {
        return "PmsSkuinfotoElasticSearch{" +
                "id='" + id + '\'' +
                ", skuName='" + skuName + '\'' +
                ", skuDesc='" + skuDesc + '\'' +
                ", catalog3Id='" + catalog3Id + '\'' +
                ", price=" + price +
                ", skuDefaultImg='" + skuDefaultImg + '\'' +
                ", productId='" + productId + '\'' +
                ", skuAttrValueList=" + skuAttrValueList +
                '}';
    }
}
