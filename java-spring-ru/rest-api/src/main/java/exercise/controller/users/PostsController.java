package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public List<Post> postsOfUser(@PathVariable String id) {

        return posts.stream()
            .filter(p -> p.getUserId() == Integer.parseInt(id))
            .toList();

    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPostOfUser(@PathVariable String id, @RequestBody Post post) {
        Post postThisUserId = new Post();
        postThisUserId.setUserId(Integer.parseInt(id));
        postThisUserId.setSlug(post.getSlug());
        postThisUserId.setTitle(post.getTitle());
        postThisUserId.setBody(post.getBody());

        posts.add(postThisUserId);

        return ResponseEntity
            .status(201)
            .body(postThisUserId);
    }
}
// END (Integer.parseInt(id), post.getSlug(), post.getTitle(), post.getBody())
