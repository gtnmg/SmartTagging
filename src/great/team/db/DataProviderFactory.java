package great.team.db;

import android.content.Context;

public class DataProviderFactory {

	private static IDataProvider mDataProvider = null;

	public static IDataProvider getDataProvider(Context ctx) {
		/**
		 * use the application context as suggested by CommonsWare. this will
		 * ensure that you dont accidentally leak an Activitys context (see this
		 * article for more information:
		 * http://developer.android.com/resources/articles
		 * /avoiding-memory-leaks.html)
		 */
		if (mDataProvider == null) {
			mDataProvider = new DBDataProvider(ctx.getApplicationContext());
		}
		return mDataProvider;
	}
}
