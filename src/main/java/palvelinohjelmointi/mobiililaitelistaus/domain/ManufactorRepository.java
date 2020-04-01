package palvelinohjelmointi.mobiililaitelistaus.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ManufactorRepository extends CrudRepository<Manufactor, Long> {

    List<Manufactor> findByBrand(String brand);
    
}