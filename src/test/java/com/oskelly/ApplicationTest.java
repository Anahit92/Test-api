package com.oskelly;

import com.oskelly.model.Comment;
import com.oskelly.model.Notification;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    private static double countComments;
    private static double countNotifications;
    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    static void beforeEachTest() {
        System.out.println("=====================");
        countComments = 0.0;
        countNotifications = 0.0;
    }

    @AfterAll
    static void afterEachTest() {
        String s = String.format("percent of added comments %1$,.2f ", (countComments / 1000 * 100)) +
                String.format("percent of added notifications %1$,.2f ", (countNotifications / 1000 * 100));
        System.out.println(s);
        System.out.println("=====================");
    }

    @RepeatedTest(1000)
    public void addCommentTest() {
        UUID id = UUID.randomUUID();
        try {
            final Comment comment = new Comment(id, "comment_test1");
            HttpEntity<Comment> entity = new HttpEntity<Comment>(comment, new HttpHeaders());
            ResponseEntity<Comment> response = restTemplate.exchange(
                    getURL("/comment"), HttpMethod.POST, entity, Comment.class);
            assertThat(response.getStatusCode(), is(HttpStatus.OK));
            final Comment resp = response.getBody();
            assertThat(resp, notNullValue());
            assertThat(resp.getId(), is(id));
            /*
                check added comment
            */
            ResponseEntity<Comment> responseComment = restTemplate.getForEntity(
                    getURL("/comment?id=" + id), Comment.class);
            assertThat(responseComment.getStatusCode(), is(HttpStatus.OK));
            final Comment respComment = responseComment.getBody();
            assertThat(respComment, notNullValue());
            assertThat(respComment.getId(), is(id));
            countComments++;
            /*
                check added notification
            */
            ResponseEntity<Notification> responseNotification = restTemplate.getForEntity(
                    getURL("/notification?comment_id=" + id), Notification.class);
            assertThat(responseNotification.getStatusCode(), is(HttpStatus.OK));
            final Notification respNotification = responseNotification.getBody();
            assertThat(respNotification, notNullValue());
            assertThat(respNotification.getComment_id(), is(id));
            countNotifications++;
        } catch (Throwable e) {
            ResponseEntity<Comment> response = restTemplate.getForEntity(
                    getURL("/comment?id=" + id), Comment.class);
            assertThat(response.getStatusCode(), is(HttpStatus.OK));
            final Comment resp = response.getBody();
            assertThat(resp.getId(), is(IsNull.nullValue()));
            ResponseEntity<Notification> responseNot = restTemplate.getForEntity(
                    getURL("/notification?comment_id=" + id), Notification.class);
            assertThat(responseNot.getStatusCode(), is(HttpStatus.OK));
            final Notification respNot = responseNot.getBody();
            assertThat(respNot.getId(), is(IsNull.nullValue()));
        }
    }

    private String getURL(String uri) {
        return "http://localhost:8080" + uri;
    }

}