package com.project.socialapp.response;

import com.project.socialapp.entities.Comment;

import lombok.Data;

@Data
public class CommentResponse {
	
	Long id;
	Long userId;
	Long postId;
	String userName;
	String text;
	
	public CommentResponse(Comment entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.postId = entity.getPost().getId();
		this.userName = entity.getUser().getUserName();
		this.text = entity.getText();
	}
	
}
