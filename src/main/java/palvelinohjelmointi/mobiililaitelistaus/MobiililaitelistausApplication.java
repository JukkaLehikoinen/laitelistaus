package palvelinohjelmointi.mobiililaitelistaus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import palvelinohjelmointi.mobiililaitelistaus.domain.User;
import palvelinohjelmointi.mobiililaitelistaus.domain.UserRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Category;
import palvelinohjelmointi.mobiililaitelistaus.domain.CategoryRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Device;
import palvelinohjelmointi.mobiililaitelistaus.domain.DeviceRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Manufactor;
import palvelinohjelmointi.mobiililaitelistaus.domain.ManufactorRepository;

@SpringBootApplication
public class MobiililaitelistausApplication {
	private static final Logger log = LoggerFactory.getLogger(MobiililaitelistausApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MobiililaitelistausApplication.class, args);
	}

	@Bean
	public CommandLineRunner Listaus(DeviceRepository devicerepository, CategoryRepository categoryrepository, ManufactorRepository manufactorrepository, UserRepository urepository) {
		return (args) -> {
			log.info("Lisätään laitteita");
			categoryrepository.save(new Category("Phone"));
			categoryrepository.save(new Category("Tablet"));
			categoryrepository.save(new Category("Watch"));
			
			manufactorrepository.save(new Manufactor("Apple"));
			manufactorrepository.save(new Manufactor("Huawei"));
			manufactorrepository.save(new Manufactor("Samsung"));
			manufactorrepository.save(new Manufactor("Nokia"));
						
						
			devicerepository.save(new Device("Galaxy S10",157,6.1,categoryrepository.findByTech("Phone").get(0),manufactorrepository.findByBrand("Samsung").get(0)));
			devicerepository.save(new Device("P30 Pro",192,6.47,categoryrepository.findByTech("Phone").get(0),manufactorrepository.findByBrand("Huawei").get(0)));
			devicerepository.save(new Device("iPhone X",174,5.9,categoryrepository.findByTech("Phone").get(0),manufactorrepository.findByBrand("Apple").get(0)));
			devicerepository.save(new Device("7.2",180,6.3,categoryrepository.findByTech("Phone").get(0),manufactorrepository.findByBrand("Nokia").get(0)));
			devicerepository.save(new Device("iPad Pro 12.9",641,12.9,categoryrepository.findByTech("Tablet").get(0),manufactorrepository.findByBrand("Apple").get(0)));
			devicerepository.save(new Device("Galaxy Tab A 10.1 (2019)",469,10.1,categoryrepository.findByTech("Tablet").get(0),manufactorrepository.findByBrand("Samsung").get(0)));
			
			// Create users: admin/admin user/user
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER","user@user.fi");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN","admin@admin.fi");
			urepository.save(user1);
			urepository.save(user2);
			
			
			
			log.info("fetch all categories, manufactors and devices");
			for (Category category : categoryrepository.findAll()) {
				log.info(category.toString());
			}
			for (Manufactor manufactor : manufactorrepository.findAll()) {
				log.info(manufactor.toString());
			}
			for (Device device : devicerepository.findAll()) {
				log.info(device.toString());
			}

		};
	}
}
