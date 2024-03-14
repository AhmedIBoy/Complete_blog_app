package com.practice.service.impl;

import com.practice.entity.Comment;
import com.practice.entity.Post;
import com.practice.exception.ResourceNotFound;
import com.practice.payload.CommentDto;
import com.practice.repository.CommentRespository;
import com.practice.repository.PostRepository;
import com.practice.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private ModelMapper modelMapper;
    private PostRepository postRepo;
    private CommentRespository commentRespo;

    public CommentServiceImpl(ModelMapper modelMapper, PostRepository postRepo, CommentRespository commentRespo) {
        this.modelMapper = modelMapper;
        this.postRepo = postRepo;
        this.commentRespo = commentRespo;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = this.mapToComment(commentDto);
        Comment save = commentRespo.save(comment);
        CommentDto dto = this.mapToCommentDto(save);
        return dto;
    }

    @Override
    public CommentDto UpdateComment(CommentDto commentDto, long id,long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post Not Found With PostId : " + postId));
        Comment comment = commentRespo.findById(id).orElseThrow(() -> new ResourceNotFound("Comment NotFound With id Is given : " + id));
        Comment comment1 = this.mapToComment(commentDto);
        comment1.setId(post.getId());
        comment1.setId(comment.getId());
        Comment save = commentRespo.save(comment1);
        CommentDto dto = this.mapToCommentDto(save);
        return dto;
    }

    @Override
    public void DeleteComment(Long id) {
        commentRespo.deleteById(id);
    }

    public Comment mapToComment(CommentDto commentDto){
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    public CommentDto mapToCommentDto(Comment comment){
        CommentDto dto = this.modelMapper.map(comment, CommentDto.class);
        return  dto;
    }
}
