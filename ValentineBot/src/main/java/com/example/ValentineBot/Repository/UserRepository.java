package com.example.ValentineBot.Repository;

import com.example.ValentineBot.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByTgName(String TgName);

    Optional<User> findById(Integer id);

    User save(User user);

    Boolean existsByChatId(String chatId);

    Boolean existsByTgName(String tg_name);

    User findByChatId(String chatId);
}
