package great.team;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class CatalogAdapter extends BaseAdapter {
	private List<String> mStrings;
	private Context mContext;

	public CatalogAdapter(Context c) {
		mContext = c;
		mStrings = new ArrayList<String>();
		mStrings.add("test1");
		mStrings.add("test2");
		mStrings.add("test3");
		mStrings.add("test4");
		mStrings.add("test5");
	}

	public int getCount() {
		return mStrings.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Button but;
		if (convertView == null) {
			but = new Button(mContext);
			// imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
		} else {
			but = (Button) convertView;
		}

		but.setText(mStrings.get(position));

		return but;
	}
}

