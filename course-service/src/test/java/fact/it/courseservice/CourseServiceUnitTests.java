package fact.it.courseservice;

import fact.it.courseservice.dto.CourseResponse;
import fact.it.courseservice.model.Course;
import fact.it.courseservice.repository.CourseRepository;
import fact.it.courseservice.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceUnitTests {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void testGetCourses(){
        List<Course> mockCourses = Arrays.asList(
                new Course(1L, "Computer Science 101", "CS101", "An introductory course covering the basics of computer science and programming.", 50),
                new Course(2L, "Introduction to Psychology", "PSY101", "Explore the fundamental principles of psychology and human behavior.", 40),
                new Course(3L, "Calculus I", "MATH101", "Learn the fundamentals of differential and integral calculus.", 25),
                new Course(4L, "World History", "HIST101", "A survey of significant events and developments from ancient to modern times.", 35)

        );
        when(courseRepository.findAll()).thenReturn(mockCourses);

        List<CourseResponse> result = courseService.getCourses();
        assertEquals(mockCourses.size(), result.size());
    }

}
