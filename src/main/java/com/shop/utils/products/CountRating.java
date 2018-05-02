package com.shop.utils.products;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CountRating {

    private double sumOneStar = 0;
    private double sumTwoStars = 0;
    private double sumThreeStars = 0;
    private double sumFourStars = 0;
    private double sumFiveStars = 0;

    public double getAverageRating(Set<Rating> ratingSet) {

        if (ratingSet == null){
            return 0.0;
        } else {

            for (Rating aRatingList : ratingSet) {

                if (aRatingList.getStars().equals(1.0)) {
                    sumOneStar += aRatingList.getStars();
                } else if (aRatingList.getStars().equals(2.0)) {
                    sumTwoStars += aRatingList.getStars();
                } else if (aRatingList.getStars().equals(3.0)) {
                    sumThreeStars += aRatingList.getStars();
                } else if (aRatingList.getStars().equals(4.0)) {
                    sumFourStars += aRatingList.getStars();
                } else if (aRatingList.getStars().equals(5.0)) {
                    sumFiveStars += aRatingList.getStars();
                }
            }

            double sum = (5 * sumFiveStars + 4 * sumFourStars + 3 * sumThreeStars + 2 * sumTwoStars + 1 * sumOneStar) /
                    (sumFiveStars + sumFourStars + sumThreeStars + sumTwoStars + sumOneStar);

            zeroAllVariables();

            return sum;
        }
    }

    private void zeroAllVariables(){
        sumOneStar = 0;
        sumTwoStars = 0;
        sumThreeStars = 0;
        sumFourStars = 0;
        sumFiveStars = 0;
    }
}