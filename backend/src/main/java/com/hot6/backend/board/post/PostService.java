package com.hot6.backend.board.post;

import com.hot6.backend.board.comment.CommentService;
import com.hot6.backend.board.post.images.PostImageService;
import com.hot6.backend.board.post.model.BoardType;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.post.model.PostDto;
import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostImageService postImageService;
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PetRepository petRepository;

    public void create(PostDto.PostRequest dto, List<MultipartFile> images) throws IOException {
        BoardType boardType = boardTypeRepository.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "카테고리 없음"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = Post.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .image(dto.getImage())
                .category(category)
                .boardType(boardType)
                .build();

        postRepository.save(post);

        if (dto.getPetIdxList() != null && !dto.getPetIdxList().isEmpty()) {
            List<Pet> pets = petRepository.findAllById(dto.getPetIdxList());
            for (Pet pet : pets) {
                pet.setPost(post);
            }
            petRepository.saveAll(pets);
        }

        if (images != null && !images.isEmpty()) {
            postImageService.saveImages(images, post);
        }
    }

    public Page<PostDto.PostResponse> list(String boardName, int page, int size) {
        BoardType boardType = boardTypeRepository.findByBoardName(boardName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByBoardType(boardType, pageable)
                .map(PostDto.PostResponse::from);
    }

    public PostDto.PostResponse read(Long idx) {
        Post post = postRepository.findById(idx).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "게시글 없음"));
        return PostDto.PostResponse.from(post);
    }

    public List<PostDto.PostResponse> search(String boardName, String categoryName, String keyword) {
        BoardType boardType = boardTypeRepository.findByBoardName(boardName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        List<Post> results;

        if (keyword != null && !keyword.isBlank()) {
            List<Post> byTitle = postRepository.findByBoardTypeAndCategoryNameAndTitleContainingIgnoreCase(boardType, categoryName, keyword);
            List<Post> byWriter = postRepository.findByBoardTypeAndCategoryNameAndUser_NicknameContainingIgnoreCase(boardType, categoryName, keyword);
            results = Stream.concat(byTitle.stream(), byWriter.stream()).distinct().toList();
        } else {
            results = postRepository.findByBoardTypeAndCategoryName(boardType, categoryName);
        }

        return results.stream().map(PostDto.PostResponse::from).toList();
    }

    public void delete(Long idx) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시글 없음"));

        List<Pet> relatedPets = petRepository.findAllByPost(post);
        relatedPets.forEach(pet -> pet.setPost(null));
        petRepository.saveAll(relatedPets);

        postImageService.deleteImagesByPost(idx);
        commentService.deleteByPostIdx(idx);
        postRepository.delete(post);
    }

    public void update(Long idx, PostDto.PostRequest dto, List<MultipartFile> images) throws IOException {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시글 없음"));

        BoardType boardType = boardTypeRepository.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "게시판 종류 없음"));

        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "카테고리 없음"));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImage(dto.getImage());
        post.setBoardType(boardType);
        post.setCategory(category);
        postRepository.save(post);

        List<Pet> oldPets = petRepository.findAllByPost(post);
        for (Pet pet : oldPets) {
            pet.setPost(null);
        }
        petRepository.saveAll(oldPets);

        if (dto.getPetIdxList() != null && !dto.getPetIdxList().isEmpty()) {
            List<Pet> newPets = petRepository.findAllById(dto.getPetIdxList());
            for (Pet pet : newPets) {
                pet.setPost(post);
            }
            petRepository.saveAll(newPets);
        }

        if (dto.getRemovedImageUrls() != null && !dto.getRemovedImageUrls().isEmpty()) {
            postImageService.deleteImagesByUrls(dto.getRemovedImageUrls());
        }

        if (images != null && !images.isEmpty()) {
            postImageService.saveImages(images, post);
        }
    }

    public List<PostDto.UserPostResponse> findUserPosts(Long userId) {
        return postRepository.findByUserIdxAndIsDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostDto.UserPostResponse::from)
                .toList();
    }
}
