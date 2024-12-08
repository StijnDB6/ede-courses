package fact.it.reviewservice.repository;

import fact.it.reviewservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewByECode(String eCode);
}
