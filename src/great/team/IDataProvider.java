package great.team;

import great.team.entity.Catalog;
import great.team.entity.Item;

import java.util.List;

public interface IDataProvider {
	public List<Item> find(String substring);
	public List<String> autocomplete(String substring);

	public List<Catalog> getRootCatalogs();
	public List<Catalog> getChildCatalogs(Long id);
	
	public Long findTermByName (String term);

	public void addTerm(String name, String term, Long catalog_id);
	public void addCatalog(Catalog catalog);
	
}
