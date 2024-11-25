package fact.it.courseservice.repository;


import fact.it.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("Select c FROM Course c WHERE c.eCode IN :eCodes")
    List<Course> findByECodeIn(@Param("eCodes") List<String> eCode);
    List<Course> findCourseByOpenSpotsGreaterThan(int greater);
}
