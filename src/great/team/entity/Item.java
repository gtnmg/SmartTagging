package great.team.entity;

public class Item {
	private Long id;
	private String path;
	private String comment;
	
	public Item(){
		
	}
	
	public Item(Long id, String path, String comment){
		this.id = id;
		this.path = path;
		this.comment = comment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
