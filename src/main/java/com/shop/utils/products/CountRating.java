package com.shop.utils.products;

import com.shop.dao.ProductsDAO;
import com.shop.entity.Product;
import com.shop.service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Component
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
    private ProductsDAO productsDAO;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductsDAO(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    public Double getAverageRating(List<Rating> ratingList) {

        for (Rating aRatingList : ratingList) {
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

    private void zeroAllVariables(){
        sumOneStar = 0;
        sumTwoStars = 0;
        sumThreeStars = 0;
        sumFourStars = 0;
        sumFiveStars = 0;
    }

    @Transactional
    public String getCurrentUserInVotedRating(Integer idProduct){

        List<Rating> rating = productsDAO.get(Long.valueOf(idProduct)).getRating();
        User user = userService.getCurrentUser();

        return rating.stream().map(Rating::getUserName).filter(u -> u.equalsIgnoreCase(user.getUsername())).findAny().orElse("non");

    }
}