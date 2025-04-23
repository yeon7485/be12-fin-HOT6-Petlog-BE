package com.hot6.backend.pet;

import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUserIdx(Long userIdx);
    List<Pet> findAllByPost(Post post);
    List<Pet> findAllByQuestion(Question question);


}
