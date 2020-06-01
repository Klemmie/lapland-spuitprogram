package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.Cultivar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CultivarRepository extends JpaRepository<Cultivar, Long> {
    Cultivar findByCultivarName(String name);

    List<Cultivar> findAll();
}
