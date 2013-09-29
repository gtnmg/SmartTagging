package great.team.db;

import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public interface IDataProvider {
	public void openDataBase();
	public void closeDataBase();
	public SQLiteDatabase getDataBase();
	
	public List<Item> getItems(Catalog cat, Long term_id);
	public Item getItem(Long item_id);
		
	public List<Catalog> getRootCatalogs();
	
	public String[] getTerms(Catalog cat);
	
	public Term findTermByName (String term);
	public Item findItemByFileURI(String fileUri);

	public Long addTerm(Term term);
	public Long addCatalog(Catalog catalog);
	public Long addItem(Item item);
	public Long addData(Long item_id, Long term_id, Long cataog_id);
	
	public boolean deleteItem(Long item_id);
	public boolean deleteTerm(Long term_id);
	public boolean deleteData(Long data_id);
	public boolean deleteCatalog(Long catalog_id);
	
}
