package eobrazovanje.service.impl;

import eobrazovanje.model.Answer;
import eobrazovanje.model.Question;
import eobrazovanje.model.Test;
import eobrazovanje.repostiroy.ITestRepository;
import eobrazovanje.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TestService implements ITestService {

    @Autowired
    private ITestRepository testRepository;


    @Override
    public Test findById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public List<Test> findByCourseId(Long courseId) {
        return testRepository.findByCourseId(courseId);
    }

    @Override
    public void export(Long testId) throws IOException {
        Test test = testRepository.findById(testId).orElse(null);

        if (test != null) {
            StringBuilder test_sb = new StringBuilder();
            StringBuilder questions_sb = new StringBuilder();
            test_sb.append(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<qti-assessment-test xmlns=\"http://www.imsglobal.org/xsd/imsqti_v3p0\" \n" +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "identifier=\"test%s\"\n" +
                    "title=\"%s\"\n" +
                    "xsi:schemaLocation=\"http://www.imsglobal.org/xsd/imsqti_v3p0 \n" +
                    "https://purl.imsglobal.org/spec/qti/v3p0/xsd/imsqti_asiv3p0_v1p0.xsd\"\n" +
                    "xml:lang=\"en-US\" >\n", test.getId(), test.getTitle()));

            for (Question q : test.getQuestions()) {
                StringBuilder question_sb = new StringBuilder();
                StringBuilder answer_sb = new StringBuilder();
                StringBuilder correct_answer_sb = new StringBuilder();
                int max_correct = 0;
                for(Answer a: q.getAnswers()){
                    if(a.isCorrect()){
                        max_correct+=1;
                        correct_answer_sb.append(String.format("<qti-value>answer%s</qti-value>", a.getId()));
                    }
                    answer_sb.append(String.format("   <qti-simple-choice identifier=\"answer%s\">%s</qti-simple-choice>\n", a.getId(), a.getText()));
                }
                question_sb.append(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<qti-assessment-item\n" +
                        "xmlns=\"http://www.imsglobal.org/xsd/qti/imsqtiasi_v3p0\" \n" +
                        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "xsi:schemaLocation=\"http://www.imsglobal.org/xsd/imsqtiasi_v3p0 \n" +
                        "https://purl.imsglobal.org/spec/qti/v3p0/schema/xsd/imsqti_asiv3p0_v1p0.xsd\"\n" +
                        "identifier=\"question%s\"\n" +
                        "time-dependent=\"false\" \n" +
                        "xml:lang=\"en-US\">\n", q.getId()));
                question_sb.append(String.format("<qti-response-declaration base-type=\"identifier\" cardinality=\"single\" identifier=\"RESPONSE\">\n" +
                        "   <qti-correct-response>\n" +
                        "     %s\n" +
                        "   </qti-correct-response>\n" +
                        " </qti-response-declaration>", correct_answer_sb.toString()));
                //ako se tacno odgovori, pitanje nosi 100%
                question_sb.append(String.format("<qti-outcome-declaration base-type=\"float\" cardinality=\"single\" identifier=\"SCORE\">\n" +
                        "   <qti-default-value>\n" +
                        "     <qti-value>%s</qti-value>\n" +
                        "   </qti-default-value>\n" +
                        " </qti-outcome-declaration>\n", 100));
                question_sb.append(String.format(" <qti-item-body>\n" +
                        "<qti-prompt>\n" +
                        "   <p>%s</p>\n" +
                        "</qti-prompt>\n", q.getText()));
                question_sb.append(String.format("<qti-choice-interaction max-choices=\"%s\" min-choices=\"1\"" +
                        "  response-identifier=\"RESPONSE\">\n", max_correct));
                question_sb.append(String.format("%s</qti-choice-interaction>\n" +
                        "\n" +
                        "</qti-item-body>\n" +
                        "\n" +
                        "<qti-response-processing\n" +
                        "template=\"https://purl.imsglobal.org/spec/qti/v3p0/rptemplates/match_correct\"/>\n" +
                        "\n" +
                        "</qti-assessment-item>", answer_sb.toString()));

                System.out.println(question_sb.toString());
                System.out.println("----------------------------------------");
                String question_xml_path = String.format("question%s.xml", q.getId());
                writeToFile(question_sb, question_xml_path);

                questions_sb.append(String.format("\t\t\t<qti-assessment-item-ref identifier=\"question%s\"\n" +
                        "              href=\"%s\"/>\n", q.getId(), question_xml_path));

            }

            test_sb.append(String.format("    <qti-test-part identifier=\"testPart-1\" navigation-mode=\"nonlinear\" \n" +
                    "   submission-mode=\"individual\">\n" +
                    "        <qti-assessment-section identifier=\"assessmentSection-1\" title=\"Section 1\"\n" +
                    "         visible=\"true\">\n" +"%s" +
                    "        </qti-assessment-section>\n"+
                    "    </qti-test-part>\n" +
                    "</qti-assessment-test>", questions_sb));
            System.out.println(test_sb.toString());
            writeToFile(test_sb, String.format("test%s.xml", test.getId()));
        }
    }

    private void writeToFile(StringBuilder sb, String filePath) throws IOException {
        Files.write(Paths.get("src", "main", "resources", "qti", filePath), sb.toString().getBytes());
    }

}
