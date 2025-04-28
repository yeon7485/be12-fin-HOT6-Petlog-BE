package com.hot6.backend.board.post;

import com.hot6.backend.board.comment.CommentService;
import com.hot6.backend.board.post.images.PostImageService;
import com.hot6.backend.board.post.model.BoardType;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.post.model.PostDto;
import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostImageService postImageService;
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PetRepository petRepository;

    @Transactional
    public void create(User user,PostDto.PostRequest dto, List<MultipartFile> images) {
        BoardType boardType = boardTypeRepository.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_BOARD_NOT_FOUND));

        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .image(dto.getImage())
                .category(category)
                .boardType(boardType)
                .build();

        try {
            postRepository.save(post);
            if (dto.getPetIdxList() != null) {
                List<Pet> pets = petRepository.findAllById(dto.getPetIdxList());
                pets.forEach(p -> p.setPost(post));
                petRepository.saveAll(pets);
            }
            if (images != null && !images.isEmpty()) {
                postImageService.saveImages(images, post);
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.POST_CREATE_FAILED);
        }
    }

    public Page<PostDto.PostResponse> list(String boardName, int page, int size) {
        BoardType boardType = boardTypeRepository.findByBoardName(boardName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByBoardType(boardType, pageable)
                .map(PostDto.PostResponse::from);
    }

    public PostDto.PostResponse read(Long idx) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        return PostDto.PostResponse.from(post);
    }

    public Page<PostDto.PostResponse> search(String boardName, String categoryName, String keyword, int page, int size) {
        BoardType boardType = boardTypeRepository.findByBoardName(boardName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        try {
            if (keyword != null && !keyword.isBlank()) {
                return postRepository.findByBoardTypeAndCategoryNameAndTitleContainingIgnoreCase(boardType, categoryName, keyword, pageable)
                        .map(PostDto.PostResponse::from);
            }
            return postRepository.findByBoardTypeAndCategoryName(boardType, categoryName, pageable)
                    .map(PostDto.PostResponse::from);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.POST_SEARCH_FAILED);
        }
    }

    @Transactional
    public void delete(Long idx) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        try {
            List<Pet> pets = petRepository.findAllByPost(post);
            pets.forEach(p -> p.setPost(null));
            postRepository.delete(post);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.POST_DELETE_FAILED);
        }
    }

    @Transactional
    public void update(Long idx, PostDto.PostRequest dto, List<MultipartFile> images) {
        Post post = postRepository.findById(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        BoardType boardType = boardTypeRepository.findByBoardName(dto.getBoardType())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_UPDATE_FAILED));
        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_UPDATE_FAILED));

        try {
            post.setTitle(dto.getTitle());
            post.setContent(dto.getContent());
            post.setImage(dto.getImage());
            post.setBoardType(boardType);
            post.setCategory(category);
            postRepository.save(post);

            List<Pet> oldPets = petRepository.findAllByPost(post);
            oldPets.forEach(p -> p.setPost(null));
            petRepository.saveAll(oldPets);

            if (dto.getPetIdxList() != null) {
                List<Pet> newPets = petRepository.findAllById(dto.getPetIdxList());
                newPets.forEach(p -> p.setPost(post));
                petRepository.saveAll(newPets);
            }

            if (dto.getRemovedImageUrls() != null) {
                postImageService.deleteImagesByUrls(dto.getRemovedImageUrls());
            }

            if (images != null && !images.isEmpty()) {
                postImageService.saveImages(images, post);
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.POST_UPDATE_FAILED);
        }
    }

    public List<PostDto.UserPostResponse> findUserPosts(Long userId) {
        return postRepository.findByUserIdxAndIsDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostDto.UserPostResponse::from)
                .toList();
    }
}

