package fact.it.lectureservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureResponse {
    private String name;

    private String lectureId;
    private Date date;
    private String eCode;
    private String courseName;
    private String reviewName;
    private String reviewReview;
    private Integer reviewStars;
}
