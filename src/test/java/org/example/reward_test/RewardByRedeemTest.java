package org.example.reward_test;


import org.example.product.Product;
import org.example.reward.RewardByRedeem;
import org.example.reward.RewardInformation;
import org.example.reward.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardByRedeemTest implements TestInitializer {
    private RewardByRedeem reward = null;
    @Override
    public RewardService getRewardService() {
        return null;
    }

    @BeforeEach
    public void setUp(){
        reward = new RewardByRedeem(20.0);
        reward.setPointsNeeded(20);
    }

    @Test
    @DisplayName("Correct amount is set")
    void correctAmount() {
        assertEquals(20.0, reward.getAmount());
    }

    @Test
    @DisplayName("When order list is empty, no discount should be applied")
    void emptyOrderTest(){
        List<Product> products = getEmptyProductList();
        RewardInformation rewardInformation = reward.applyReward(products, 10);
        assertEquals(0, rewardInformation.getDiscount());
        assertEquals(0, rewardInformation.getPointsRedeemed());
    }

    @Test
    @DisplayName("When not enough points no rewards should be applied")
    void notEnoughPoints() {
        RewardInformation info = reward.applyReward(getSampleProductList(), 10);

        assertEquals(0, info.getDiscount());
        assertEquals(0, info.getPointsRedeemed());
    }

    @Test
    @DisplayName("When empty order and enough points no rewards should be applied")
    void emptyOrderEnoughPoints() {
        RewardInformation info = reward.applyReward(getEmptyProductList(), 200);

        assertEquals(0, info.getDiscount());
        assertEquals(0, info.getPointsRedeemed());
    }

}
