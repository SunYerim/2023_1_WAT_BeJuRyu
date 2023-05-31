package com.WAT.BEJURYU.controller;

import com.WAT.BEJURYU.dto.*;
import com.WAT.BEJURYU.service.DrinkService;
import com.WAT.BEJURYU.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/drinks")
public class DrinkApiController {

    private final DrinkService drinkService;
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<DrinkResponses> findAll() {
        final DrinkResponses drinks = drinkService.getAllDrinks();

        return ResponseEntity.ok(drinks);
    }

    @GetMapping("/{name}")
    public ResponseEntity<DrinkResponses> findByName(@PathVariable String name) {
        final DrinkResponses drinks = drinkService.getAllDrinks();
        final List<DrinkResponse> foundByName = drinks.getDrinks().stream()
                .filter(drink -> drink.getName().contains(name))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DrinkResponses(foundByName));
    }

    @GetMapping("/{drink_id}/rating")
    public ResponseEntity<DrinkRatingResponse> findRating(@PathVariable Long id) {
        final DrinkRatingResponse rating = reviewService.getAverageScore(id);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{drink_id}/reviews")
    public ResponseEntity<ReviewResponses> findReviewsById(@PathVariable(value = "drink_id") Long drinkId) {
        final ReviewResponses reviews = reviewService.getReviews(drinkId);

        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{drink_id}/reviews")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable(value = "drink_id") Long drinkId,
                                                       @RequestBody WriteReviewRequest reviewRequest) {
        final ReviewResponse review = drinkService.postReview(drinkId, reviewRequest);

        return ResponseEntity.ok(review);
    }

    @PutMapping("/{drink_id}/reviews/{review_id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable(value = "drink_id") Long drinkId,
                                                       @PathVariable(value = "review_id") Long reviewId,
                                                       @RequestBody WriteReviewRequest reviewRequest) {
        final ReviewResponse review = drinkService.updateReview(reviewId, reviewRequest);

        return ResponseEntity.ok(review);
    }

    @GetMapping(value = "/{drink_name}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> showImage(@PathVariable("drink_name") final String name) {
        final DrinkResponses drinksByName = drinkService.getDrinksByName(name.replaceAll("_", " "));

        return ResponseEntity.ok(drinksByName.getDrinks().get(0).getImage());
    }
}
