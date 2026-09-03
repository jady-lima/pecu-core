package repositorios;

import modelos.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepositorio extends JpaRepository<Animal, Long> {
}
