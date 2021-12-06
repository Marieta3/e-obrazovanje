package eobrazovanje.util;

import eobrazovanje.dto.*;
import eobrazovanje.model.*;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.persistence.EntityManager;
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
        String title = knowledgeSpaceNode.getNode().getTitle();
        Long id = knowledgeSpaceNode.getId();
        Coordinates coordinates = knowledgeSpaceNode.getCoordinates();
        String description = knowledgeSpaceNode.getNode().getDescription();
        Long domainProblemId = knowledgeSpaceNode.getNode().getId();
        Size size = knowledgeSpaceNode.getSize();
        return new DomainProblemDTO(id,title,size,coordinates,description,domainProblemId);
    }

    public static KnowledgeSpace dtoToKnowledgeSpace(GraphDTO graphDTO, Long knowledgeSpaceId){
        KnowledgeSpace result = new KnowledgeSpace();
        result.setTitle(graphDTO.getTitle());

        Map<Long,KnowledgeSpaceNode> nodeMap = new HashMap<>(graphDTO.getNodes().size());
        for (DomainProblemDTO node : graphDTO.getNodes()) {
            KnowledgeSpaceNode ksn = new KnowledgeSpaceNode();
            if(knowledgeSpaceId != null)
                ksn.setId(node.getId());
            DomainProblem dp = new DomainProblem();
            dp.setId(node.getData().getDomainProblemId());
            ksn.setNode(dp);
            ksn.setCoordinates(node.getCoordinates());
            ksn.setSize(node.getSize());
            nodeMap.put(node.getId(),ksn);
        }

        Set<Link> links = new HashSet<>(graphDTO.getLinks().size());
        for (LinkDTO linkDTO: graphDTO.getLinks()) {
            Link l = new Link();
            if(knowledgeSpaceId != null) {
                l.setId(Long.parseLong(linkDTO.getId()));
                KnowledgeSpace ks = new KnowledgeSpace();
                ks.setId(knowledgeSpaceId);
                l.setKnowledgeSpace(ks);
            }
            else
                l.setKnowledgeSpace(result);
            l.setStartNode(nodeMap.get(Long.parseLong(linkDTO.getStart_id())));
            l.setEndNode(nodeMap.get(Long.parseLong(linkDTO.getEnd_id())));
            links.add(l);
        }
        result.setLinks(links);
        return result;
    }

    public static List<KnowledgeSpaceDescriptionDTO> KnowledgeSpaceToKnowledgeSpaceDescriptionDTO(List<KnowledgeSpace> knowledgeSpaces){
        List<KnowledgeSpaceDescriptionDTO> result = new ArrayList<>(knowledgeSpaces.size());
        for(KnowledgeSpace ks : knowledgeSpaces){
            result.add(new KnowledgeSpaceDescriptionDTO(ks));
        }
        return result;
    }

    public static List<DomainProblemDescriptionDTO> domainProblemsToDomainProblemDescriptionDTOList(List<DomainProblem> domainProblemList){
        List<DomainProblemDescriptionDTO> result = new ArrayList<>(domainProblemList.size());
        for (DomainProblem dp : domainProblemList){
            result.add(new DomainProblemDescriptionDTO(dp));
        }
        return result;
    }

    public static Domain domainDTOToDomain(DomainDTO dto){
        Domain result = new Domain();
        result.setTitle(dto.getTitle());
        Set<DomainProblem> domainProblemSet = new HashSet<>(dto.getDomainProblemDTOList().size());
        for (DomainProblemDescriptionDTO dpd: dto.getDomainProblemDTOList()) {
            DomainProblem domainProblem = new DomainProblem();
            domainProblem.setDescription(dpd.getDescription());
            domainProblem.setDomain(result);
            domainProblem.setTitle(dpd.getTitle());
            domainProblemSet.add(domainProblem);
        }
        result.setDomainProblems(domainProblemSet);
        return result;
    }
}
