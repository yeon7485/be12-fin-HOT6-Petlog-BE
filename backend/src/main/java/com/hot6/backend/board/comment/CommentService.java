package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.Comment;
import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.board.post.PostRepository;
import com.hot6.backend.board.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void create(CommentDto.CommentRequest dto) {
        Post post = postRepository.findById(dto.getPostIdx())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시글 없음"));
        Comment comment = Comment.builder()
                .writer(dto.getWriter())
                .content(dto.getContent())
                .post(post)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentDto.CommentResponse> list(Long postIdx) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시글 없음"));

        return commentRepository.findByPost(post).stream()
                .map(CommentDto.CommentResponse::from)
                .toList();
    }
}
