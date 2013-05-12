package great.team.activities;

import great.team.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

// TODO: use not deprecated class for tabs
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
}
