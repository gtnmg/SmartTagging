package great.team.activities;

import great.team.CatalogAdapter;
import great.team.R;
import great.team.R.id;
import great.team.R.layout;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

// removed SystemUIHider you can revert this link 
// http://stackoverflow.com/questions/15186111/fullscreen-activity-wizard-activity-how-do-i-stop-actionbar-from-showing-when-i
public class MainViewActivity extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_view);

		final View contentView = findViewById(R.id.catalogGridView);
		GridView gv = (GridView) (contentView);
		IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
		gv.setAdapter(new CatalogAdapter(getApplicationContext(), dataProvider
				.getRootCatalogs()));
		gv.setNumColumns(2);

		List<String> strTerms = dataProvider.getTerms();

		AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.term_autocomplete);
		Button searchTermBtn = (Button) findViewById(R.id.search_term_button);
		searchTermBtn.setOnClickListener(this);

		autoComplete.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, strTerms.toArray(new String [strTerms.size()])));

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.search_term_button:
            	AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.term_autocomplete);
            	String term = autoComplete.getText().toString();
				Intent intent = new Intent(getApplicationContext(), ItemsOverviewActivity.class);
				IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
				intent.putExtra("term", dataProvider.findTermByName(term));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent);
				break;
	    }
		
	}
	
	

}
