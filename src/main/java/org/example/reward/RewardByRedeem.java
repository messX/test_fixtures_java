package org.example.reward;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.product.Product;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RewardByRedeem extends RewardService {
    private double amount;
    @Override
    public RewardInformation applyReward(List<Product> productList, int customerPoints) {
        RewardInformation rewardInformation = new RewardInformation();
        double totalPrice = this.calculateTotal(productList);
        if(customerPoints > pointsNeeded){
            if(totalPrice > amount){
                rewardInformation = new RewardInformation(pointsNeeded, amount);
            }
        }
        return rewardInformation;
    }
}
