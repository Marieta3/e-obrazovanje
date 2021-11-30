package eobrazovanje.util;

import eobrazovanje.dto.*;
import eobrazovanje.model.*;

import java.util.*;

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
        Set<Long> nodeIds = new HashSet<>();

        for(Link link : ks.getLinks()){
            KnowledgeSpaceNode start = link.getStartNode();
            KnowledgeSpaceNode end = link.getEndNode();
            if(!nodeIds.contains(start.getId())) {
                DomainProblemDTO dp = createDomainProblemDTO(start);
                ksDTO.getNodes().add(dp);
                nodeIds.add(start.getId());
            }
            if(!nodeIds.contains(end.getId())) {
                DomainProblemDTO dp = createDomainProblemDTO(end);
                ksDTO.getNodes().add(dp);
                nodeIds.add(end.getId());
            }
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

    private static DomainProblemDTO createDomainProblemDTO(KnowledgeSpaceNode knowledgeSpaceNode) {
        DomainProblemDTO dpDTO = new DomainProblemDTO();
        dpDTO.setTitle(knowledgeSpaceNode.getNode().getTitle());
        dpDTO.setId(knowledgeSpaceNode.getId().toString());
        dpDTO.setCoordinates(knowledgeSpaceNode.getCoordinates());
        dpDTO.setSize(knowledgeSpaceNode.getSize());
        dpDTO.setData(knowledgeSpaceNode.getNode().getDescription());
        return dpDTO;
    }

    public static KnowledgeSpace dtoToKnowledgeSpace(GraphDTO graphDTO){
        Domain domain = new Domain();
        KnowledgeSpace ks = new KnowledgeSpace();
        ks.setTitle(graphDTO.getTitle());
        Map<String, KnowledgeSpaceNode> mapa = new HashMap<>();

        for(DomainProblemDTO dpDTO: graphDTO.getNodes()){
            KnowledgeSpaceNode ksn = new KnowledgeSpaceNode();
            ksn.getNode().setTitle(dpDTO.getTitle());
            ksn.getNode().setDomain(domain);
            ksn.getNode().setDescription(dpDTO.getData());
            ksn.setSize(dpDTO.getSize());
            ksn.setCoordinates(dpDTO.getCoordinates());
            //DomainProblem dpSaved = domainProblemService.save(dp);
            mapa.put(dpDTO.getId(), ksn);
            domain.getDomainProblems().add(ksn.getNode());
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
