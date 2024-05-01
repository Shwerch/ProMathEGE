package com.pro.math.EGE.Products;

public class Product extends AbstractProduct {
    public int topicId, subTopicId;
    public Product(int topicId, int subTopicId) {
        this.topicId = topicId;
        this.subTopicId = subTopicId;
    }
}
