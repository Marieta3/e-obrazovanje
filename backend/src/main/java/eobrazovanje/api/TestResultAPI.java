package eobrazovanje.api;

import eobrazovanje.dto.*;
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
    public TestAnswerResponseDTO AnswerQuestion(@RequestBody TestAnswerDTO answerDTO, Principal user){
        System.out.println("***************************************Pocetak***************************");
        Student student = (Student) userService.findByUsername(user.getName());

        Set<Long> availableDomainProblemsIds;
        TestResult testResult;

        double maxProbability=0;
        final double THRESHOLD = 0.7;
        if(answerDTO.getTestResultId()==null && answerDTO.getTestId() != null){
            System.out.println("****************************************\nKreiram test result");
            testResult = createNewTestResult(answerDTO, student);
            availableDomainProblemsIds = getSetOfAllDomainProblemIdsForDomainId(testResult.getTest().getCourse().getDomain().getId());
            System.out.println("Dostupni domenski problemi:");
            System.out.println(availableDomainProblemsIds);
        }else if(answerDTO.getTestResultId() != null && answerDTO.getAnswerId() != null){
            testResult = testResultService.findById(answerDTO.getTestResultId());
            Answer answer = answerService.findById(answerDTO.getAnswerId());
            System.out.println("Odgovor na pitanje "+answer.getId()+": tacnost = "+answer.isCorrect());
            DomainProblem domainProblem = answerService.getDomainProblemByAnswerId(answerDTO.getAnswerId());
            Domain domain = domainProblem.getDomain();
            availableDomainProblemsIds = getSetOfAllDomainProblemIdsForDomainId(domain.getId());
            System.out.println("Svi domenski problemi za domen dobijen iz odgovora");
            System.out.println(availableDomainProblemsIds);
            Set<State> states = domain.getActiveKnowledgeSpace().getStates();
            System.out.println("-------------\nSva moguca stanja sa verovatnocama:");
            for(State s : states){
                System.out.println(s.toString());
            }
            testResult.addAnswer(answer);
            testResultService.save(testResult);

            updateStateProbabilities(availableDomainProblemsIds, testResult.getAnswers(), states);
            System.out.println("Dostupni preostali domenski problemi");
            System.out.println(availableDomainProblemsIds);
            System.out.println("Trazim max verovatnocu");
            for(State s: states){
                if(maxProbability< s.getProbability())
                    maxProbability = s.getProbability();
            }
            System.out.println("Max verovatnoca je "+maxProbability);
            Set<Long> domainProblemsInMaximalProbabilityStates = new HashSet<>();
            for(State s: states){
                if(s.getProbability()==maxProbability){
                    for(DomainProblem dp : s.getDomainProblems()){
                        domainProblemsInMaximalProbabilityStates.add(dp.getId());
                    }
                }
            }
            if(domainProblemsInMaximalProbabilityStates.size()==0){
                System.out.println("Jedini preostali state sa maksimalnom verovatnocom je prazan state i ne menjamo skup mogucih domenskih problema");
            }else{
                System.out.println("pravim presek skupova domenskih problema");
                Set<Long> intersection = getSetIntersection(availableDomainProblemsIds, domainProblemsInMaximalProbabilityStates);
                if(intersection.size()>0)
                    availableDomainProblemsIds = intersection;
            }
            System.out.println("Preostali domenski problemi koji se nalaze u stanju znanja sa maksimalnom verovatnocom su:");
            System.out.println(availableDomainProblemsIds);
        }else{
            //throw bad request
            return null;
        }

        if(availableDomainProblemsIds.isEmpty() || maxProbability >= THRESHOLD) {
            testResult.setEndTime(new Date());
            testResultService.save(testResult);
            return null;
        }

        Long[] domainProblemsIds = new Long[availableDomainProblemsIds.size()];
        availableDomainProblemsIds.toArray(domainProblemsIds);
        return new TestAnswerResponseDTO(testResult.getId(), getRandomQuestionFromAvailableDomainProblems(domainProblemsIds));
    }

    private void updateStateProbabilities(Set<Long> availableDomainProblemsIds, Set<Answer> answers, Set<State> states) {
        System.out.println("-------------------------------------------------------\n\nAzuriranje verovatnoce\n------------------------------------");
        final double UPDATE_VALUE = 0.05;
        for(Answer ans : answers){
            System.out.println("Novo pitanje\n-----------------------------");
            Long domainProblemId = ans.getQuestion().getDomainProblem().getId();
            System.out.println("1) Odgovaram na pitanje iz domenskog problema "+ domainProblemId+ ": tacnost = "+ans.isCorrect());
            availableDomainProblemsIds.remove(domainProblemId);
            System.out.println("Trenutni skup dostupnih domenskih problema:");
            System.out.println(availableDomainProblemsIds);
            System.out.println("2) Prolazim kroz sva stanja znanja\n------------------------------------");
            double minProbability = 10d;
            double maxProbability = 0;
            double sum = 0;
            for(State s : states){
                double increment = updateNegativity(s, domainProblemId, ans.isCorrect()) * UPDATE_VALUE;
                s.increaseProbability(increment);
                System.out.println(String.format("Menjam vrednost stanja %2d na ",s.getId())+ s.getProbability());
                minProbability = (minProbability > s.getProbability()) ? s.getProbability() : minProbability;
                maxProbability = (maxProbability < s.getProbability()) ? s.getProbability() : maxProbability;
                sum += s.getProbability();
            }
            System.out.println("---------------------------\n3) Skaliram verovatnoce ako za to ima potrebe");
            System.out.println("MAX verovatnoca = "+maxProbability);
            System.out.println("MIN verovatnoca = "+minProbability);
            System.out.println("Suma = "+ sum);
            System.out.println("-------------------------------");
            boolean isProbabilitiesInRange = minProbability >= 0d && maxProbability < 1d;
            if(!isProbabilitiesInRange){
                sum = 0;
                System.out.println("Skaliranje vrednosti u opseg [0,1]\n*******************************************");
                for(State s : states){
                    //https://stackoverflow.com/questions/929103/convert-a-number-range-to-another-range-maintaining-ratio
                    //NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
                    double newProbability = (s.getProbability()-minProbability)/(maxProbability-minProbability);
                    s.setProbability(newProbability);
                    sum+=newProbability;
                    System.out.println(s.toString());
                }
            }
            boolean isProbabilitiesSumCorrect = sum > 0.999d && sum < 1.001d;
            if(!isProbabilitiesSumCorrect){
                System.out.println("Skaliranje vrednosti kako bi suma bila 1\n*******************************************");
                for(State s : states){
                    double newProbability = s.getProbability()/sum;
                    s.setProbability(newProbability);
                    System.out.println(s.toString());
                }
            }
            System.out.println("Zavrseno skaliranje");
            System.out.println("-------------\nFinalna vrednost verovatnoce:");
            for(State s : states){
                System.out.println(s.toString());
            }

            System.out.println("Kraj trenutnog pitanja");
        }
        System.out.println("Kraj algoritma za azuriranje");
    }

    private int updateNegativity(State s,  Long domainProblemId, boolean isCorrectAnswer) {
        if(s.containsDomainProblem(domainProblemId) && isCorrectAnswer)
            return 1;
        if(!s.containsDomainProblem(domainProblemId) && !isCorrectAnswer)
            return 1;
        return -1;
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
            System.out.println("Dajem sledece pitanje iz domenskog problema "+question.getDomainProblem().getId() );
            return new QuestionDTO(question,true);
    }

    private Set<Long> getSetOfAllDomainProblemIdsForDomainId(Long domainId){
        List<DomainProblem> domainProblems = domainProblemService.findByDomainId(domainId);
        Set<Long> result = new HashSet<>(domainProblems.size());
        for(DomainProblem dp : domainProblems){
            result.add(dp.getId());
        }
        return result;
    }

    private Set<Long> getSetIntersection(Set<Long> set1, Set<Long> set2){
        System.out.println("********************************\nPravim presek skupova\n*********************\n");
        System.out.println("available: "+set1);
        System.out.println("max states: " + set2);
        Set<Long> result = new HashSet<>();
        for(long id : set1){
            if(set2.contains(id))
                result.add(id);
        }
        System.out.println("rezultat = "+ result);
        return result;
    }


}
