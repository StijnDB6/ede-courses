package fact.it.lectureservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "lecture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lectureId;
    private String name;
    private Date date;
    private String eCode;
    private String courseName;

    private String reviewName;
    private String reviewReview;
    private Integer reviewStars;
}
