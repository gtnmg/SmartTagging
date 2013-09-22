package great.team.adapters;

import great.team.R;
import great.team.activities.ItemsOverviewActivity;
import great.team.entity.Item;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemsArrayAdapter extends ArrayAdapter<Item> {
    private final Context mContext;
    private final List<Item> mItems;

    public ItemsArrayAdapter(Context context, List<Item> items) {
        super(context, R.layout.items_layout,items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.items_layout, parent, false);
        TextView tvItemComment = (TextView) rowView.findViewById(R.id.item_comment);
        TextView tvItemFileURI = (TextView) rowView.findViewById(R.id.item_file_uri);

//      ImageView imageView = (ImageView) rowView.findViewById(R.id.item_icon); // can change image for item
        Item curItem = mItems.get(position);
        tvItemComment.setText(curItem.getComment());
        tvItemFileURI.setText(curItem.getPath());
        rowView.setTag(curItem.getPath());
		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String filePath = (String) v.getTag();
				File file = new File(filePath);
				if (file.exists()) {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					// TODO: add mapping for file extention paths
					// mp3 -> audio/*
					// jpg -> image/*
					intent.setDataAndType(Uri.fromFile(file), "*/*"); 
					mContext.startActivity(intent); 
				}
			}
		});
        return rowView;
    }
}