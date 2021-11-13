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
import eobrazovanje.service.impl.TestService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private TestService testService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestResult> createTestResult(@RequestBody TestResultDTO testResultDTO, Principal user) throws MethodArgumentNotValidException {
        TestResult testResult = new TestResult();
        Student student = (Student) userService.findByUsername(user.getName());
        testResult.setStudent(student);
        Test test = testService.findById(testResultDTO.getTestID());
        testResult.setTest(test);
        testResult.setStartTime(testResultDTO.getStartTime());
        testResult.setEndTime(testResultDTO.getEndTime());
        Set<Answer> answers = answerService.findByIds(testResultDTO.getAnswerIDs());
        testResult.setAnswers(answers);
        int points = 0;

        HashMap<Long, Integer> questResult = scoreTest(answers);
        for(Long qId: questResult.keySet()){
            points += questResult.get(qId);
        }
        testResult.setPoints(points/test.getQuestions().size());

        TestResult saved = testResultService.save(testResult);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(value = "/answers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTestResult(@RequestBody TestResultDTO testResultDTO){
        //probni endpoint
        Set<Answer> answers = answerService.findByIds(testResultDTO.getAnswerIDs());
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')")
    @PostMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> questResults(@RequestBody TestResultDTO testResultDTO){
        //probni endpoint
        Set<Answer> answers = answerService.findByIds(testResultDTO.getAnswerIDs());
        return new ResponseEntity<>(scoreTest(answers), HttpStatus.OK);
    }

    private HashMap<Long, Integer> scoreTest(Set<Answer> answers) {
        HashMap<Long, Integer> questResult = new HashMap<>();
        for (Answer a : answers) {
            Long questId = a.getQuestion().getId();
            if (questResult.containsKey(questId)) {
                Integer value = questResult.get(questId);
                questResult.put(questId, value + a.getAccuracy());
            } else {
                questResult.put(questId, a.getAccuracy());
            }

        }
        for (Long qId : questResult.keySet()) {
//            if(questResult.get(qId) >= 50){
//                questResult.put(qId, 1);
//            }else{
//                questResult.put(qId, 0);
//            }
            questResult.put(qId, Math.max(0, questResult.get(qId)));
        }
        return questResult;
    }
}
