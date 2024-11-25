package fact.it.courseservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    private String name;
    private String eCode;
    private String description;
    private boolean isAvailable;
}
