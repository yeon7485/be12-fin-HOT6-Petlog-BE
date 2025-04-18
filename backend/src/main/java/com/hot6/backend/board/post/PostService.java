package com.hot6.backend.board.post;

import com.hot6.backend.board.comment.CommentService;
import com.hot6.backend.board.post.model.BoardType;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.post.model.PostDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final BoardTypeRepository boardTypeRepoistory;

    public void create(PostDto.PostRequest dto) {
        BoardType boardType = boardTypeRepoistory.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = Post.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .image(dto.getImage())
                .category(dto.getCategory())
                .boardType(boardType)
                .build();

        postRepository.save(post);
    }


    public List<PostDto.PostResponse> list(String boardName) {
        BoardType boardType = boardTypeRepoistory.findByBoardName(boardName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));
        return postRepository.findByBoardType(boardType).stream().map(PostDto.PostResponse::from).toList();
    }

    public PostDto.PostResponse read(Long idx) {
        Post post = postRepository.findById(idx).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "게시글 없음"));
        return PostDto.PostResponse.from(post);
    }

    public List<PostDto.PostResponse> search(String boardName, String category, String keyword) {
        BoardType boardType = boardTypeRepoistory.findByBoardName(boardName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        List<Post> results;

        if (keyword != null && !keyword.isBlank()) {
            // 제목 + 작성자 검색으로 확장
            List<Post> byTitle = postRepository.findByBoardTypeAndCategoryAndTitleContainingIgnoreCase(boardType, category, keyword);
            List<Post> byWriter = postRepository.findByBoardTypeAndCategoryAndUser_NicknameContainingIgnoreCase(boardType, category, keyword);

            // 두 리스트 합치되 중복 제거
            results = Stream.concat(byTitle.stream(), byWriter.stream())
                    .distinct()
                    .toList();
        } else {
            results = postRepository.findByBoardTypeAndCategory(boardType, category);
        }

        return results.stream()
                .map(PostDto.PostResponse::from)
                .toList();
    }


    public void delete(Long idx) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시글 없음"));

        commentService.deleteByPostIdx(idx);
        postRepository.delete(post);
    }

    public void update(Long idx, PostDto.PostRequest dto) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시글 없음"));

        BoardType boardType = boardTypeRepoistory.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setImage(dto.getImage());
        post.setBoardType(boardType);

        postRepository.save(post);
    }
    public List<PostDto.UserPostResponse> findUserPosts(Long userId) {
        return postRepository.findByUserIdxAndIsDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostDto.UserPostResponse::from)
                .toList();
    }
}