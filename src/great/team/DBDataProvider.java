package great.team;

import great.team.entity.Catalog;
import great.team.entity.Item;

import java.util.List;

public class DBDataProvider implements IDataProvider {

	@Override
	public List<Item> find(String substring) {
		return null;
	}

	@Override
	public List<String> autocomplete(String substring) {
		return null;
	}

	@Override
	public List<Catalog> getRootItems() {
		return null;
	}

	@Override
	public List<Catalog> getChildrens(Long id) {
		return null;
	}

	@Override
	public Long findTermByName(String term) {
		return null;
	}

	@Override
	public void addTerm(String name, String term, Long catalog_id) {
	}
}
