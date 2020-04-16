package palvelinohjelmointi.mobiililaitelistaus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import palvelinohjelmointi.mobiililaitelistaus.domain.Category;
import palvelinohjelmointi.mobiililaitelistaus.domain.CategoryRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Device;
import palvelinohjelmointi.mobiililaitelistaus.domain.DeviceRepository;
import palvelinohjelmointi.mobiililaitelistaus.domain.Manufactor;
import palvelinohjelmointi.mobiililaitelistaus.domain.ManufactorRepository;

@Controller
@CrossOrigin(origins = "*")
public class DeviceController {
	@Autowired
	private DeviceRepository devicerepository;

	@Autowired
	private CategoryRepository categoryrepository;

	@Autowired
	private ManufactorRepository manufactorrepository;

	@RequestMapping(value = "/")
	public String gotoLogin() {
		return "login";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/devicelist", method = RequestMethod.GET)
	public String listingBooks(Model model) {
		model.addAttribute("devices", devicerepository.findAll());
		//System.out.println(devicerepository.findAll());
		return "devicelist";
	}

	@RequestMapping(value = "/adddevice")
	public String addDevice(Model model) {
		model.addAttribute("devices", new Device());
		model.addAttribute("categories", categoryrepository.findAll());
		model.addAttribute("manufactories", manufactorrepository.findAll());

		return "adddevice";
	}

	@RequestMapping(value = "/addmanufactor")
	public String addManufactor(Model model) {
		model.addAttribute("manufactories", new Manufactor());
		return "addmanufactor";
	}
	@RequestMapping(value = "/listingmanufactor")
	public String ListingManufactor(Model model) {
		model.addAttribute("manufactories",manufactorrepository.findAll());
		//model.addAttribute("categories", new Category());
		
		return "listingmanufactor";
	}

	@RequestMapping(value = "/listingcategory")
	public String ListingCatergory(Model model) {
		model.addAttribute("categories",categoryrepository.findAll());
		//model.addAttribute("categories", new Category());
		
		return "listingcategory";
	}
	@RequestMapping(value = "/addcategory")
	public String addCatergory(Model model) {
		//model.addAttribute("categories",categoryrepository.findAll());
		model.addAttribute("categories", new Category());
		
		return "addcategory";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveDevice(Device device) {
		
		if (device.getModel()=="") {
			device.setModel("Unknown super duper device");
		}
		if (device.getWeight()==0) {
			device.setWeight(1);
		}
		if (device.getScreen()==0.0) {
			device.setScreen(0.1);
		}
		devicerepository.save(device);

		return "redirect:devicelist";
	}

	@RequestMapping(value = "/manusave", method = RequestMethod.POST)
	public String saveManufactor(Manufactor manufactor) {
		if (manufactor.getBrand()=="") {
			manufactor.setBrand("Unknown brand");
		}
		
		String upperCaser = manufactor.getBrand().substring(0, 1).toUpperCase() + manufactor.getBrand().substring(1);
		manufactor.setBrand(upperCaser);
		manufactorrepository.save(manufactor);

		
		
		return "redirect:listingmanufactor";
	}

	@RequestMapping(value = "/catesave", method = RequestMethod.POST)
	public String saveCategory(Category category) {
		if (category.getTech()=="") {
			category.setTech("Unknown tech");
		}
		
		String upperCaser = category.getTech().substring(0, 1).toUpperCase() + category.getTech().substring(1);
		category.setTech(upperCaser);
		
		categoryrepository.save(category);

		return "redirect:listingcategory";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteDevice(@PathVariable("id") Long DeviceId, Model model) {
		devicerepository.deleteById(DeviceId);
		return "redirect:../devicelist";
	}
	
	@RequestMapping(value = "/catedelete/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteCategory(@PathVariable("id") Long cateId, Model model) {
		categoryrepository.deleteById(cateId);
		return "redirect:../listingcategory";
	}
	
	@RequestMapping(value = "/manudelete/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteManufactor(@PathVariable("id") Long manuId, Model model) {
		manufactorrepository.deleteById(manuId);
		return "redirect:../listingmanufactor";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editDevice(Model model, @PathVariable("id") Long DeviceId) {
		model.addAttribute("devices", devicerepository.findAll());

		model.addAttribute("categories", categoryrepository.findAll());
		model.addAttribute("manufactories", manufactorrepository.findAll());
//		 	String hmm = devicerepository.findById(bookId).get().getCategory().getName();
//		 	System.out.println(hmm);
		return "editdevice";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String modifyDevice(@RequestParam(name = "manufactor", required = false) Long manu,
			@RequestParam(name = "category", required = false) Long cat,
			@RequestParam(name = "modelname", required = false) String modelname,
			@RequestParam(name = "weight", required = false) int weight,
			@RequestParam(name = "screen", required = false) double screen, @RequestParam(name = "id") Long id,
			Model model) {
		 //devicerepository.findById(id);			
		//model.addAttribute("devices", devicerepository.findAll());
		//devicerepository.findById(id);
		//System.out.println(devicerepository.findAll());

		devicerepository.save(new Device(id,modelname, weight, screen, categoryrepository.findById(cat).get(),
				manufactorrepository.findById(manu).get()));
		return "redirect:devicelist";
	}

	// RESTFUL Devices list
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public @ResponseBody List<Device> deviceRest() {
		return ((List<Device>) devicerepository.findAll());
	}
	
	// RESTFUL Brand list
	@RequestMapping(value = "/brands", method = RequestMethod.GET)
	public @ResponseBody List<Manufactor> manufactorRest() {
		return (List<Manufactor>) manufactorrepository.findAll();
	}
	
	// RESTFUL Category list
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public @ResponseBody List<Category> categoryRest() {
		return (List<Category>) categoryrepository.findAll();
	}

	// RESTful service to get device by id
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Device> findDeviceRest(@PathVariable("id") Long DeviceId) {
		return devicerepository.findById(DeviceId);
	}
	
	// RESTful service to get manufactor by manufactorid
	@RequestMapping(value = "/brands/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Manufactor> findManufactorRest(@PathVariable("id") Long manufactorId) {
		return manufactorrepository.findById(manufactorId);
	}
	
	// RESTful service to get categories by categoryid
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Category> findCategoryRest(@PathVariable("id") Long categoryId) {
		return categoryrepository.findById(categoryId);
		
	}
	
	
	
	@CrossOrigin(origins = "http://localhost:3000/*")
	//REST-metodi kyselyn lisäämiseen
		@PostMapping("/devices")
		public @ResponseBody Device addNewDevice(@RequestBody Device device) {
			devicerepository.save(device);
			return device;
		}
}
