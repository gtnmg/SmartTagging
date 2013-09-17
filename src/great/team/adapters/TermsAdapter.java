package great.team.adapters;

import great.team.activities.ItemsOverviewActivity;
import great.team.db.DataProviderFactory;
import great.team.entity.Term;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class TermsAdapter extends BaseAdapter {
	private String[] mTerms;
	private Context mContext;

	public TermsAdapter(Context c, String[] terms) {
		mContext = c;
		mTerms =terms;
	}
	public int getCount() {
		return mTerms.length;
	}
	public Object getItem(int position) {
		return mTerms[position];
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
		but.setHeight(130);
		but.setText(mTerms[position]);
		but.setTag(mTerms[position]);
		but.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ItemsOverviewActivity.class);
				String termName = (String)v.getTag();
				Term term = DataProviderFactory.getDataProvider(mContext).findTermByName(
								termName);
				intent.putExtra("term", term);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
		return but;
	}

}

