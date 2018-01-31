package codesquad.web;

import codesquad.domain.UserRepository;
import codesquad.etc.HtmlFormDataBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class LoginAcceptanceTest extends AcceptanceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() throws Exception {
        HtmlFormDataBuilder headersBuilder = HtmlFormDataBuilder.urlEncodedForm();

        String userId = "testuser";
        headersBuilder.addParameter("userId", userId)
                .addParameter("password", "password")
                .addParameter("name", "자바지기")
                .addParameter("email", "javajigi@slipp.net");
        HttpEntity<MultiValueMap<String, Object>> request = headersBuilder.build();

        ResponseEntity<String> response = template().postForEntity("/users", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(userRepository.findByUserId(userId));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }
}
