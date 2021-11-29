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
            QuestionDTO questionDTO = new QuestionDTO(question.getText(), question.isRandomize(), null, question.getDomainProblem().getId());
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

    public static GraphDTO knowledgeSpaceToDTO(KnowledgeSpace ks){
        GraphDTO ksDTO = new GraphDTO();
        ksDTO.setTitle(ks.getTitle());
        for(DomainProblem dp: ks.getDomain().getDomainProblems()){
            DomainProblemDTO dpDTO = new DomainProblemDTO();
            dpDTO.setTitle(dp.getTitle());
            dpDTO.setId(dp.getId().toString());
            dpDTO.setCoordinates(new DomainProblemDTO.Coordinates(dp.getxCoordinate(), dp.getyCoordinate()));
            dpDTO.setSize(new DomainProblemDTO.Size(dp.getWidth(), dp.getHeight()));
            dpDTO.setData(dp.getDescription());
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

    public static KnowledgeSpace dtoToKnowledgeSpace(GraphDTO graphDTO){
        Domain domain = new Domain();
        KnowledgeSpace ks = new KnowledgeSpace();
        ks.setTitle(graphDTO.getTitle());
        Map<String, DomainProblem> mapa = new HashMap<>();

        for(DomainProblemDTO dpDTO: graphDTO.getNodes()){
            DomainProblem dp = new DomainProblem();
            dp.setTitle(dpDTO.getTitle());
            dp.setWidth(dpDTO.getSize().getWidth());
            dp.setHeight(dpDTO.getSize().getHeight());
            dp.setxCoordinate(dpDTO.getCoordinates().getX());
            dp.setyCoordinate(dpDTO.getCoordinates().getY());
            dp.setDomain(domain);
            dp.setDescription(dpDTO.getData());
            //DomainProblem dpSaved = domainProblemService.save(dp);
            mapa.put(dpDTO.getId(), dp);
            domain.getDomainProblems().add(dp);
        }

        ks.setDomain(domain);
        domain.getKnowledgeSpaces().add(ks);

        for(LinkDTO linkDTO: graphDTO.getLinks()){
            Link link = new Link();
            link.setKnowledgeSpace(ks);
            link.setStartNode(mapa.get(linkDTO.getStart_id()));
            link.setEndNode(mapa.get(linkDTO.getEnd_id()));
            ks.getLinks().add(link);
        }

        return ks;
    }

    public static List<KnowledgeSpaceDescriptionDTO> KnowledgeSpaceToKnowledgeSpaceDescriptionDTO(List<KnowledgeSpace> knowledgeSpaces){
        List<KnowledgeSpaceDescriptionDTO> result = new ArrayList<>(knowledgeSpaces.size());
        for(KnowledgeSpace ks : knowledgeSpaces){
            result.add(new KnowledgeSpaceDescriptionDTO(ks));
        }
        return result;
    }
}
