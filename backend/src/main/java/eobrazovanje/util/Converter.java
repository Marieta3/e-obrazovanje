package eobrazovanje.util;

import eobrazovanje.dto.*;
import eobrazovanje.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {
    public static TestDTO convertTestToTestDTO(Test test, boolean shouldContainsIsAnswerCorrectProperty){
        TestDTO result = new TestDTO(test.getTitle(),test.getCourse().getId(),null);
        ArrayList<QuestionDTO> questions = new ArrayList<>(test.getQuestions().size());
        for (Question question : test.getQuestions()){
            QuestionDTO questionDTO = new QuestionDTO(question.getText(), question.isRandomize(), null);
            ArrayList<AnswerDTO> answers = new ArrayList<AnswerDTO>(question.getAnswers().size());
            for(Answer answer : question.getAnswers()){
                AnswerDTO answerDTO = new AnswerDTO(answer.getId(), answer.getText(), answer.getImagePath(), null, answer.getAccuracy());
                if(shouldContainsIsAnswerCorrectProperty){
                    answerDTO.setCorrect(answer.isCorrect());
                }
                answers.add(answerDTO);
            }

            questionDTO.setAnswers(answers);

            questions.add(questionDTO);
        }

        result.setQuestions(questions);

        return result;
    }

    public static UserDTO userToUserDTO(User user){
        return new UserDTO(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName());
    }

    public static List<UserDTO> usersToUserDTOs(List<User> users){
        List<UserDTO> result = new ArrayList<>(users.size());
        for (User u : users){
            result.add(userToUserDTO(u));
        }
        return result;
    }

    public static CourseDTO courseToCourseDTO(Course course){
        return new CourseDTO(course.getId(), course.getName(), course.getDescription(), course.getTeacher().getId());
    }

    public static List<CourseDTO> coursesToCourseDTOs(List<Course> courses){
        List<CourseDTO> result = new ArrayList<>(courses.size());
        for (Course c : courses){
            result.add(courseToCourseDTO(c));
        }
        return result;
    }

    public static TestDescriptionDTO testToTestDescritpionDTO(Test test){
        return new TestDescriptionDTO(test.getTitle(),test.getId(),test.getCourse().getId());
    }

    public static List<TestDescriptionDTO> testsToTestDescriptionDTOs(List<Test> tests){
        List<TestDescriptionDTO> result = new ArrayList<>(tests.size());
        for (Test t : tests){
            result.add(testToTestDescritpionDTO(t));
        }
        return result;
    }

    public static KnowledgeSpaceDTO knowledgeSpaceToDTO(KnowledgeSpace ks){
        KnowledgeSpaceDTO ksDTO = new KnowledgeSpaceDTO();
        ksDTO.setTitle(ks.getTitle());
        for(DomainProblem dp: ks.getDomainProblems()){
            DomainProblemDTO dpDTO = new DomainProblemDTO();
            dpDTO.setTitle(dp.getTitle());
            dpDTO.setId(dp.getId().toString());
            dpDTO.setCoordinates(new DomainProblemDTO.Coordinates(dp.getxCoordinate(), dp.getyCoordinate()));
            dpDTO.setSize(new DomainProblemDTO.Size(dp.getWidth(), dp.getHeight()));
            ksDTO.getNodes().add(dpDTO);
        }

        for(Link link: ks.getLinks()){
            LinkDTO linkDTO = new LinkDTO();
            linkDTO.setId(link.getId().toString());
            linkDTO.setStart_id(link.getStartNode().getId().toString());
            linkDTO.setEnd_id(link.getEndNode().getId().toString());
            ksDTO.getLinks().add(linkDTO);
        }

        return ksDTO;
    }

    public static KnowledgeSpace dtoToKnowledgeSpace(KnowledgeSpaceDTO ksDTO){
        KnowledgeSpace ks = new KnowledgeSpace();
        ks.setTitle(ksDTO.getTitle());
        Map<String, DomainProblem> mapa = new HashMap<>();
        for(DomainProblemDTO dpDTO: ksDTO.getNodes()){
            DomainProblem dp = new DomainProblem();
            dp.setTitle(dpDTO.getTitle());
            dp.setWidth(dpDTO.getSize().getWidth());
            dp.setHeight(dpDTO.getSize().getHeight());
            dp.setxCoordinate(dpDTO.getCoordinates().getX());
            dp.setyCoordinate(dpDTO.getCoordinates().getY());
            dp.setKnowledgeSpace(ks);
            //DomainProblem dpSaved = domainProblemService.save(dp);
            mapa.put(dpDTO.getId(), dp);
            ks.addDomainProblem(dp);
        }
        System.out.println("*****************************************************");
        for (DomainProblem dp : ks.getDomainProblems()){
            System.out.println(dp.toString());
        }
        System.out.println("*****************************************************");

        for(LinkDTO linkDTO: ksDTO.getLinks()){
            Link link = new Link();
            link.setKnowledgeSpace(ks);
            link.setStartNode(mapa.get(linkDTO.getStart_id()));
            link.setEndNode(mapa.get(linkDTO.getEnd_id()));
            ks.getLinks().add(link);
        }
        for(String key : mapa.keySet()){
            System.out.println(mapa.get(key).toString());
        }

        return ks;
    }
}
