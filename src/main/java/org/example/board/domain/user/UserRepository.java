package org.example.board.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
//    Optional<User> findByEmail(String username);

    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    void deleteById(Long id);

//    List<SiteUser> findByUsername(String username);
}
