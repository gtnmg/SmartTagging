package great.team.activities;

import great.team.R;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Term;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TermsOverviewActivity extends Activity {

	ListView termsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_overview);
		
		termsListView = (ListView) findViewById(R.id.terms_list_view);
		IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
		List<String> terms = dataProvider.getTerms();
		termsListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, terms.toArray(new String[terms.size()])));
		termsListView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
	              String termName = ((TextView) view).getText().toString();
	              Term term = DataProviderFactory.getDataProvider(getApplicationContext()).findTermByName(termName);
	              Intent i = new Intent(getApplicationContext(), ItemsOverviewActivity.class);
	              i.putExtra("term", term);
	              startActivity(i);
			}
		});
	}

}
