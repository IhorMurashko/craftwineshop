package com.craftWine.shop.repositories;

import com.craftWine.shop.models.WineComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineCommentRepository extends JpaRepository<WineComment, Long> {



}
