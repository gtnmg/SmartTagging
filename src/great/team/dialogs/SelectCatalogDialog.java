package great.team.dialogs;

import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Catalog;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class SelectCatalogDialog {
	private static List<Catalog> mCatalogs = null;
	private Catalog mSelectedCatalog;

	public static void reloadData() {
		mCatalogs = null;
	}

	private String[] getCatalogNames() {
		String[] result = null;
		if (mCatalogs != null) {
			result = new String[mCatalogs.size()];
			int i = 0;
			for (Catalog cat : mCatalogs)
				result[i++] = cat.getName();
		}
		return result;
	}

	private void init(Activity act) {
		if (mCatalogs == null) {
			IDataProvider dataProvider = DataProviderFactory
					.getDataProvider(act.getApplicationContext());
			mCatalogs = dataProvider.getRootCatalogs();
		}
		mSelectedCatalog = null;
	}

	private Builder createDialog(Activity act) {
		init(act);
		Builder builder = new AlertDialog.Builder(act);
		builder.setTitle("Select catalog"); // TODO: localization
		builder.setCancelable(false);
	
		builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mSelectedCatalog = null;
				dialog.cancel();
			}
		});
		builder.setSingleChoiceItems(getCatalogNames(), -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						mSelectedCatalog = mCatalogs.get(item);
						dialog.cancel();
					}
				});
		return builder;
	}

	public static Catalog execute(Activity activity) 
	{
		SelectCatalogDialog dialog = new SelectCatalogDialog();
		dialog.createDialog(activity).show();
	    return dialog.mSelectedCatalog;
	}
}
