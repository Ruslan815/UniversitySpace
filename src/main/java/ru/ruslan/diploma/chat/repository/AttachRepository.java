package ru.ruslan.diploma.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.diploma.chat.model.Attach;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Integer> {
    boolean existsByFileName(String fileName);
    Attach findByFileName(String fileName);
}
