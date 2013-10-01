package great.team.activities;

import great.team.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class CatalogSettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
