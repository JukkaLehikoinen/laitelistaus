package palvelinohjelmointi.mobiililaitelistaus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Device {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String model;
	private int weight;
	private double screen;
	
	
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="categoryid")
	private Category category;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="manufactorid")
	private Manufactor manufactor;
	
	public Device() {
		
	}

	public Device(String model, int weight, double screen, Category category, Manufactor manufactor) {
		super();
		this.model = model;
		this.weight = weight;
		this.screen = screen;
		this.category = category;
		this.manufactor = manufactor;
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public double getScreen() {
		return screen;
	}

	public void setScreen(double screen) {
		this.screen = screen;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category= category;
	}
	
	public Manufactor getManufactor() {
		return manufactor;
	}

	public void setManufactor(Manufactor manufactor) {
		this.manufactor= manufactor;
	}

	@Override
	public String toString() {
	
			return "Device [model=" + model + ", weight=" + weight+ ", screen=" + screen + "]";
		
	}
	 

}
