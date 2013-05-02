package great.team;

import great.team.entity.Catalog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class CatalogAdapter extends BaseAdapter {
	private List<Catalog> mCatalogs; // root catalogs
	private Context mContext;

	public CatalogAdapter(Context c) {
		mContext = c;
		mCatalogs = new ArrayList<Catalog>();
		mCatalogs.add(new Catalog(Long.parseLong("11"), Long.parseLong("1"), Long.parseLong("40"), "test1"));
		mCatalogs.add(new Catalog(Long.parseLong("12"), Long.parseLong("1"), Long.parseLong("40"), "test2"));
		mCatalogs.add(new Catalog(Long.parseLong("13"), Long.parseLong("1"), Long.parseLong("40"), "test3"));
		mCatalogs.add(new Catalog(Long.parseLong("14"), Long.parseLong("1"), Long.parseLong("40"), "test4"));
		mCatalogs.add(new Catalog(Long.parseLong("15"), Long.parseLong("1"), Long.parseLong("40"), "test5"));
		mCatalogs.add(new Catalog(Long.parseLong("16"), Long.parseLong("1"), Long.parseLong("40"), "test6"));
	}

	@Override
	public int getCount() {
		return mCatalogs.size();
	}

	@Override
	public Object getItem(int position) {
		return mCatalogs.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button but;
		if (convertView == null) {
			but = new Button(mContext);
			// imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
		} else {
			but = (Button) convertView;
		}

		but.setText(mCatalogs.get(position).getName());
//		but.setId((mCatalogs.get(position).getId()));
		but.setTag(mCatalogs.get(position).getId());
		but.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CatalogOverviewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
		return but;
	}

}

