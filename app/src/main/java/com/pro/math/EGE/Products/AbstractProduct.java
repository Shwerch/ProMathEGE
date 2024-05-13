package com.pro.math.EGE.Products;

import androidx.annotation.NonNull;

public abstract class AbstractProduct implements Comparable<Product> {
    public int topicId, subTopicId;
    @Override
    public int compareTo(@NonNull Product product) {
        if (this.topicId > product.topicId)
            return 1;
        else if (this.topicId < product.topicId)
            return -1;
        else if (this.subTopicId > product.subTopicId)
            return 1;
        else if (this.subTopicId < product.subTopicId)
            return -1;
        return 0;
    }
}
