package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/createReview")
    public ResponseEntity<?> createReview(
            @RequestBody Review review,
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId
            ){
        Property property = propertyRepository.findById(propertyId).get();
        Review reviewDetails = reviewRepository.findByUserAndProperty(user, property);
        if(reviewDetails!=null){
            return new ResponseEntity<>("Review Exists", HttpStatus.CREATED);
        }
        review.setAppUser(user);
        review.setProperty(property);
        Review savedReview = reviewRepository.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/userReviews")
    public List<Review> listReviewOfUser(
            @AuthenticationPrincipal AppUser user
    ){
        List<Review> reviews = reviewRepository.findReviewsByUser(user);
        return reviews;
    }
}
