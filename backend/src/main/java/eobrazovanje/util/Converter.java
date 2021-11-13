package eobrazovanje.util;

import eobrazovanje.dto.*;
import eobrazovanje.model.*;

import java.util.ArrayList;
import java.util.List;

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
}
