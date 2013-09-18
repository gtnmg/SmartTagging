package great.team.db;

import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.List;

public interface IDataProvider {
	public List<Item> getItems(Catalog cat, Long term_id);
	public Item getItem(Long item_id);
		
	public List<Catalog> getRootCatalogs();
	
	public String[] getTerms(Catalog cat);
	
	public Term findTermByName (String term);

	public void addTerm(Term term);
	public void addCatalog(Catalog catalog);
	public void addItem(Item item, String term, Long catalog_id);
}
