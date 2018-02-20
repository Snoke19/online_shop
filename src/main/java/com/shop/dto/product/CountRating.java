package com.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class CountRating {

    private double sumOneStar = 0;
    private double sumTwoStars = 0;
    private double sumThreeStars = 0;
    private double sumFourStars = 0;
    private double sumFiveStars = 0;

    public double getAverageRating(List<Rating> ratingList) {

        if (ratingList == null){
            return 0.0;
        } else {

            for (Rating aRatingList : ratingList) {

                if (aRatingList.getStar().equals(1.0)) {
                    sumOneStar += aRatingList.getStar();
                } else if (aRatingList.getStar().equals(2.0)) {
                    sumTwoStars += aRatingList.getStar();
                } else if (aRatingList.getStar().equals(3.0)) {
                    sumThreeStars += aRatingList.getStar();
                } else if (aRatingList.getStar().equals(4.0)) {
                    sumFourStars += aRatingList.getStar();
                } else if (aRatingList.getStar().equals(5.0)) {
                    sumFiveStars += aRatingList.getStar();
                }
            }

            double sum = (5 * sumFiveStars + 4 * sumFourStars + 3 * sumThreeStars + 2 * sumTwoStars + 1 * sumOneStar) /
                    (sumFiveStars + sumFourStars + sumThreeStars + sumTwoStars + sumOneStar);

            zeroAllVariables();

            return sum;
        }
    }

    private void zeroAllVariables(){
        setSumOneStar(0);
        setSumTwoStars(0);
        setSumThreeStars(0);
        setSumFourStars(0);
        setSumFiveStars(0);
    }
}