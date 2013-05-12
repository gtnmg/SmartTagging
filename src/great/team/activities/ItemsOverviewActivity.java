package great.team.activities;

import great.team.ItemsArrayAdapter;
import great.team.R;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

public class ItemsOverviewActivity extends Activity  implements View.OnClickListener{ 

	private ListView itemsListView;
	List<Item> itemList;
	Long mCatalogId = null;
	Long mTermId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_overview);
		Bundle bundle = getIntent().getExtras();
		
		if(bundle != null){
			Catalog cat = (Catalog)bundle.get("catalog");
			if(cat != null)
				mCatalogId = cat.getId();
			Term term = (Term)bundle.get("term");
			if(term != null)
				mTermId = term.getId();
		}

		itemsListView = (ListView)findViewById(R.id.itemsListView);
		IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
		itemList = dataProvider.getItems( mCatalogId , mTermId );
		ItemsArrayAdapter itemsArrayAdapter = new ItemsArrayAdapter(this, itemList);
		itemsListView.setAdapter(itemsArrayAdapter);

		List<String> strTerms = dataProvider.getTerms(mCatalogId);
		AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.term_autocomplete);
		autoComplete.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, strTerms.toArray(new String [strTerms.size()])));

		Button searchTermBtn = (Button) findViewById(R.id.search_term_button);
		searchTermBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.search_term_button:
				AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.term_autocomplete);
				String termName = autoComplete.getText().toString();
				IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
				Term term = dataProvider.findTermByName(termName);
				if(term !=null)
					mTermId = term.getId();
				itemList = dataProvider.getItems( mCatalogId , mTermId );
				ItemsArrayAdapter itemsArrayAdapter = new ItemsArrayAdapter(this, itemList);
				itemsListView.setAdapter(itemsArrayAdapter);
				break;
		}
		
	}

}
