package org.example.reward_test;

import org.example.product.Product;
import org.example.reward.RewardByGift;
import org.example.reward.RewardInformation;
import org.example.reward.RewardService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class RewardByGiftTest implements TestInitializer {
    private RewardByGift reward = null;

    static Stream<Arguments> productIdsCustomerPoints() {
        return productIds()
                .mapToObj(
                        productId ->
                                Arguments.of(productId, 100 * productId)
                );
    }

    private static IntStream productIds() {
        return IntStream.range(1, 6);
    }

    @BeforeEach
    public void setUp(){
        reward = new RewardByGift(2);
        reward.setPointsNeeded(20);
    }

    @Test
    @DisplayName("Reward applied with enough points")
    void rewardApplied() {
        RewardInformation info = reward.applyReward(
                getSampleProductList(), 200
        );

        //assertNotNull(info);
        //assertEquals(2.99, info.getDiscount());
        //assertEquals(100, info.getPointsRedeemed());
        /*assertAll("Reward info errors",
                () -> assertEquals(2, info.getDiscount()),
                () -> assertEquals(10, info.getPointsRedeemed())
        );*/
        assertAll("Reward info errors",
                () -> assertNotNull(info),
                () -> Assertions.assertEquals(20, info.getDiscount()),
                () -> Assertions.assertEquals(20, info.getPointsRedeemed())
        );
    }

    @Test
    @DisplayName("When invalid product is passed")
    public void invalidProductIdTest(){
        int productId = 0;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reward.setGiftId(productId);
        });
        assertTrue(exception.getMessage().contains(String.valueOf(productId)));
    }

    @RepeatedTest(value = 5, name =  "-{displayName} -> {currentRepetition}/{totalRepetitions}")
    @DisplayName("When gift id not in order, no discount should be applied")
    void noGiftIdTest(RepetitionInfo repetitionInfo){
        int productId = getRandomProductIdNotInOrder();
        System.out.println(productId);
        reward.setGiftId(productId);
        RewardInformation info = reward.applyReward(getSampleProductList(), 200);
        assertEquals(0, info.getDiscount(), 0);
        assertEquals(0, info.getPointsRedeemed());
    }

    private int getRandomProductIdNotInOrder() {
        Random r = new Random();
        return r.ints(1000, 2000).findFirst().getAsInt();
    }

    @Override
    public RewardService getRewardService() {
        return reward;
    }

    @Test
    @DisplayName("Should not exceed timeout")
    void timeoutNotExceeded() {
        int numberOfProducts = 10;
        reward.setGiftId(numberOfProducts - 1);

        RewardInformation info = assertTimeout/*Preemptively*/(
                Duration.ofMillis(4),
                () ->
                        reward.applyReward(
                                buildSampleOrders(numberOfProducts),
                                200
                        )
        );

        Assertions.assertEquals(100.0, info.getDiscount());
    }

    @ParameterizedTest(name = "Test #{index}: productId={0}")
    @ValueSource(ints = { 1, 2, 3, 4 })
    void discountShouldNotBeApplied(int productId) {
        reward.setGiftId(productId);
        RewardInformation info = reward.applyReward(getSampleProductList(), 20);
        assertTrue(info.getDiscount() == 0);
    }

    @ParameterizedTest
    @MethodSource("productIds")
    void discountShouldBeAppliedMethodSource(int productId) {
        reward.setGiftId(productId);
        RewardInformation info = reward.applyReward(
                getSampleProductList(), 200);

        Assertions.assertTrue(info.getDiscount() > 0);
    }

    @ParameterizedTest
    @MethodSource("productIdsCustomerPoints")
    void discountShouldBeAppliedMethodSourceMultipleParams(int productId, int customerPoints) {
        reward.setGiftId(productId);
        RewardInformation info = reward.applyReward(
                getSampleProductList(), customerPoints);

        Assertions.assertTrue(info.getDiscount() > 0);
    }

    @ParameterizedTest
    //@CsvSource({ "1, 200", "2, 150", "3, 100" })
    @CsvFileSource(resources = "/product-point-data.csv")
    void discountShouldBeAppliedCsvSource(int productId, int customerPoints) {
        reward.setGiftId(productId);
        RewardInformation info = reward.applyReward(
                getSampleProductList(), customerPoints);

        Assertions.assertTrue(info.getDiscount() > 0);
    }

    private List<Product> buildSampleOrders(int count){
        List<Product> list = IntStream.range(1, count)
                .mapToObj((cnt)->{return new Product(cnt, "I am prod" + String.valueOf(cnt), 100.0);}).collect(Collectors.toList());
        return list;
    }
}
