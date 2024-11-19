package fact.it.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReviewRequest {

    private String eCode;
    private String name;
    private String review;
    private Integer stars;

}
