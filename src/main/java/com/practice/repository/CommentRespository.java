package com.practice.repository;

import com.practice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment,Long>{

}
