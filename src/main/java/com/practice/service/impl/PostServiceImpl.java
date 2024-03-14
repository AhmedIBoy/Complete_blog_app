package com.practice.service.impl;

import com.practice.entity.Post;
import com.practice.exception.ResourceNotFound;
import com.practice.payload.PostDto;
import com.practice.repository.PostRepository;
import com.practice.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper modelMapper;
    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto){
        Post post = this.mapToPost(postDto);
        Post save = postRepo.save(post);
        PostDto postDto1 = this.mapToPostDto(save);
        return postDto1;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
       Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> postPage = postRepo.findAll(pageable);
        List<Post> all = postPage.getContent();
        List<PostDto> collect = all.stream().map(e -> this.mapToPostDto(e)).collect(Collectors.toList());
        return collect;
    }



    public PostDto updatePost(PostDto postDto,long id){
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound(" ResourceNotFound With Id " + id));
        PostDto postDto1 = this.mapToPostDto(post);
        postDto1.setId(post.getId());
        Post post1 = this.mapToPost(postDto1);
        Post save = postRepo.save(post1);
        PostDto postDto2 = this.mapToPostDto(save);
        return postDto2;
    }

    @Override
    public void deletePost(long id) {
        postRepo.deleteById(id);
    }

    public PostDto getPostById(long id){
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("ResourceNotFound with Id " + id));
        PostDto postDto = this.mapToPostDto(post);
        return postDto;
    }

    public Post mapToPost(PostDto postDto){
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }

    public PostDto mapToPostDto(Post post){
        PostDto dto = this.modelMapper.map(post, PostDto.class);
        return dto;
    }
}
