package org.example.reward;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.product.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RewardByDiscount extends RewardService {
    private double percentage;
    @Override
    public RewardInformation applyReward(List<Product> productList, int customerPoints) {
        RewardInformation rewardInformation = new RewardInformation();
        double totalPrice = this.calculateTotal(productList);
        if(customerPoints > pointsNeeded){
            double discount = totalPrice * percentage;
            rewardInformation = new RewardInformation(pointsNeeded, discount);
        }
        return rewardInformation;
    }
}
