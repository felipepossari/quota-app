package com.felipepossari.quota.user.repository.mysql;

import com.felipepossari.quota.user.repository.mysql.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}
