package great.team.activities;

import great.team.R;
import great.team.adapters.TermsAdapter;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.dialogs.SelectCatalogDialog;
import great.team.entity.Catalog;
import great.team.interfaces.ICatalogSetter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class TermsOverviewActivity extends Activity implements OnClickListener, ICatalogSetter {

	GridView mGvTerms;
	Button mBtnSelectCatalog;
	Button mBtnCancelCatalogSelection;
	Button mBtnSearchTerm;
	AutoCompleteTextView mAutocompleteTerms;
	TextView mSelectedCatalog;
	Catalog mCurrentCatalog = null;

	private String[] getTerms() {
		IDataProvider dataProvider = DataProviderFactory
				.getDataProvider(getApplicationContext());
		return dataProvider.getTerms(mCurrentCatalog);
	}

	private void refreshView() {
		mGvTerms.setAdapter(new TermsAdapter(getApplicationContext(),getTerms()));
	}

	private void setCatalogLabel() {
		mSelectedCatalog.setText(mCurrentCatalog == null ? "" : "Catalog:"
				+ mCurrentCatalog.getName());
	}

	private void init() {
		mGvTerms = (GridView) findViewById(R.id.gvTerms);
		refreshView();
		mSelectedCatalog = (TextView) findViewById(R.id.tvSelectedCatalog);
		mBtnSelectCatalog = (Button) findViewById(R.id.btnCatalogSelect);
		mBtnCancelCatalogSelection = (Button) findViewById(R.id.btnClearCatalogSelection);
		mBtnCancelCatalogSelection.setOnClickListener(this);
		mBtnSelectCatalog.setOnClickListener(this);

		IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
		String[] strTerms = dataProvider.getTerms(null);
		
		mAutocompleteTerms = (AutoCompleteTextView) findViewById(R.id.term_autocomplete);
		
		//mBtnSearchTerm = (Button) findViewById(R.id.search_term_button);
		//mBtnSearchTerm.setOnClickListener(this);

		mAutocompleteTerms.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, strTerms));
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_overview);
		init();
	}

	@Override
	public void onClick(View v) {
		if (v == mBtnSelectCatalog) {
			SelectCatalogDialog.execute(this);
		} else if (v == mBtnCancelCatalogSelection) {
			mCurrentCatalog = null;
			setCatalogLabel();
			refreshView();
		} else if (v == mBtnSearchTerm){
        	AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.term_autocomplete);
        	String term = autoComplete.getText().toString();
			Intent intent = new Intent(getApplicationContext(), ItemsOverviewActivity.class);
			IDataProvider dataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
			intent.putExtra("term", dataProvider.findTermByName(term));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(intent);
		}
	}

	public void setCatalog(Catalog cat) {
		mCurrentCatalog = cat;
		setCatalogLabel();
		refreshView();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		ActionBar bar = getActionBar();
//		bar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main_tab_layout, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_item:
			Intent intent = new Intent(getApplicationContext(), AddTagActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(intent);
			break;
/*		case R.id.action_search_term:
			break;
		default:
			break;*/
		}
		return true;
	}
}
