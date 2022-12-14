package com.project.socialapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.socialapp.entities.Post;
import com.project.socialapp.entities.User;
import com.project.socialapp.request.PostCreateRequest;
import com.project.socialapp.request.PostUpdateRequest;
import com.project.socialapp.response.LikeResponse;
import com.project.socialapp.response.PostResponse;
import com.project.socialapp.repository.PostRepository;


@Service
public class PostService {
	
	private PostRepository postRepository;
	private LikeService likeService;
	private UserService userService;
	
	public PostService(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}
	
	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}
	
	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		
		List<Post> list;

		if (userId.isPresent()) {
			list = postRepository.findByUserId(userId.get());
		} else {
			list = postRepository.findAll();
		}
		return list.stream().map(p -> { 
			List<LikeResponse> likes = likeService.getAllLike(Optional.ofNullable(null), Optional.of(p.getId()));
			return new PostResponse(p, likes);}).collect(Collectors.toList());
		
	}

	public Post getOnePostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}

	public Post createOnePost(PostCreateRequest newPostRequest) {
		User user = userService.getOneUser(newPostRequest.getUserId());
		if (user == null) {
			return null;
		}
		
		Post toSave = new Post();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setUser(user);
		return postRepository.save(toSave);
	}

	public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
		Optional<Post> post = postRepository.findById(postId);
		if(post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(postUpdateRequest.getText());
			toUpdate.setTitle(postUpdateRequest.getTitle());
			postRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}

	public void deleteOnePostById(Long postId) {
		postRepository.deleteById(postId);
		
	}
	

}
