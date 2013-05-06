package great.team.entity;

import java.io.Serializable;

public class Catalog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5258438524028368415L;

	private Long id;
	private Long parent_id;
	private Long weight;
	private String name;
	
	public Catalog(){
		
	}
	
	public Catalog(Long id, Long parent_id, Long weight, String name){
		this.id = id;
		this.parent_id = parent_id;
		this.weight = weight;
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public Long getWeight() {
		return weight;
	}
	public void setWeight(Long weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
