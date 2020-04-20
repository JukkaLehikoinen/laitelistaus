package palvelinohjelmointi.mobiililaitelistaus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import palvelinohjelmointi.mobiililaitelistaus.domain.DeviceRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Manufactor;
import palvelinohjelmointi.mobiililaitelistaus.domain.CategoryRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Device;
import palvelinohjelmointi.mobiililaitelistaus.domain.ManufactorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

	@Autowired
	private DeviceRepository devicerepository;

	@Autowired
	private CategoryRepository categoryrepository;

	@Autowired
	private ManufactorRepository manufactorrepository;

	@Test
	public void DeviceCheck() {
		List<Device> devices = devicerepository.findByModel("P30 Pro");
		assertThat(devices.get(0).getWeight()).isEqualTo(192);

		devicerepository.save(new Device("Galaxy A8 (2018)", 172, 5.6, categoryrepository.findByTech("Phone").get(0),
				manufactorrepository.findByBrand("Samsung").get(0)));

		assertThat(devicerepository.count()).isEqualTo(7);

		devicerepository.findByModel("Galaxy S10");
		devices.get(0).setScreen(12.6);
		assertThat(devices.get(0).getScreen()).isEqualTo(12.6);

	}

	@Test
	public void CategoryCheck() {

		assertThat(categoryrepository.count()).isEqualTo(3);

		categoryrepository.findByTech("Tablet");
		assertThat(categoryrepository.findByTech("Tablet").isEmpty() == false);

	}

	@Test
	public void ManufactorCheck() {

		manufactorrepository.save(new Manufactor("Xiaomi"));

		assertThat(manufactorrepository.findByBrand("Xiaomi").get(0));

		manufactorrepository.deleteAll();
		assertThat(manufactorrepository.count()).isEqualTo(0);
	}

}