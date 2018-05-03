package com.shop.utils.products;

import com.shop.service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Map<Boolean, Rating> getAverageRating(List<Rating> ratingSet) {

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

        User user = userService.getCurrentUser();

        boolean userVoted = ratingSet.stream().map(Rating::getUserName).anyMatch(r -> r.equalsIgnoreCase(user.getUsername()));

        String userIfVoted = ratingSet.stream().map(Rating::getUserName).filter(u -> u.equalsIgnoreCase(user.getUsername())).findAny().orElse("non");

        Map<Boolean, Rating> booleanRatingMap = new HashMap<>();
        booleanRatingMap.put(userVoted, new Rating(sum, userIfVoted));

        return booleanRatingMap;
    }

    private void zeroAllVariables(){
        sumOneStar = 0;
        sumTwoStars = 0;
        sumThreeStars = 0;
        sumFourStars = 0;
        sumFiveStars = 0;
    }
}