package fact.it.reviewservice.model;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class Review {

    private String id;
    private String eCode;
    private String name;
    private String review;
    private Integer stars;

}
