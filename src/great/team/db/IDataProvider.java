package great.team.db;

import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.List;

public interface IDataProvider {
	public List<Item> getItems(Long catalog_id, Long term_id);
	public Item getItem(Long item_id);
		
	public List<Catalog> getRootCatalogs();
	public List<Catalog> getChildCatalogs(Long id);

	public List<String> getTerms(Long catalog_id);
	public List<String> getTerms();
	
	public Term findTermByName (String term);

	public void addTerm(Term term);
	public void addCatalog(Catalog catalog);
	
}
