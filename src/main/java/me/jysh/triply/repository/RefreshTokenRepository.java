package me.jysh.triply.repository;

import java.util.Optional;
import me.jysh.triply.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByToken(final String token);

  Integer deleteByToken(final String token);
}
