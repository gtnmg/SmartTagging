package great.team;

import great.team.entity.Catalog;
import great.team.entity.Item;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CatalogOverviewActivity extends Activity { 

	private ListView itemsListView;
	List<Item> itemList;

	private String itemsArray[]={"item1","item2","item3","item4"};  // fake items

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog_overview);
		Bundle bundle = getIntent().getExtras();
		Catalog cat = (Catalog)bundle.get("catalog");

		itemsListView = (ListView)findViewById(R.id.itemsListView);
		DBDataProvider dbDataProvider = new DBDataProvider(this);
		itemList = dbDataProvider.getItems(cat.getId(), null);
/*
 		itemList = new ArrayList<Item>();
		for(int i = 0 ; i < itemsArray.length; i ++){ 
			itemList.add(new Item(Long.valueOf(i), "somePath" , cat.getName() + itemsArray[i]));
		}
*/

		ItemsArrayAdapter itemsArrayAdapter = new ItemsArrayAdapter(this, itemList);
		itemsListView.setAdapter(itemsArrayAdapter);
	}
}
