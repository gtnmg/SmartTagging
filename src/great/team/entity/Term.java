package great.team.entity;

import java.io.Serializable;

public class Term  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4957843424126698456L;
	
	private Long id;
	private String name;
	private Long weight;

	public Term(){
		
	}
	
	public Term(Long id, String name, Long weight){
		this.id = id;
		this.name = name;
		this.weight = weight;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getWeight() {
		return weight;
	}
	public void setWeight(Long weight) {
		this.weight = weight;
	} 
}
