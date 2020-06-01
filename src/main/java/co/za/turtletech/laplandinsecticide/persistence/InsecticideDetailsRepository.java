package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.InsecticideDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsecticideDetailsRepository extends JpaRepository<InsecticideDetails, Long> {
    List<InsecticideDetails> findByStreetName(String name);

    List<InsecticideDetails> findAll();
}
