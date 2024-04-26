package exercise.controller;

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

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> index() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment show(@PathVariable Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment update(@PathVariable Long id, @RequestBody Comment dataComment) {
        var maybeComment = commentRepository.findById(id);

        if (maybeComment.isPresent()) {
            var comment = maybeComment.get();
            comment.setPostId(dataComment.getPostId());
            comment.setBody(dataComment.getBody());

            commentRepository.save(comment);
        } else {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }
        return dataComment;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable Long id) {
        var maybeComment = commentRepository.findById(id);

        if (maybeComment.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }
    }
}
// END
