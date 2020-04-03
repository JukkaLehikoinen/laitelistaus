package palvelinohjelmointi.mobiililaitelistaus.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="manufactor")
public class Manufactor {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long manufactorid;
	private String brand;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "manufactor")
	private List<Device> devices;
	
	public Manufactor() {}
	
	public Manufactor(String brand) {
		super();
		this.brand= brand;
	}
	
	public Long getManufactorid() {
		return manufactorid;
	}
	
	public void setManufactorid(Long manufactorid) {
		this.manufactorid = manufactorid;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand (String brand) {
		this.brand = brand;
	}

	public List<Device> getDevice() {
		return devices;
	}

	public void setBooks(List<Device> devices) {
		this.devices= devices;
	}

	@Override
	public String toString() {
		return "Manufactor [manufactorid=" + manufactorid + ", brand=" + brand + "]";
	}
}
