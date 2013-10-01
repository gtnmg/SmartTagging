package great.team.preferences;

import great.team.R;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class CatalogEditPreference extends DialogPreference {
    public CatalogEditPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.activity_edit_catalog);
        setPositiveButtonText(android.R.string.ok);
        //setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
    }
}