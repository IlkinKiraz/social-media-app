package com.project.socialapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.socialapp.entities.Post;
import com.project.socialapp.request.PostCreateRequest;
import com.project.socialapp.request.PostUpdateRequest;
import com.project.socialapp.response.PostResponse;
import com.project.socialapp.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	
	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
		return postService.getAllPosts(userId);
	}
	
	@GetMapping("/{postId}")
	public Post getOnePost(@PathVariable Long postId) {
		return postService.getOnePostById(postId);
	}
	
	@PostMapping
	public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createOnePost(newPostRequest);
	}
	
	@PutMapping("/{postId}")
	public Post updateonePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
		return postService.updateOnePostById(postId, postUpdateRequest);
	}
	
	@DeleteMapping("/{postId}")
	public void deleteOnePost(@PathVariable Long postId) {
		postService.deleteOnePostById(postId);
	}

}
