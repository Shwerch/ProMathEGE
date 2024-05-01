package com.pro.math.EGE.Products;

public class Product extends AbstractProduct {
    public int topicId;
    public int subTopicId;
    public void Change(int topicId,int subTopicId) {
        this.topicId = topicId;
        this.subTopicId = subTopicId;
    }
    public Product(int topicId, int subTopicId) {
        Change(topicId,subTopicId);
    }
}
