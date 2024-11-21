package fact.it.lectureservice;

import fact.it.lectureservice.dto.CourseResponse;
import fact.it.lectureservice.dto.LectureRequest;
import fact.it.lectureservice.dto.LectureResponse;
import fact.it.lectureservice.dto.ReviewResponse;
import fact.it.lectureservice.model.Lecture;
import fact.it.lectureservice.repository.LectureRepository;
import fact.it.lectureservice.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatcher;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureServiceUnitTests {


    @InjectMocks
    private LectureService lectureService;
    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;


    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(lectureService, "courseServiceBaseUrl", "http://localhost:8081");
        ReflectionTestUtils.setField(lectureService, "reviewServiceBaseUrl", "http://localhost:8082");
    }


    @Test
    void testCreateLecture_Success() {

        String name = "Hackaton";
        Date date = new Date();
        String eCode = "PYTH101";

        LectureRequest lectureRequest = new LectureRequest();
        lectureRequest.setDate(date);
        lectureRequest.setName(name);
        lectureRequest.setECode(eCode);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setECode(eCode);
        courseResponse.setAvailable(true);
        courseResponse.setName("Python beginner course");
        courseResponse.setDescription("Learn all data structures in python");

        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setECode(eCode);
        reviewResponse.setName("Jan");
        reviewResponse.setReview("Great course for beginners indeed");
        reviewResponse.setStars(5);


        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLectureId("1");
        lecture.setName(name);
        lecture.setDate(date);

        when(lectureRepository.save(any(Lecture.class))).thenReturn(new Lecture());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CourseResponse[].class)).thenReturn(Mono.just(new CourseResponse[]{courseResponse}));
        when(responseSpec.bodyToMono(ReviewResponse[].class)).thenReturn(Mono.just(new ReviewResponse[]{reviewResponse}));


        boolean r = lectureService.createLecture(lectureRequest);

        assertTrue(r);
        verify(lectureRepository, times(1)).save(any(Lecture.class));
    }

    @Test
    void testCreateLecture_Failure() {

        String name = "Hackaton";
        Date date = new Date();
        String eCode = "PYTH101";

        LectureRequest lectureRequest = new LectureRequest();
        lectureRequest.setDate(date);
        lectureRequest.setName(name);
        lectureRequest.setECode(eCode);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setECode(eCode);
        courseResponse.setAvailable(false);
        courseResponse.setName("Python beginner course");
        courseResponse.setDescription("Learn all data structures in python");

        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setECode(eCode);
        reviewResponse.setName("Jan");
        reviewResponse.setReview("Great course for beginners indeed");
        reviewResponse.setStars(5);


        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLectureId("1");
        lecture.setName(name);
        lecture.setDate(date);


        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CourseResponse[].class)).thenReturn(Mono.just(new CourseResponse[]{courseResponse}));
        when(responseSpec.bodyToMono(ReviewResponse[].class)).thenReturn(Mono.just(new ReviewResponse[]{reviewResponse}));


        boolean r = lectureService.createLecture(lectureRequest);

        assertFalse(r);
        verify(lectureRepository, times(0)).save(lecture);
    }

    @Test
    void testDeleteLecture() {
        String lectureId = "1";
        Lecture mockLecture = new Lecture();
        when(lectureRepository.findLectureByLectureId(lectureId)).thenReturn(mockLecture);

        boolean r = lectureService.deleteLecture(lectureId);

        assertTrue(r);
        verify(lectureRepository, times(1)).deleteById(mockLecture.getId());
    }

    @Test
    void testGetAllLectures() {
        List<Lecture> mockLectures = Arrays.asList(new Lecture());
        when(lectureRepository.findAll()).thenReturn(mockLectures);

        List<LectureResponse> r = lectureService.getAllLectures();

        assertEquals(mockLectures.size(), r.size());
    }

    @Test
    void testGetLectureByLectureId() {
        // Arrange
        String lectureId = "1";
        Lecture mockLecture = new Lecture();
        when(lectureRepository.findLectureByLectureId(lectureId)).thenReturn(mockLecture);

        LectureResponse r = lectureService.getLectureByLectureId(lectureId);

        assertNotNull(r);
    }

}
