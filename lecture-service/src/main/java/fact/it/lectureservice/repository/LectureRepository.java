package fact.it.lectureservice.repository;

import fact.it.lectureservice.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Lecture findLectureByLectureId(String lectureId);
}
