package com.hot6.backend.board.post;

import com.hot6.backend.board.comment.CommentService;
import com.hot6.backend.board.post.model.BoardType;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.post.model.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

        Post post = Post.builder()
                .writer(dto.getWriter())
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
            results = postRepository.findByBoardTypeAndCategoryAndTitleContainingIgnoreCase(boardType, category, keyword);
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

}