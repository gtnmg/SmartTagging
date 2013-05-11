package great.team.activities;

import great.team.ItemsArrayAdapter;
import great.team.R;
import great.team.R.id;
import great.team.R.layout;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ItemsOverviewActivity extends Activity { 

	private ListView itemsListView;
	List<Item> itemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_overview);
		Bundle bundle = getIntent().getExtras();

		Catalog cat = (Catalog)bundle.get("catalog");
		Term term = (Term)bundle.get("term");

		itemsListView = (ListView)findViewById(R.id.itemsListView);
		IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
		itemList = dataProvider.getItems(cat != null ? cat.getId() : null , term != null ? term.getId() : null);

		ItemsArrayAdapter itemsArrayAdapter = new ItemsArrayAdapter(this, itemList);
		itemsListView.setAdapter(itemsArrayAdapter);
	}
}
