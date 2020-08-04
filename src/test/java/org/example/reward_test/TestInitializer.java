package org.example.reward_test;

import org.example.product.Product;
import org.example.reward.RewardService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public interface TestInitializer {
    default List<Product> getEmptyProductList(){
        return new ArrayList<Product>();
    }

    default List<Product> getSampleProductList(){
        Product p1=new Product(1, "p1", 10.0);
        Product p2=new Product(2, "p2", 20.0);
        Product p3=new Product(3, "p3", 30.0);
        return Arrays.asList(p1, p2, p3);
    }

    RewardService getRewardService();

    @Test
    @DisplayName("Correct points are set")
    default void correctPoint() {
        assertEquals(100, getRewardService().getPointsNeeded());
    }
}
