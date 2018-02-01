package codesquad.web;

import codesquad.domain.QuestionRepository;
import codesquad.etc.HtmlFormDataBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class QuestionAcceptanceTest extends AcceptanceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template().getForEntity("/questions/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    private ResponseEntity<String> create(TestRestTemplate template) throws Exception {
        HtmlFormDataBuilder headersBuilder = HtmlFormDataBuilder.urlEncodedForm();

        String title = "title";
        headersBuilder.addParameter("title", title)
                .addParameter("contents", "hello.");
        HttpEntity<MultiValueMap<String, Object>> request = headersBuilder.build();

        ResponseEntity<String> response = template.postForEntity("/questions", request, String.class);

        return response;
    }

    @Test
    public void createTest() throws Exception {
        ResponseEntity<String> response = create(basicAuthTemplate());

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(questionRepository.findByTitle("title"));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void show() throws Exception {
        ResponseEntity<String> response = template().getForEntity(String.format("/questions/%d", 2), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?"), is(true));
    }

    @Test
    public void updateForm_no_login() {
        ResponseEntity<String> response = template().getForEntity(String.format("/questions/%d/form", 1),
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateForm_login() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate()
                .getForEntity(String.format("/questions/%d/form", 1), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("title"), is(true));
    }


}
