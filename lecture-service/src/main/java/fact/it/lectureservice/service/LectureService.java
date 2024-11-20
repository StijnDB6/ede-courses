package fact.it.lectureservice.service;


import fact.it.lectureservice.dto.CourseResponse;
import fact.it.lectureservice.dto.LectureRequest;
import fact.it.lectureservice.dto.LectureResponse;
import fact.it.lectureservice.dto.ReviewResponse;
import fact.it.lectureservice.model.Lecture;
import fact.it.lectureservice.repository.LectureRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;
    private final WebClient webClient;

    @PostConstruct
    public void loadData() {
        lectureRepository.deleteAll();
        if (lectureRepository.count() <= 0) {
            Lecture lecture1 = new Lecture();
            lecture1.setName("Hackathon");
            lecture1.setLectureId("1");
            lecture1.setCourseName("Computer Science 101");
            lecture1.setECode("CS101");
            lecture1.setReviewName("Jan");
            lecture1.setReviewReview("Very good course");
            lecture1.setReviewStars(4);
            lecture1.setDate(new Date());

            Lecture lecture2 = new Lecture();
            lecture2.setName("Cognitive Psychology Overview");
            lecture2.setLectureId("2");
            lecture2.setCourseName("Introduction to Psychology");
            lecture2.setECode("PSY101");
            lecture2.setReviewName("Emily");
            lecture2.setReviewReview("Engaging and insightful, highly recommend.");
            lecture2.setReviewStars(5);
            lecture2.setDate(new Date());

            Lecture lecture3 = new Lecture();
            lecture3.setName("Limits and Continuity");
            lecture3.setLectureId("3");
            lecture3.setCourseName("Calculus I");
            lecture3.setECode("MATH101");
            lecture3.setReviewName("Mark");
            lecture3.setReviewReview("Challenging but rewarding. The concepts were well-explained.");
            lecture3.setReviewStars(4);
            lecture3.setDate(new Date());

            Lecture lecture4 = new Lecture();
            lecture4.setName("Ancient Civilizations");
            lecture4.setLectureId("4");
            lecture4.setCourseName("World History");
            lecture4.setECode("HIST101");
            lecture4.setReviewName("Sophia");
            lecture4.setReviewReview("A fascinating overview of world history. Learned a lot!");
            lecture4.setReviewStars(5);
            lecture4.setDate(new Date());

            lectureRepository.save(lecture1);
            lectureRepository.save(lecture2);
            lectureRepository.save(lecture3);
            lectureRepository.save(lecture4);
        }
    }

    @Value("${courseservice.baseurl}")
    private String courseServiceBaseUrl;
    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseUrl;

    public boolean createLecture(LectureRequest lectureRequest){
        Lecture lecture = new Lecture();
        lecture.setLectureId(UUID.randomUUID().toString());
        lecture.setName(lecture.getName());
        lecture.setDate(lecture.getDate());

        String eCode = lectureRequest.getECode();

        CourseResponse[] courseResponseArray = webClient.get()
                .uri("http://" + courseServiceBaseUrl + "/api/course", uriBuilder -> uriBuilder.queryParam("eCode", eCode).build())
                .retrieve()
                .bodyToMono(CourseResponse[].class)
                .block();



        ReviewResponse[] reviewResponseArray = webClient.get()
                .uri("http://" + reviewServiceBaseUrl + "/api/review", uriBuilder -> uriBuilder.queryParam("eCode", eCode).build())
                .retrieve()
                .bodyToMono(ReviewResponse[].class)
                .block();


        if (courseResponseArray != null && reviewResponseArray != null){
            boolean isAvailable = Arrays.stream(courseResponseArray).allMatch(CourseResponse::isAvailable);

            if (isAvailable){
                CourseResponse course = Arrays.stream(courseResponseArray)
                        .filter(c -> c.getECode().equals(lectureRequest.getECode()))
                        .findFirst()
                        .orElse(null);
                if (course != null){
                    lecture.setCourseName(course.getName());
                }
                ReviewResponse review = Arrays.stream(reviewResponseArray)
                        .filter(r -> r.getECode().equals(lectureRequest.getECode()))
                        .findFirst()
                        .orElse(null);
                if (review != null){
                    lecture.setReviewName(review.getName());
                    lecture.setReviewReview(review.getReview());
                    lecture.setReviewStars(review.getStars());


                }

                lectureRepository.save(lecture);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean deleteLecture(String lectureId){
        Lecture lecture = lectureRepository.findLectureByLectureId(lectureId);
        if (lecture != null){
            lectureRepository.deleteById(lecture.getId());
            return true;
        }
        else{
            return false;
        }
    }

    public List<LectureResponse> getAllLectures(){
        List<Lecture> lectures = lectureRepository.findAll();
        return lectures.stream().map(this::mapToLectureResponse).toList();
    }

    public LectureResponse getLectureByLectureId(String lectureId){
        Lecture lecture = lectureRepository.findLectureByLectureId(lectureId);
        return mapToLectureResponse(lecture);
    }

    private LectureResponse mapToLectureResponse(Lecture lecture){
        return LectureResponse.builder()
                .name(lecture.getName())
                .date(lecture.getDate())
                .eCode(lecture.getECode())
                .lectureId(lecture.getLectureId())
                .courseName(lecture.getCourseName())
                .build();
    }
}
