package great.team.activities;

import great.team.R;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

// TODO: remove this epta
public class MainTabLayoutActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab_layout);
		
		Resources ressources = getResources();
		TabHost tabHost = getTabHost(); 
 
		// Catalogs tab
		Intent intentCatalogs = new Intent().setClass(this, MainViewActivity.class);
		TabSpec tabSpecCatalogs = tabHost
		  .newTabSpec("Catalogs")
		  .setIndicator("Catalogs")
		  .setContent(intentCatalogs);
 
		// Terms tab
		Intent intentTerms = new Intent().setClass(this, TermsOverviewActivity.class);
		TabSpec tabSpecTerms = tabHost
		  .newTabSpec("Terms")
		  .setIndicator("Terms")
		  .setContent(intentTerms);
 
		// Items tab
		Intent intentItems = new Intent().setClass(this, ItemsOverviewActivity.class);
		TabSpec tabSpecItems = tabHost
		  .newTabSpec("Items")
		  .setIndicator("Items")
		  .setContent(intentItems);
 
		// add all tabs 
		tabHost.addTab(tabSpecCatalogs);
		tabHost.addTab(tabSpecTerms);
		tabHost.addTab(tabSpecItems);
 
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
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
		case R.id.action_search_term:
			break;
		default:
			break;
		}
		return true;
	}

}
