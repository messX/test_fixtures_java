package org.example.reward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.product.Product;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class RewardByGift extends RewardService {
    private int giftId;
    @Override
    public RewardInformation applyReward(List<Product> productList, int customerPoints) {
        RewardInformation rewardInformation = new RewardInformation();
        if(customerPoints > pointsNeeded){
            Optional<Product> productMached = productList.stream().filter((product) -> giftId == product.getId()).findAny();
            if(productMached.isPresent()){
                rewardInformation = new RewardInformation(pointsNeeded, productMached.get().getPrice());
            }
            return rewardInformation;
        }
        return rewardInformation;
    }

    public void setGiftId(int giftProductId){
        if(giftProductId > 0) {
            this.giftId = giftProductId;
        } else {
            throw new IllegalArgumentException(giftProductId + " is not a valid product");
        }
    }
}
