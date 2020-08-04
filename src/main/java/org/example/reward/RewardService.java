package org.example.reward;

import org.example.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public abstract class RewardService {
    protected int pointsNeeded;
    public abstract RewardInformation applyReward(List<Product> productList, int customerPoints);

    protected double calculateTotal(List<Product> productList){
        Optional<Double> sum = productList.stream().map((product)-> product.getPrice()).reduce((value, combinedValue)->{return value + combinedValue;});
        return sum.orElse(0.0);
    }

    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public int getPointsNeeded(){
        return this.pointsNeeded;
    }
}
