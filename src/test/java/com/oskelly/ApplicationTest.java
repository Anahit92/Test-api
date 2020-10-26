package com.oskelly;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import com.oskelly.model.Comment;
import com.oskelly.model.Notification;
import java.util.UUID;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    private static final String fooResourceUrl = "http://localhost:8080/comment";
    TestRestTemplate restTemplate = new TestRestTemplate();
    private static double countComments;
    private static double countNotifications;

    @BeforeAll
    static void beforeEachTest() {
        System.out.println("=====================");
        countComments = 0.0;
        countNotifications = 0.0;
    }

    @AfterAll
    static void afterEachTest() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("percent of added comments %1$,.2f ", (countComments/1000*100)));
        s.append(String.format("percent of added notifications %1$,.2f ", (countNotifications/1000*100)));
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
                    fooResourceUrl, HttpMethod.POST, entity, Comment.class);
            assertThat(response.getStatusCode(), is(HttpStatus.OK));
            final Comment fooResponse = response.getBody();
            assertThat(fooResponse, notNullValue());
            assertThat(fooResponse.getId(), is(id));
            /*
                check added comment
            */
            ResponseEntity<Comment> responseComment = restTemplate.getForEntity(
                    "http://localhost:8080/comment?id="+id, Comment.class);
            assertThat(responseComment.getStatusCode(), is(HttpStatus.OK));
            final Comment fooResponseComment = responseComment.getBody();
            assertThat(fooResponseComment, notNullValue());
            assertThat(fooResponseComment.getId(), is(id));
            countComments++;
            /*
                check added notification
            */
            ResponseEntity<Notification> responseNotification = restTemplate.getForEntity(
                    "http://localhost:8080/notification?comment_id="+id, Notification.class);
            assertThat(responseNotification.getStatusCode(), is(HttpStatus.OK));
            final Notification fooResponseNotification = responseNotification.getBody();
            assertThat(fooResponseNotification, notNullValue());
            assertThat(fooResponseNotification.getComment_id(), is(id));
            countNotifications++;
        } catch (Throwable e){
            ResponseEntity<Comment> response = restTemplate.getForEntity(
                    "http://localhost:8080/comment?id="+id, Comment.class);
            assertThat(response.getStatusCode(), is(HttpStatus.OK));
            final Comment fooResponse = response.getBody();
            assertThat(fooResponse.getId(), is(IsNull.nullValue()));
            ResponseEntity<Notification> responseNot = restTemplate.getForEntity(
                    "http://localhost:8080/notification?comment_id="+id, Notification.class);
            assertThat(responseNot.getStatusCode(), is(HttpStatus.OK));
            final Notification fooResponseNot = responseNot.getBody();
            assertThat(fooResponseNot.getId(), is(IsNull.nullValue()));
        }
    }

}