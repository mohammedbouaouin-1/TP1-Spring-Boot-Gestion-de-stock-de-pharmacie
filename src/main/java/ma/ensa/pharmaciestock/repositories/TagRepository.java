package ma.ensa.pharmaciestock.repositories;

import ma.ensa.pharmaciestock.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByLibelle(String libelle);
}
