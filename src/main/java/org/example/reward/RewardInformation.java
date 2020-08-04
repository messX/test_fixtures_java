package org.example.reward;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardInformation {
    private int pointsRedeemed;
    private double discount;
}
