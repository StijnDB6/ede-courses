package fact.it.reviewservice.service;

import fact.it.reviewservice.dto.ReviewRequest;
import fact.it.reviewservice.dto.ReviewResponse;
import fact.it.reviewservice.model.Review;
import fact.it.reviewservice.repository.ReviewRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @PostConstruct
    public void loadData() {
        reviewRepository.deleteAll();
        if (reviewRepository.count() <= 0) {
            Review review1 = new Review();
            review1.setName("Jan");
            review1.setECode("CS101");
            review1.setReview("Very good course");
            review1.setStars(4);

            Review review2 = new Review();
            review2.setName("Emily");
            review2.setECode("PSY101");
            review2.setReview("Engaging and insightful, highly recommend.");
            review2.setStars(5);

            Review review3 = new Review();
            review3.setName("Mark");
            review3.setECode("MATH101");
            review3.setReview("Challenging but rewarding. The concepts were well-explained.");
            review3.setStars(4);

            Review review4 = new Review();
            review4.setName("Sophia");
            review4.setECode("HIST101");
            review4.setReview("A fascinating overview of world history. Learned a lot!");
            review4.setStars(5);


            reviewRepository.save(review1);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
            reviewRepository.save(review4);
        }
    }

    public void createReview(ReviewRequest reviewRequest){
        Review review = Review.builder()
                .eCode(reviewRequest.getECode())
                .name(reviewRequest.getName())
                .review(reviewRequest.getReview())
                .stars(reviewRequest.getStars())
                .build();

        reviewRepository.save(review);
    }

    public List<ReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews.stream().map(this::mapToReviewResponse).toList();
    }

    public List<ReviewResponse> getReviewByECode(String eCode) {
        List<Review> reviews = reviewRepository.findReviewByECode(eCode);

        return reviews.stream().map(this::mapToReviewResponse).toList();
    }

    public boolean updateReview(ReviewRequest reviewRequest){
        String eCode = reviewRequest.getECode();
        Optional<Review> review = reviewRepository.findReviewByECode(eCode).stream().findFirst();
        if (review.isPresent()) {
            Review review1 = review.get();
            review1.setName(reviewRequest.getName());
            review1.setReview(reviewRequest.getReview());
            review1.setStars(reviewRequest.getStars());

            reviewRepository.save(review1);
            return true;
        }
        return false;
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .eCode(review.getECode())
                .name(review.getName())
                .review(review.getReview())
                .stars(review.getStars())
                .build();
    }
}
