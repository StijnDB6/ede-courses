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
        course.setId("1");
        course.setECode("Hist101");
        course.setName("History course");
        course.setDescription("Roman empire");
        course.setOpenSpots(24);

        Course course1 = new Course();
        course1.setId("2");
        course1.setECode("Math101");
        course1.setName("Math course");
        course1.setDescription("Derivatives");
        course1.setOpenSpots(30);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course1));

        // Act
        List<CourseResponse> courses = courseService.getCourses();

        // Assert
        assertEquals(2, courses.size());
        assertEquals("2", courses.get(1).getId());
        assertEquals("Hist101", courses.get(0).getECode());
        assertEquals("History course", courses.get(0).getName());
        assertEquals("Roman empire", courses.get(0).getDescription());

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCoursesByECode() {
        // Arrange
        Course course = new Course();
        course.setId("1");
        course.setECode("TEST101");
        course.setName("Test course");
        course.setDescription("Great test course");
        course.setOpenSpots(20);

        when(courseRepository.findByECodeIn(Arrays.asList("TEST101"))).thenReturn(Arrays.asList(course));

        // Act
        List<CourseResponse> courses = courseService.getCoursesByECode(Arrays.asList("TEST101"));

        // Assert
        assertEquals(1, courses.size());
        assertEquals("1", courses.get(0).getId());
        assertEquals("TEST101", courses.get(0).getECode());
        assertEquals("Test course", courses.get(0).getName());
        assertEquals("Great test course", courses.get(0).getDescription());

        verify(courseRepository, times(1)).findByECodeIn(Arrays.asList(course.getECode()));
    }

    @Test
    void testGetCoursesAvailable() {
        // Arrange
        List<Course> mockCourses = Arrays.asList(
                new Course("1", "PY101", "Python course", "Basics Python", 15),
                new Course("2", "JAVA101", "Java course", "Basics Java", 0),
                new Course("3", "CO101", "COBOL course", "Basics COBOL", 25)
        );
        when(courseRepository.findCourseByOpenSpotsGreaterThan(0)).thenReturn(mockCourses);

        // Act
        List<CourseResponse> courseResponses = courseService.getCourseAvailable();

        // Assert
        assertEquals(mockCourses.size(), courseResponses.size());
        assertEquals("Python course", courseResponses.get(0).getName());
        assertEquals("PY101", courseResponses.get(0).getECode());
        assertTrue(courseResponses.get(0).isAvailable());
        assertEquals("Basics Java", courseResponses.get(1).getDescription());
        assertEquals("JAVA101", courseResponses.get(1).getECode());
        assertFalse(courseResponses.get(1).isAvailable());
        assertEquals("CO101", courseResponses.get(2).getECode());
        assertTrue(courseResponses.get(2).isAvailable());

        verify(courseRepository, times(1)).findCourseByOpenSpotsGreaterThan(0);
        // Add more assertions based on your specific requirements
    }

}
