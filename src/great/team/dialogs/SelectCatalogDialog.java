package great.team.dialogs;

import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Catalog;
import great.team.interfaces.ICatalogSetter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class SelectCatalogDialog {
	private static List<Catalog> mCatalogs = null;
	private ICatalogSetter mSetter;

	public SelectCatalogDialog(ICatalogSetter setter) { // here we should pass some activity wich implement ICatalogSetter
		mSetter = null;
		if( setter instanceof Activity )
		{
			mSetter = setter;
			Activity act = (Activity)mSetter;
			createDialog(act).show();
		}
	}
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
	}

	private Builder createDialog(Activity act) {
		init(act);
		Builder builder = new AlertDialog.Builder(act);
		builder.setTitle("Select catalog"); // TODO: localization
		builder.setCancelable(false);
	
		builder.setSingleChoiceItems(getCatalogNames(), -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						mSetter.setCatalog(mCatalogs.get(item));
						mSetter=null;
						dialog.cancel();
					}
				});
		return builder;
	}
    
	public static void execute(ICatalogSetter setter) {
		new SelectCatalogDialog(setter);
	}
}
