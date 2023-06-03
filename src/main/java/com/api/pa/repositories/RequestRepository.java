package com.api.pa.repositories;

import com.api.pa.models.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "SELECT r FROM Request r where r.user.userId = :userId ")
    Page<Request> findByUserUserId(@Param("userId") Integer userId, Pageable pageable);
    @Query(value = "SELECT r FROM Request r")
    Page<Request> findAllRequests(Pageable pageable);


}
