package fact.it.courseservice;

import fact.it.courseservice.dto.CourseResponse;
import fact.it.courseservice.model.Course;
import fact.it.courseservice.repository.CourseRepository;
import fact.it.courseservice.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceUnitTests {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void testGetCourses() {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setECode("Hist101");
        course.setName("History course");
        course.setDescription("Roman empire");
        course.setOpenSpots(24);

        Course course1 = new Course();
        course1.setId(2L);
        course1.setECode("Math101");
        course1.setName("Math course");
        course1.setDescription("Derivatives");
        course1.setOpenSpots(30);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        // Act
        List<CourseResponse> courses = courseService.getCourses();

        // Assert
        assertEquals(2, courses.size());
        assertEquals("Hist101", courses.get(0).getECode());
        assertEquals("History course", courses.get(0).getName());
        assertEquals("Roman empire", courses.get(0).getDescription());

        verify(courseRepository, times(1)).findAll();
    }

}
