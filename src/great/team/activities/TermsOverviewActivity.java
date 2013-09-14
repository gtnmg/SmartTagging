package great.team.activities;

import great.team.R;
import great.team.adapters.TermsAdapter;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.dialogs.SelectCatalogDialog;
import great.team.entity.Catalog;
import great.team.interfaces.ICatalogSetter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class TermsOverviewActivity extends Activity implements OnClickListener, ICatalogSetter {

	GridView mGvTerms;
	Button mBtnSelectCatalog;
	Button mBtnCancel;
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
		mBtnCancel = (Button) findViewById(R.id.btnClearCatalogSelection);
		mBtnCancel.setOnClickListener(this);
		mBtnSelectCatalog.setOnClickListener(this);
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
		} else if (v == mBtnCancel) {
			mCurrentCatalog = null;
			setCatalogLabel();
			refreshView();
		}
	}
	public void setCatalog(Catalog cat) {
		mCurrentCatalog = cat;
		setCatalogLabel();
		refreshView();
	}
}
