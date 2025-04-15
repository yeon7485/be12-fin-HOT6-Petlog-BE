package com.hot6.backend.board.post;

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

    private final PostRepository postRepo;
    private final BoardTypeRepository boardTypeRepo;

    public void create(PostDto.PostRequest dto) {
        BoardType boardType = boardTypeRepo.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        Post post = Post.builder()
                .writer(dto.getWriter())
                .title(dto.getTitle())
                .content(dto.getContent())
                .image(dto.getImage())
                .category(dto.getCategory())
                .boardType(boardType)
                .build();

        postRepo.save(post);
    }

    public List<PostDto.PostResponse> list(String boardName) {
        BoardType boardType = boardTypeRepo.findByBoardName(boardName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));
        return postRepo.findByBoardType(boardType).stream().map(PostDto.PostResponse::from).toList();
    }

    public PostDto.PostResponse read(Long idx) {
        Post post = postRepo.findById(idx).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "게시글 없음"));
        return PostDto.PostResponse.from(post);
    }

    public List<PostDto.PostResponse> search(String boardName, String category, String keyword) {
        BoardType boardType = boardTypeRepo.findByBoardName(boardName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        List<Post> results;

        if (keyword != null && !keyword.isBlank()) {
            results = postRepo.findByBoardTypeAndCategoryAndTitleContainingIgnoreCase(boardType, category, keyword);
        } else {
            results = postRepo.findByBoardTypeAndCategory(boardType, category);
        }

        return results.stream()
                .map(PostDto.PostResponse::from)
                .toList();
    }

}