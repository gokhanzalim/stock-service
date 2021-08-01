package com.gokhan.stockservice.repository;

import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.response.StatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdAndActive(Long id, boolean isActive);

    @Query("select e from Order e where year(e.createdDate) = ?1 and month(e.createdDate) = ?2")
    List<Order> getByYearAndMonth(int year, int month);

    @Query("select e from Order e where year(e.createdDate) = year(current_date) and  month(e.createdDate) = month(current_date)")
    List<Order> getAllOfCurrentMonth();

    @Query("select year(o.createdDate) AS year ,month(o.createdDate) AS month, count(o.id) AS orderCount, sum(o.quantity) AS bookCount, sum(o.price) AS totalAmount" +
            " from Order o group by year(o.createdDate),month(o.createdDate)" +
            " order by year(o.createdDate),month(o.createdDate)")
    List<StatisticsResponse> getAllOrderStatisticsPerMonth();
}
