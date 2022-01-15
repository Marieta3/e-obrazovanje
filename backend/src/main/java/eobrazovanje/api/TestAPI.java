package eobrazovanje.api;

import eobrazovanje.dto.AnswerDTO;
import eobrazovanje.dto.QuestionAnswersDTO;
import eobrazovanje.dto.QuestionDTO;
import eobrazovanje.dto.TestDTO;
import eobrazovanje.model.Answer;
import eobrazovanje.model.Course;
import eobrazovanje.model.Question;
import eobrazovanje.model.Test;
import eobrazovanje.service.ICourseService;
import eobrazovanje.service.IDomainProblemService;
import eobrazovanje.service.IQuestionService;
import eobrazovanje.service.ITestService;
import eobrazovanje.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static eobrazovanje.util.Connection.hasRole;
//import javax.validation.Valid;

@RestController
@RequestMapping("/tests")
public class TestAPI {

    @Autowired
    private ITestService testService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IDomainProblemService domainProblemService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public TestDTO FindTestById(@PathVariable("id") Long id){
        Test test = testService.findById(id);
        Set<Long> domainProblemIds = new HashSet<>();
        for(Question q :test.getQuestions()){
            domainProblemIds.add(q.getDomainProblem().getId());
        }
        Set<Question> questionsToAsk = new HashSet<>();
        for(Long dpId : domainProblemIds){
            questionsToAsk.add(questionService.getRandomQuestionForDomainProblemId(dpId));
        }
        test.setQuestions(questionsToAsk);
        return Converter.convertTestToTestDTO(test, !hasRole("ROLE_STUDENT"));
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Test> createTest(@RequestBody TestDTO testDTO) throws MethodArgumentNotValidException {
        Test test = new Test();
        test.setTitle(testDTO.getTitle());
        test.setCourse((Course) courseService.findById(testDTO.getCourseId()));
        for(QuestionDTO questionDTO : testDTO.getQuestions()){
            Question question = new Question();
            question.setText(questionDTO.getText());
            question.setDomainProblem(domainProblemService.findById(questionDTO.getDomainProblemId()));
            //question.setPoints(questionDTO.getPoints());
            question.setTest(test);
            question.setRandomize(questionDTO.getRandomized());
            int num_correct = 0;
            for(AnswerDTO aDTO: questionDTO.getAnswers()){
                if(aDTO.getCorrect()){
                    num_correct += 1;
                }
            }
            int accuracy = 100/num_correct;
            for(AnswerDTO answerDTO: questionDTO.getAnswers()){
                Answer answer = new Answer();
                answer.setText(answerDTO.getText());
                answer.setCorrect(answerDTO.getCorrect());
                answer.setImagePath(answerDTO.getImagePath());
                answer.setQuestion(question);
                if(answer.isCorrect()){
                    answer.setAccuracy(accuracy);
                }else{
                    answer.setAccuracy(0-accuracy);
                }
                question.getAnswers().add(answer);
            }

            test.getQuestions().add(question);
        }
        return new ResponseEntity<>(testService.save(test), HttpStatus.OK);
    }


    @GetMapping("/export/{test_id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> export(@PathVariable("test_id") Long testId) throws IOException {
        testService.export(testId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/next-question")
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
//    public ResponseEntity<QuestionDTO> getNextQuestion(){
//        //TODO: get next question
//        return new ResponseEntity<>(new QuestionDTO(), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
//    @PostMapping(value="/answer-question", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> sendAnswersForQuestion(@RequestBody QuestionAnswersDTO questionAnswersDTO){
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
