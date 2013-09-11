package great.team.activities;

import great.team.R;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.dialogs.SelectCatalogDialog;
import great.team.entity.Catalog;
import great.team.entity.Term;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TermsOverviewActivity extends Activity implements OnClickListener{

	ListView mTermsListView;
	Button mBtnSelectCatalog;
	TextView mSelectedCatalog;
	Catalog mCurrentCatalog = null;
	

	private String[] getTerms() {
		IDataProvider dataProvider = DataProviderFactory
				.getDataProvider(getApplicationContext());
		return dataProvider.getTerms(mCurrentCatalog);
	}

	private void init() {
		mTermsListView = (ListView) findViewById(R.id.terms_list_view);

		mTermsListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getTerms()));
		mTermsListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				String termName = ((TextView) view).getText().toString();
				Term term = DataProviderFactory.getDataProvider(
						getApplicationContext()).findTermByName(termName);
				Intent i = new Intent(getApplicationContext(),
						ItemsOverviewActivity.class);
				i.putExtra("term", term);
				startActivity(i);
			}
		});
		mSelectedCatalog = (TextView)findViewById(R.id.tvSelectedCatalog);
		mBtnSelectCatalog = (Button) findViewById(R.id.btnCatalogSelect);
		mBtnSelectCatalog.setOnClickListener(this);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_overview);
		init();
	}

	@Override
	public void onClick(View v) {
		if(v==mBtnSelectCatalog)
		{
			mCurrentCatalog = SelectCatalogDialog.execute(this);
			mSelectedCatalog.setText(mCurrentCatalog==null?"":mCurrentCatalog.getName());
		}
	}

}
