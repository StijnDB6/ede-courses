package fact.it.lectureservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureRequest {

    private String name;
    private Date date;
    private String eCode;
}
