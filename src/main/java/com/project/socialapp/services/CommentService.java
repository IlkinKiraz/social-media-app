package com.project.socialapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.socialapp.entities.Comment;
import com.project.socialapp.entities.Post;
import com.project.socialapp.entities.User;
import com.project.socialapp.request.CommentCreateRequest;
import com.project.socialapp.request.CommentUpdateRequest;
import com.project.socialapp.response.CommentResponse;
import com.project.socialapp.repository.CommentRepository;


@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;
	
	public CommentService(
			CommentRepository commentRepository, 
			UserService userService, 
			PostService postService) {
		
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}
	
	
	public List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId){
		List<Comment> comments;
		if (userId.isPresent() && postId.isPresent()) {
			comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (postId.isPresent()) {
			comments = commentRepository.findByUserId(postId.get());
		} else if (userId.isPresent()) {
			comments = commentRepository.findByUserId(userId.get());
		} else {
			comments = commentRepository.findAll();
		}
		
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
}

	
	public Comment getOneCommentById(Long CommentId) {
		return commentRepository.findById(CommentId).orElse(null);
	}
	
	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUser(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if(user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
	
			return commentRepository.save(commentToSave);
		}else		
			return null;
	}
	
	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment toUpdate = comment.get();
			toUpdate.setText(commentUpdateRequest.getText());
			commentRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}
	
	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
	}

}
