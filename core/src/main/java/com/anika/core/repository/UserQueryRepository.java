package com.anika.core.repository;

import com.anika.core.entity.User;
import com.anika.core.entity.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQueryRepository extends JpaRepository<UserQuery, Long> {
    List<UserQuery> findAllByUser(User user);
}
