package fact.it.reviewservice.controller;

import fact.it.reviewservice.dto.ReviewRequest;
import fact.it.reviewservice.dto.ReviewResponse;
import fact.it.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createReview(@RequestBody ReviewRequest reviewRequest){
        reviewService.createReview(reviewRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getAllReviewsByECode(@RequestParam List<String> eCode) {
        return reviewService.getAllReviewsByECode(eCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getAllReviews() {
        return reviewService.getAllReviews();
    }
}
