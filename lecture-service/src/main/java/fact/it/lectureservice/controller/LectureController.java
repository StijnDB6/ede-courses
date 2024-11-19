package fact.it.lectureservice.controller;

import fact.it.lectureservice.dto.LectureRequest;
import fact.it.lectureservice.dto.LectureResponse;
import fact.it.lectureservice.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<LectureResponse> getLectures(){
        return lectureService.getAllLectures();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public LectureResponse getLectureByLectureId(@RequestParam String lectureId) {
        return lectureService.getLectureByLectureId(lectureId);
    }

    // POST race
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String createLecture(@RequestBody LectureRequest lectureRequest) {
        boolean result = lectureService.createLecture(lectureRequest);
        return (result ? "Lecture created successfully" : "Lecture created unsuccessfully");
    }

    // DELETE race
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteLectureById(@RequestParam String lectureId) {
        boolean result = lectureService.deleteLecture(lectureId);
        return (result ? "Lecture deleted successfully" : "Lecture deleted unsuccessfully");
    }
}
