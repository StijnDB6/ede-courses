package fact.it.courseservice.service;


import fact.it.courseservice.dto.CourseResponse;
import fact.it.courseservice.model.Course;
import fact.it.courseservice.repository.CourseRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @PostConstruct
    public void loadData(){
        courseRepository.deleteAll();
        if (courseRepository.count() <= 0){
            Course course1 = new Course();
            course1.setName("Computer Science 101");
            course1.setCourseCode("CS101");
            course1.setOpenSpots(50);

            Course course2 = new Course();
            course2.setName("Introduction to Psychology");
            course2.setCourseCode("PSY101");
            course2.setOpenSpots(40);

            Course course3 = new Course();
            course3.setName("Calculus I");
            course3.setCourseCode("MATH101");
            course3.setOpenSpots(25);

            Course course4 = new Course();
            course4.setName("World History");
            course4.setCourseCode("HIST101");
            course4.setOpenSpots(35);

            Course course5 = new Course();
            course5.setName("Creative Writing");
            course5.setCourseCode("CW102");
            course5.setOpenSpots(20);

            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);
            courseRepository.save(course4);
            courseRepository.save(course5);

        }
    }

    public List<CourseResponse> getCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::mapToCourseResponse).toList();
    }


    public List<CourseResponse> getCourseByCourseCode(String courseCode) {
        List<Course> courses = courseRepository.findCourseByCourseCode(courseCode);
        return courses.stream().map(this::mapToCourseResponse).toList();
    }

    public List<CourseResponse> getCourseAvailable() {
        List<Course> courses = courseRepository.findCourseByOpenSpotsGreaterThan(0);
        return courses.stream().map(this::mapToCourseResponse).toList();
    }


    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.builder()
                .courseCode(course.getCourseCode())
                .name(course.getName())
                .isAvailable(course.getOpenSpots() > 0)
                .build();

    }

}
