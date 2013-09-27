package great.team.activities;

import great.team.R;
import great.team.adapters.CatalogSpinnerAdapter;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Catalog;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class EditCatalogActivity extends Activity {
	private ListView m_lvCatalog;
	final int MENU_DELETE = 1;
	final int MENU_EDIT = 0;
	final int MENU_ADD = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IDataProvider db = DataProviderFactory.getDataProvider(this);

		m_lvCatalog = (ListView) findViewById(R.id.lvCatalog);
		m_lvCatalog.setAdapter(new CatalogSpinnerAdapter(this, db
				.getRootCatalogs()));
		registerForContextMenu(m_lvCatalog);
		setContentView(R.layout.activity_edit_catalog);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_catalog, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		switch (v.getId()) {

		case R.id.lvCatalog:
			menu.add(0, MENU_EDIT, 0, "Edit"); // TODO: move text to contants
			menu.add(0, MENU_DELETE, 0, "Delete");
			menu.add(0, MENU_ADD, 0, "Add");
			break;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Catalog cat = (Catalog)m_lvCatalog.getSelectedItem();
		switch (item.getItemId()) {
		// menu items for tvColor
		case MENU_DELETE:
			// do thms
			break;

		case MENU_ADD:
			// do thms
			break;
		case MENU_EDIT:
			// do thms
			break;
		}
		return super.onContextItemSelected(item);
	}

}
