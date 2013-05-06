package great.team;

import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.List;

public interface IDataProvider {
	public List<Item> find(String substring);
	public List<String> autocomplete(String substring);

	public List<Catalog> getRootCatalogs();
	public List<Catalog> getChildCatalogs(Long id);
	
	public Term findTermByName (String term);

	public void addTerm(Term term);
	public void addCatalog(Catalog catalog);
	
}
