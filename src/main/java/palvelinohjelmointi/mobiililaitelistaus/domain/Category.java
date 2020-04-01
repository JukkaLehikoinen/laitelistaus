package palvelinohjelmointi.mobiililaitelistaus.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryid;
	private String tech;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Device> devices;
	
	
	
	public Category() {}
	
	public Category(String tech) {
		super();
		this.tech= tech;
	}
	
	public Long getCategoryid() {
		return categoryid;
	}
	
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	
	public String getTech() {
		return tech;
	}
	
	public void setTech(String tech) {
		this.tech = tech;
	}

	public List<Device> getDevice() {
		return devices;
	}

	public void setBooks(List<Device> devices) {
		this.devices= devices;
	}

	@Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", tech=" + tech + "]";
	}
}
