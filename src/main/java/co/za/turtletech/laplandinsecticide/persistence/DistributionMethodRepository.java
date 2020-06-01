package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.DistributionMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionMethodRepository extends JpaRepository<DistributionMethod, Long> {
    List<DistributionMethod> findByName(String name);

    List<DistributionMethod> findAll();
}
