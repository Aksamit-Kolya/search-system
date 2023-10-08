package com.anika.core.repository;

import com.anika.core.entity.Query;
import com.anika.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findAllByUsers_NameOrderByTimestampAsc(String userName);
    List<Query> findAllByUsersOrderByTimestampDesc(User user);

    @org.springframework.data.jpa.repository.Query("SELECT q FROM Query q JOIN q.users u WHERE u.id = :userId ORDER BY q.timestamp DESC")
    List<Query> findAllByUserIdSortedByTimestamp(@Param("userId") Long userId);
}
