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
    public void loadData() {
        courseRepository.deleteAll();
        if (courseRepository.count() <= 0) {
            Course course1 = new Course();
            course1.setName("Computer Science 101");
            course1.setECode("CS101");
            course1.setOpenSpots(50);
            course1.setDescription("An introductory course covering the basics of computer science and programming.");

            Course course2 = new Course();
            course2.setName("Introduction to Psychology");
            course2.setECode("PSY101");
            course2.setOpenSpots(40);
            course2.setDescription("Explore the fundamental principles of psychology and human behavior.");

            Course course3 = new Course();
            course3.setName("Calculus I");
            course3.setECode("MATH101");
            course3.setOpenSpots(25);
            course3.setDescription("Learn the fundamentals of differential and integral calculus.");

            Course course4 = new Course();
            course4.setName("World History");
            course4.setECode("HIST101");
            course4.setOpenSpots(35);
            course4.setDescription("A survey of significant events and developments from ancient to modern times.");

            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);
            courseRepository.save(course4);
        }
    }


    public List<CourseResponse> getCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::mapToCourseResponse).toList();
    }


    public List<CourseResponse> getCoursesByECode(List<String> eCode) {
        List<Course> courses = courseRepository.findByECodeIn(eCode);
        return courses.stream().map(this::mapToCourseResponse).toList();
    }

    public List<CourseResponse> getCourseAvailable() {
        List<Course> courses = courseRepository.findCourseByOpenSpotsGreaterThan(0);
        return courses.stream().map(this::mapToCourseResponse).toList();
    }


    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .eCode(course.getECode())
                .name(course.getName())
                .isAvailable(course.getOpenSpots() > 0)
                .description(course.getDescription())
                .build();

    }

}
