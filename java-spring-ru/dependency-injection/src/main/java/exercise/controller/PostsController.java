package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> index() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post show(@PathVariable Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        postRepository.save(post);

        return post;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post update(@PathVariable Long id, @RequestBody Post dataPost) {
        var maybePost = postRepository.findById(id);

        if (maybePost.isPresent()) {
            var upPost = maybePost.get();
            upPost.setTitle(dataPost.getTitle());
            upPost.setBody(dataPost.getBody());

            postRepository.save(upPost);
        } else {
            throw new ResourceNotFoundException("Post with id " + id + " not found");
        }
        return dataPost;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable Long id) {
        var maybePost = postRepository.findById(id);

        if (maybePost.isPresent()) {
            commentRepository.deleteByPostId(id);
            postRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Post with id " + id + " not found");
        }
    }
}
// END
