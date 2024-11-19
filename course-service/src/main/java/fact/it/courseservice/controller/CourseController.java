package fact.it.courseservice.controller;

import fact.it.courseservice.dto.CourseResponse;
import fact.it.courseservice.model.Course;
import fact.it.courseservice.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCourses(){
        return courseService.getCourses();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCourseByECode(@RequestParam String eCode) {
        return courseService.getCourseByECode(eCode);
    }

    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCoursesAvailable() {
        return courseService.getCourseAvailable();
    }


}
