package palvelinohjelmointi.mobiililaitelistaus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin(origins = "https://mobiledevicelist.herokuapp.com", maxAge = 3600)
public class DeviceController {
	@Autowired
	private DeviceRepository devicerepository; 
	
	@Autowired
	private CategoryRepository categoryrepository;
	
	@Autowired
	private ManufactorRepository manufactorrepository;
	
	@RequestMapping(value="/")
    public String gotoLogin() {	
        return "login";
    }


	
	@RequestMapping(value="/login")
	public String login() {	
		return "login";
	}
	
	@RequestMapping(value = "/devicelist", method = RequestMethod.GET)
		public String listingBooks(Model model) {	
			model.addAttribute("devices", devicerepository.findAll());
		//	System.out.println();
			return "devicelist";
	}
	
	 @RequestMapping(value = "/adddevice")
	 	public String addDevice(Model model){
		 	model.addAttribute("devices", new Device());
		 	model.addAttribute("categories", categoryrepository.findAll());
		 	model.addAttribute("manufactories", manufactorrepository.findAll());
		 	
		 	return "adddevice";
 }   
	 @RequestMapping(value = "/addmanufactor")
	 	public String addManufactor(Model model){
		 	model.addAttribute("manufactories", new Manufactor());
		 	return "addmanufactor";
}   
	 @RequestMapping(value = "/addcategory")
	 	public String addCatergory(Model model){
		 	model.addAttribute("categories", new Category());
		 	return "addcategory";
}   
	 
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	    public String saveDevice(Device device){
	        devicerepository.save(device);
	        //String hmm = devicerepository.findById(id).get().getModel();
	      
	       
	        return "redirect:devicelist";
	    }    
	 
	 @RequestMapping(value = "/manusave", method = RequestMethod.POST)
	    public String saveManufactor(Manufactor manufactor){
	        manufactorrepository.save(manufactor);
	       
	        return "redirect:devicelist";
	    }    
	 @RequestMapping(value = "/catesave", method = RequestMethod.POST)
	    public String saveCategory(Category category){
	        categoryrepository.save(category);
	       
	        return "redirect:devicelist";
	    }
	 
	 @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	 @PreAuthorize("hasAuthority('ADMIN')")
	    public String deleteDevice(@PathVariable("id") Long DeviceId, Model model) {
		 	devicerepository.deleteById(DeviceId);
	        return "redirect:../devicelist";
	    }
	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public String editDevice(Model model,@PathVariable("id") Long DeviceId) {
		 model.addAttribute("devices", devicerepository.findAll());
	
		 	model.addAttribute("categories", categoryrepository.findAll());
		 	model.addAttribute("manufactories",manufactorrepository.findAll());
//		 	String hmm = devicerepository.findById(bookId).get().getCategory().getName();
//		 	System.out.println(hmm);
	        return "editdevice";
	 	}
	 @RequestMapping(value = "/modify", method = RequestMethod.POST)
	 @PreAuthorize("hasAuthority('ADMIN')")
	    public String modifyDevice(@RequestParam(name= "manufactor",required=false) Long manu,@RequestParam(name= "category",required=false) Long cat,
	    		@RequestParam(name= "modelname",required=false) String modelname,
	    		@RequestParam(name= "weight",required=false) int weight,
	    		@RequestParam(name= "screen",required=false) double screen,
	    		@RequestParam(name= "id") Long id, Model model) {
		 	//repository.deleteById(id);
		 
		 	model.addAttribute("devices", devicerepository.findAll());
		 //	System.out.println(crepository.findById(cat).get());
		 	
		 	devicerepository.save(new Device(id,modelname,weight,screen,categoryrepository.findById(cat).get(),manufactorrepository.findById(manu).get()));
	        return "redirect:devicelist";
	    }    
	 
		//RESTFUL Devices list
	 @CrossOrigin(origins = "https://mobiledevicelist.herokuapp.com", maxAge = 3600)
	 @RequestMapping(value="/devices", method = RequestMethod.GET)
    public @ResponseBody List<Device> deviceRest() {	
        return (List<Device>) devicerepository.findAll();
    }
		//RESTFUL Brand list
	 @RequestMapping(value="/brands", method = RequestMethod.GET)
 public @ResponseBody List<Manufactor> manufactorRest() {	
     return (List<Manufactor>) manufactorrepository.findAll();
 }    
		//RESTFUL Category list
	 @RequestMapping(value="/categories", method = RequestMethod.GET)
 public @ResponseBody List<Category> categoryRest() {	
     return (List<Category>) categoryrepository.findAll();
 }    


	// RESTful service to get device by id
    @RequestMapping(value="/devices/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Device> findBookRest(@PathVariable("id") Long DeviceId) {	
    	return devicerepository.findById(DeviceId);
    }       
}



