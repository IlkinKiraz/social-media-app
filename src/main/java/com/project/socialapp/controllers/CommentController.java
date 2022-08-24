package com.project.socialapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.socialapp.entities.Comment;
import com.project.socialapp.request.CommentCreateRequest;
import com.project.socialapp.request.CommentUpdateRequest;
import com.project.socialapp.response.CommentResponse;
import com.project.socialapp.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	@GetMapping
	public List<CommentResponse> getAllComment(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
		return commentService.getAllComments(userId, postId);
	}
	
	@GetMapping("/{commentId}")
	public Comment getOneComment(@PathVariable Long commentId) {
		return commentService.getOneCommentById(commentId);
	}
	
	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest commentCreateRequest) {
		return commentService.createOneComment(commentCreateRequest);
	}
	
	@PutMapping("/{commentId}")
	public Comment updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
		return commentService.updateOneCommentById(commentId, commentUpdateRequest);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteOneComment(@PathVariable Long commentId) {
		commentService.deleteOneCommentById(commentId);
	}

}
