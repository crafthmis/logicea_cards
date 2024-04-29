package com.logicea.cards.repositories;

import com.logicea.cards.models.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c INNER JOIN c.status s WHERE " +
            ":lookupText IS NULL " +
            "OR c.name LIKE :lookupText " +
            "OR c.color LIKE :lookupText " +
            "OR s.name LIKE :lookupText " +
            "AND ((:startDate IS NULL AND :endDate IS NULL) OR " +
            "(c.createdAt <= :endDate AND c.createdAt >= :startDate))")
    Page<Card> search(String lookupText, Date startDate, Date endDate, Pageable pageable);
}

