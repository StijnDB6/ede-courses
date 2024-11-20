package fact.it.courseservice.repository;


import fact.it.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByECodeIn(List<String> eCode);
    List<Course> findCourseByOpenSpotsGreaterThan(int greater);
}
