# Test-api

application run steps

1. mvn clean 
2. mvn install
3. run Dockerfile
4.run docker-compose.yml

e.g.

add comment
http://host:port/comment
{
"comment":"test_comment"
}

get notification by comment_id
http://host:port/notification?comment_id=d86cb0d7-57e5-45c8-8502-e0d61734e60e

get comment by id
http://host:port/comment?id=d86cb0d7-57e5-45c8-8502-e0d61734e60e

get all comments
http://host:port/comments?page=0&size=10

get all notifications 
http://host:port/notifications?page=0&size=10
