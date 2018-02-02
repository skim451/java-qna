package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.dto.QuestionDto;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

    private QuestionDto createQuestionDto(String title) {
        return new QuestionDto(title, "contents");
    }

    private AnswerDto createAnswerDto() {
        return createAnswerDto("test answer");
    }

    private AnswerDto createAnswerDto(String contents) {
        Question question = new Question();
        return new AnswerDto().setWriter(defaultUser())
                .setContents(contents)
                .setQuestion(question);
    }

    @Test
    public void create() throws Exception {
        QuestionDto questionDto = createQuestionDto("test2");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        AnswerDto answerDto = createAnswerDto();
        location = createResourceDefaultLogin("/api/answers", answerDto);
        AnswerDto dbAnswer = getResource(location, AnswerDto.class);

        assertThat(dbAnswer, is(answerDto));
    }
//
//    @Test
//    public void showQuestionDetail() throws Exception {
//        QuestionDto questionDto = createQuestionDto("test2");
//        String location = createResourceDefaultLogin("/api/questions", questionDto);
//        ResponseEntity<String> response = template().getForEntity(location, String.class);
//
//        assertThat(response.getBody().contains("test2"), is(true));
//    }
//
//    @Test
//    public void update() throws Exception {
//        QuestionDto questionDto = createQuestionDto("test3");
//        String location = createResourceDefaultLogin("/api/questions", questionDto);
//
//        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
//        QuestionDto updateQuestion = new QuestionDto(dbQuestion.getId(), "new title", "new contents");
//        basicAuthTemplate().put(location, updateQuestion);
//
//        dbQuestion = basicAuthTemplate(defaultUser()).getForObject(location, QuestionDto.class);
//        assertThat(dbQuestion, is(updateQuestion));
//    }
//
//    @Test
//    public void update_no_authroity() throws Exception {
//        QuestionDto questionDto = createQuestionDto("test4");
//        String location = createResourceDefaultLogin("/api/questions", questionDto);
//
//        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
//        QuestionDto updateQuestion = new QuestionDto(dbQuestion.getId(), "new title", "new contents");
//        basicAuthTemplate().put(location, updateQuestion);
//
//        User user = new User("sehwan", "1001", "sehwan", "email");
//        dbQuestion = basicAuthTemplate(user).getForObject(location, QuestionDto.class);
//        assertThat(dbQuestion, not(questionDto));
//    }
//
//    @Test
//    public void delete() throws Exception {
//        QuestionDto questionDto = createQuestionDto("test5");
//        String location = createResourceDefaultLogin("/api/questions", questionDto);
//
//        QuestionDto dbQuestion = template().getForObject(location, QuestionDto.class);
//        basicAuthTemplate().delete(location, dbQuestion);
//
//        dbQuestion = getResource(location, QuestionDto.class);
//        assertNull(dbQuestion);
//    }
//
//    @Test
//    public void delete_no_authority() throws Exception {
//        QuestionDto questionDto = createQuestionDto("test5");
//        String location = createResourceDefaultLogin("/api/questions", questionDto);
//
//        QuestionDto dbQuestion = template().getForObject(location, QuestionDto.class);
//        User user = new User("sehwan", "1001", "sehwan", "email");
//        basicAuthTemplate(user).delete(location, dbQuestion);
//
//        dbQuestion = getResource(location, QuestionDto.class);
//        assertNotNull(dbQuestion);
//    }
}
