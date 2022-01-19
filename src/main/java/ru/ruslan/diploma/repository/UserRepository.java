package ru.ruslan.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.diploma.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
