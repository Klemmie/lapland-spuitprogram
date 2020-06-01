package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByUsername(String username);

    List<Users> findAll();
}
