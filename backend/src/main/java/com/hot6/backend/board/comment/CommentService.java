package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.Comment;
import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.board.post.PostRepository;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void create(CommentDto.CommentRequest dto, User currentUser) {
        Post post = postRepository.findById(dto.getPostIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        try {
            Comment comment = Comment.builder()
                    .content(dto.getContent())
                    .post(post)
                    .user(currentUser)
                    .build();
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_CREATE_FAILED);
        }
    }

    public List<CommentDto.CommentResponse> list(Long postIdx) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        try {
            return commentRepository.findByPost(post).stream()
                    .map(CommentDto.CommentResponse::from)
                    .toList();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteByPostIdx(Long postIdx) {
        try {
            commentRepository.deleteByPostIdx(postIdx);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_DELETE_FAILED);
        }
    }

    @Transactional
    public void delete(Long idx) {
        Comment comment = commentRepository.findById(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));

        try {
            commentRepository.delete(comment);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_DELETE_FAILED);
        }
    }

    @Transactional
    public void update(Long idx, CommentDto.CommentRequest dto) {
        Comment comment = commentRepository.findById(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));

        try {
            comment.setContent(dto.getContent());
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_UPDATE_FAILED);
        }
    }

    public List<CommentDto.CommentResponse> readByAnswer(Long userId) {
        try {
            return commentRepository.findByUser_IdxOrderByCreatedAtDesc(userId).stream()
                    .map(CommentDto.CommentResponse::from)
                    .toList();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND);
        }
    }
}
