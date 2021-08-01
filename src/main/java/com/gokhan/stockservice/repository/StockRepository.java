package com.gokhan.stockservice.repository;

import com.gokhan.stockservice.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("select s from Stock s where s.book.id = :bookId")
    Optional<Stock> findByBook_Id(Long bookId);

    @Modifying
    @Query("update Stock s set s.quantity= :quantity where s.id = :id")
    void updateStockQuantity(@Param("quantity") Integer quantity, @Param("id") Long id);
}
