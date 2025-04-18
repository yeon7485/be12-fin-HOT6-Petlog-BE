package com.hot6.backend.board.comment;

import com.hot6.backend.board.answer.model.AnswerDto;
import com.hot6.backend.board.comment.model.Comment;
import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.board.post.PostRepository;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void create(CommentDto.CommentRequest dto, User currentUser) {
        Post post = postRepository.findById(dto.getPostIdx())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다"));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .post(post)
                .user(currentUser)
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

    @Transactional
    public void deleteByPostIdx(Long PostIdx) {
        commentRepository.deleteByPostIdx(PostIdx);
    }

    @Transactional
    public void delete(Long idx) {
        Comment comment = commentRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }

    @Transactional
    public void update(Long idx, CommentDto.CommentRequest dto) {
        Comment comment = commentRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다"));
        comment.setContent(dto.getContent());
        commentRepository.save(comment);
    }

    public List<CommentDto.CommentResponse> readByAnswer(Long userId) {
        return commentRepository.findByUser_IdxOrderByCreatedAtDesc(userId)
                .stream()
                .map(CommentDto.CommentResponse::from)
                .toList();
    }
}
