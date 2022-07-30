package com.project.socialapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.socialapp.entities.Like;
import com.project.socialapp.request.LikeCreateRequest;
import com.project.socialapp.response.LikeResponse;
import com.project.socialapp.services.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	private LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}
	
	@GetMapping
	public List<LikeResponse> getAllLike( 
			@RequestParam Optional<Long> userId,
			@RequestParam Optional<Long> postId){
		
		return likeService.getAllLike(userId, postId);
		
	}
	
	@GetMapping("/{likeId}")
	public Like getOneLike(@PathVariable Long likeId) {
		return likeService.getOneLikeById(likeId);
	}
	
	@PostMapping
	public Like createOneLike(@RequestBody LikeCreateRequest likeCreateRequest) {
		return likeService.createOneLike(likeCreateRequest);
	}
	
	@DeleteMapping("/{likeId}")
	public void deleteOnelike(@PathVariable Long likeId) {
		likeService.deleteOneLikeById(likeId);
	}

}
