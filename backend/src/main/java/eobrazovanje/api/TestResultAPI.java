package eobrazovanje.api;

import eobrazovanje.dto.QuestionDTO;
import eobrazovanje.dto.TestAnswerDTO;
import eobrazovanje.dto.TestDTO;
import eobrazovanje.dto.TestResultDTO;
import eobrazovanje.model.*;
import eobrazovanje.service.IUserService;
import eobrazovanje.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

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

    @Autowired
    private DomainProblemService domainProblemService;

    @Autowired
    private QuestionService questionService;

    /*@PreAuthorize("hasRole('ROLE_STUDENT')")
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
    }*/


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

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping()
    public QuestionDTO AnswerQuestion(@RequestBody TestAnswerDTO answerDTO, Principal user){
        Student student = (Student) userService.findByUsername(user.getName());

        Set<Long> availableDomainProblemsIds = new HashSet<>();
        TestResult testResult;
        if(answerDTO.getTestResultId()==null && answerDTO.getTestId() != null){
            System.out.println("\n\nKreiram test!\n\n");
            testResult = createNewTestResult(answerDTO, student);
            System.out.println("*************************\nPronasao domenske probleme:");
            for (DomainProblem dp : domainProblemService.findByDomainId(testResult.getTest().getCourse().getDomain().getId())){
                availableDomainProblemsIds.add(dp.getId());
                System.out.println(dp.getId());
            }
        }else if(answerDTO.getTestResultId() != null && answerDTO.getAnswerId() != null){
            testResult = testResultService.findById(answerDTO.getTestResultId());
            Answer answer = answerService.findById(answerDTO.getAnswerId());
            testResult.addAnswer(answer);
            testResultService.save(testResult); //TODO: proveriti da li je upisan samo novi answer

            DomainProblem domainProblem = answerService.getDomainProblemByAnswerId(answerDTO.getAnswerId());
            for (DomainProblem dp : domainProblemService.findByDomainId(domainProblem.getDomain().getId())){
                availableDomainProblemsIds.add(dp.getId());
            }
            for(Answer ans : testResult.getAnswers()){
                availableDomainProblemsIds.remove(ans.getId());
                //TODO: azurirati listu verovatnoca za svako stanje znanja
            }
        }else{
            //throw bad request
        }

        if(availableDomainProblemsIds.size()==0)
            return null;





        //TODO: dobaviti listu mogucih stanja znanja i njihovih verovatnoca


        //TODO: proci kroz listu svih i proveriti da li ima neko stanje znanja koje ima veci koeficijent od 0.7 i vratiti null



        Long[] domainProblemsIds = new Long[availableDomainProblemsIds.size()];
        availableDomainProblemsIds.toArray(domainProblemsIds);
        return getRandomQuestionFromAvailableDomainProblems(domainProblemsIds);
    }

    private TestResult createNewTestResult(TestAnswerDTO answerDTO, Student student) {
        Test test = testService.findById(answerDTO.getTestId());
        TestResult tr = new TestResult(new Date(),null,0,new HashSet<>(), student, test);
        return testResultService.save(tr);
    }

    private QuestionDTO getRandomQuestionFromAvailableDomainProblems(Long[] availableDomainProblemsIds) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableDomainProblemsIds.length);
            Question question = questionService.getRandomQuestionForDomainProblemId(availableDomainProblemsIds[randomIndex]);
            return new QuestionDTO(question,true);
    }


}
