package com.practice.service;

import com.practice.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto);

    CommentDto UpdateComment(CommentDto CommentDto,long id,long postId);

    void DeleteComment(Long id);



}
