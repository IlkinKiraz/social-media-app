package com.project.socialapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.socialapp.entities.Like;
import com.project.socialapp.entities.Post;
import com.project.socialapp.entities.User;
import com.project.socialapp.repository.LikeRepository;
import com.project.socialapp.request.LikeCreateRequest;
import com.project.socialapp.response.LikeResponse;

@Service
public class LikeService {
	
private LikeRepository likeRepository;
	
	private UserService userService;
	private PostService postService;
	
	public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
		this.likeRepository = likeRepository;
		this.userService = userService;
		this.postService = postService;
	}
	
	public List<LikeResponse> getAllLike(Optional<Long> userId, Optional<Long> postId){
		List<Like> list;
		if(userId.isPresent() && postId.isPresent()) {
			list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			list = likeRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			list = likeRepository.findByPostId(postId.get());
		}else
			list = likeRepository.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

	
	
	public Like getOneLikeById(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}
	
	
	public Like createOneLike(LikeCreateRequest likeCreateRequest) {
		User user = userService.getOneUser(likeCreateRequest.getUserId());
		Post post = postService.getOnePostById(likeCreateRequest.getPostId());
		
		if (user != null && post != null) {
			Like likeToSave = new Like();
			likeToSave.setId(likeCreateRequest.getId());
			likeToSave.setUser(user);
			likeToSave.setPost(post);
			
			return likeRepository.save(likeToSave);
		} else {
			return null;
		}
		
	}
	
	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);
	}

}
