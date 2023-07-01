package com.api.pa.repositories;

import com.api.pa.models.Request;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "SELECT r FROM Request r where r.user.userId = :userId ")
    List<Request> findByUserUserId(@Param("userId") Integer userId, Sort sort);
    @Query(value = "SELECT r FROM Request r")
    List<Request> findAllRequests(Sort sort);


}
