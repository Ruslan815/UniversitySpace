package ru.ruslan.diploma.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.diploma.chat.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
