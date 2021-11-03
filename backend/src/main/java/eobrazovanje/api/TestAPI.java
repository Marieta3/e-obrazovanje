package eobrazovanje.api;

import eobrazovanje.dto.AnswerDTO;
import eobrazovanje.dto.QuestionDTO;
import eobrazovanje.dto.TestDTO;
import eobrazovanje.model.Answer;
import eobrazovanje.model.Course;
import eobrazovanje.model.Question;
import eobrazovanje.model.Test;
import eobrazovanje.service.ICourseService;
import eobrazovanje.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;

@RestController
@RequestMapping("/tests")
public class TestAPI {

    @Autowired
    private ITestService testService;

    @Autowired
    private ICourseService courseService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')")
    public Test FindTestById(@PathVariable("id") Long id){
        return testService.findById(id);
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
            question.setPoints(questionDTO.getPoints());
            question.setTest(test);
            question.setRandomize(questionDTO.getRandomized());
            for(AnswerDTO answerDTO: questionDTO.getAnswers()){
                Answer answer = new Answer();
                answer.setText(answerDTO.getText());
                answer.setCorrect(answerDTO.getCorrect());
                answer.setImagePath(answerDTO.getImagePath());
                answer.setQuestion(question);
                question.getAnswers().add(answer);
            }

            test.getQuestions().add(question);
        }
        return new ResponseEntity<>(testService.save(test), HttpStatus.OK);
    }
}
