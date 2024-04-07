package kz.bitlab.techorda.controller;

import kz.bitlab.techorda.model.ApplicationRequest;
import kz.bitlab.techorda.model.Courses;
import kz.bitlab.techorda.repository.ApplicationRequestRepository;
import kz.bitlab.techorda.repository.CourseRepository;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(value = "/home")
    public String getHomePage(Model model) {
        List<ApplicationRequest> applicationRequestList = applicationRequestRepository.findAll();
        model.addAttribute("applicationRequest", applicationRequestList);
        return "home";
    }

    @GetMapping(value = "/addRequest")
    public String addRequestPage(Model model) {
        List<Courses> coursesList= courseRepository.findAll();
        model.addAttribute("coursesList", coursesList);
        return "addrequest";
    }

    @PostMapping(value = "addRequest")
    public String addRequestSubmit(@RequestParam(value = "username") String userName,
                                   @RequestParam(value = "courseId") Long courseId,
                                   @RequestParam(value = "phoneNumber") String phoneNumber,
                                   @RequestParam(value = "commentary") String commentText) {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setUserName(userName);
        Courses course= courseRepository.findById(courseId).orElseThrow();
        applicationRequest.setCourse(course);
        applicationRequest.setPhone(phoneNumber);
        applicationRequest.setCommentary(commentText);
        applicationRequest.setHandled(false);
        applicationRequestRepository.save(applicationRequest);
        return "redirect:/home";
    }

    @GetMapping(value = "/details/{id}")
    public String getdetailsRequest(@PathVariable(value = "id") Long id, Model model) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(id).orElseThrow();
        model.addAttribute("appDetail", applicationRequest);
        return "details";
    }

    @PostMapping(value = "/removeRequest/{id}")
    public String removeRequestById(@PathVariable(value = "id") Long id) {
        applicationRequestRepository.deleteById(id);
        return "redirect:/home";
    }

    @PostMapping(value = "/changeHandled/{id}")
    public String changeHandledById(@PathVariable(value = "id") Long id) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(id).orElseThrow();
        applicationRequest.setHandled(true);
        applicationRequestRepository.save(applicationRequest);
        return "redirect:/home";
    }
    @GetMapping(value = "/trueRequests")
    public String getPageTrueRequests(Model model){
        List<ApplicationRequest> applicationRequestList = applicationRequestRepository.findByHandled(true);
        model.addAttribute("appRequests", applicationRequestList);
        return "trueRequests";
    }
    @GetMapping(value = "/falseRequests")
    public String getPageFalseRequests(Model model){
        List<ApplicationRequest> applicationRequestList = applicationRequestRepository.findByHandled(false);
        model.addAttribute("appRequests", applicationRequestList);
        return "falseRequests";
    }
    @GetMapping(value = "/courses")
    public String getPageCourses(Model model){
        List<Courses> coursesList=courseRepository.findAll();
        model.addAttribute("courses",coursesList);
        return "courses";
    }
    @GetMapping(value = "/addCourse")
    public String getPageAddCourses(){
        return "addCourse";
    }
    @PostMapping(value = "/addCourse")
    public String getPageAddCourses(@RequestParam(value = "name") String name,
                                    @RequestParam(value = "price") int price,
                                    @RequestParam(value = "desc") String desc){
        Courses courses= new Courses();
        courses.setName(name);
        courses.setPrice(price);
        courses.setDescription(desc);
        courseRepository.save(courses);
        return "redirect:/courses";
    }
    @GetMapping(value = "/coursesDetails/{id}")
    public String getPageCoursesDetails(Model model,
                                        @PathVariable(value = "id") Long id) {
        Courses course= courseRepository.findById(id).orElseThrow();
        model.addAttribute("course",course);
        return "courseDetails";
    }
    @PostMapping(value = "/updateCourse/{id}")
    public String updateCourse(@PathVariable(value = "id") Long id,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "price") int price,
                               @RequestParam(value = "desc") String desc){
        Courses courses=courseRepository.findById(id).orElseThrow();
        courses.setName(name);
        courses.setPrice(price);
        courses.setDescription(desc);
        courseRepository.save(courses);
        return "redirect:/courses";
    }
    @PostMapping(value = "/deleteCourse/{id}")
    public String deleteCourse(@PathVariable(value = "id") Long id){
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }
}
