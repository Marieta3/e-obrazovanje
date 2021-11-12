package eobrazovanje.api;

import eobrazovanje.dto.TestDTO;
import eobrazovanje.dto.TestResultDTO;
import eobrazovanje.model.Answer;
import eobrazovanje.model.Student;
import eobrazovanje.model.Test;
import eobrazovanje.model.TestResult;
import eobrazovanje.service.IUserService;
import eobrazovanje.service.impl.AnswerService;
import eobrazovanje.service.impl.TestResultService;
import eobrazovanje.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test-results")
public class TestResultAPI {

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerService answerService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestResult> createTestResult(@RequestBody TestResultDTO testResultDTO, Principal user) throws MethodArgumentNotValidException {
        TestResult testResult = new TestResult();
        Student student = (Student) userService.findByUsername(user.getName());
        testResult.setStudent(student);
        testResult.setStartTime(testResultDTO.getStartTime());
        testResult.setEndTime(testResultDTO.getEndTime());
        Set<Answer> answers = answerService.findByIds(testResultDTO.getAnswerIDs());
        testResult.setAnswers(answers);
        int points = 0;
        // nije dobra logika za bodovanje
        for(Answer a : answers){
            if(a.isCorrect()) {
                points += a.getQuestion().getPoints();
            }
        }
        testResult.setPoints(points);
        return new ResponseEntity<>(testResultService.save(testResult), HttpStatus.OK);
    }


    /*@PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(value = "/answers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTestResult(@RequestBody TestResultDTO testResultDTO){
        Set<Answer> answers = answerService.findByIds(testResultDTO.getAnswerIDs());
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }*/

}
