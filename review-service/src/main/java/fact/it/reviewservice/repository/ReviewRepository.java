package fact.it.reviewservice.repository;

import fact.it.reviewservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    // Use @Query annotation with MongoDB's query syntax
    @Query("{ 'eCode' : { '$eq' : ?0 } }")  // Query for a singular eCode
    List<Review> findByECodeIn(String eCode);
}
