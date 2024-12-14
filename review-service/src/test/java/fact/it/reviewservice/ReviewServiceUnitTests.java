package fact.it.reviewservice;

import fact.it.reviewservice.dto.ReviewRequest;
import fact.it.reviewservice.dto.ReviewResponse;
import fact.it.reviewservice.model.Review;
import fact.it.reviewservice.repository.ReviewRepository;
import fact.it.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceUnitTests {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void testGetAllReviews(){

        List<Review> mockReviews = Arrays.asList(
                new Review("1", "MATH101", "Jan", "Very good math course", 4),
                new Review("2", "HIST101", "Creed", "Awesome, learned about Julius Caesar", 5),
                new Review("3", "PHYS", "Lauren", "Nice course about gravity", 3)


        );
        when(reviewRepository.findAll()).thenReturn(mockReviews);

        List<ReviewResponse> r = reviewService.getAllReviews();

        assertEquals(mockReviews.size(), r.size());
        assertEquals("MATH101", r.get(0).getECode());
        assertEquals("Creed", r.get(1).getName());
        assertEquals("Nice course about gravity", r.get(2).getReview());
        assertNotEquals(4, r.get(1).getStars());

        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testCreateReview() {

        ReviewRequest reviewRequest = new ReviewRequest("MATH101", "Jake", "Awesome math course", 4);
        reviewService.createReview(reviewRequest);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }


    @Test
    void testUpdateReview() {

        ReviewRequest reviewRequest = new ReviewRequest("MATH101", "Jake", "Awesome math course", 4);
        when(reviewRepository.findByECodeIn("MATH101"))
                .thenReturn(Arrays.asList(new Review("1","MATH101", "Jake", "Awesome math course", 4)));

        boolean r = reviewService.updateReview(reviewRequest);

        assertTrue(r);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }


    @Test
    void testGetReviewByECode() {

        String eCode = "MATH101";
        List<Review> mockReviews = Arrays.asList(
                new Review("1", eCode, "Peter", "Not that good of a course", 2)
        );
        when(reviewRepository.findByECodeIn(eCode)).thenReturn(mockReviews);
        List<ReviewResponse> r = reviewService.getReviewByECode(eCode);

        assertEquals(mockReviews.size(), r.size());
    }
}
