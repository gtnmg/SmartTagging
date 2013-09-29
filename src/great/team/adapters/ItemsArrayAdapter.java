package great.team.adapters;

import great.team.R;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.entity.Item;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemsArrayAdapter extends ArrayAdapter<Item> {
    private final Context mContext;
    private final List<Item> mItems;
    private IDataProvider mDataProvider;
    Map <String, Bitmap> mThumbnails;

    public ItemsArrayAdapter(Context context, List<Item> items) {
        super(context, R.layout.items_layout,items);
        this.mContext = context;
        this.mItems = items;
        mDataProvider = DataProviderFactory.getDataProvider(mContext);
        mThumbnails = new HashMap<String, Bitmap>();
    }
    
    public Bitmap getThumbnail(ContentResolver cr, String path) throws Exception {
    	File file = new File(path);
    	if(!file.exists())
    		return null;

    	String fileExt = path.substring(path.lastIndexOf('.'), path.length()).toLowerCase();;
    	if(!fileExt.equals(".jpg")) // only for jpg yet
    		return null;
 
    	if(mThumbnails.containsKey(path))
    		return mThumbnails.get(path);
    		
        Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.MediaColumns._ID }, MediaStore.MediaColumns.DATA + "=?", new String[] {path}, null);
        if (ca != null && ca.moveToFirst()) {
            int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
            mThumbnails.put(path, MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null ));
        }
        ca.close();
        return mThumbnails.get(path);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.items_layout, parent, false);
        TextView tvItemComment = (TextView) rowView.findViewById(R.id.item_comment);
        TextView tvItemFileURI = (TextView) rowView.findViewById(R.id.item_file_uri);
        Button btnDeleteItem = (Button) rowView.findViewById(R.id.btn_delete_item);
        btnDeleteItem.setTag(position);
        btnDeleteItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curPos = (Integer) v.getTag();
//				Item curItem = mItems.get(curPos);
//				mDataProvider.deleteItem(item_id);
			}
		});

        Item curItem = mItems.get(position);
        
		try {
			Bitmap bMap = getThumbnail(mContext.getContentResolver(),curItem.getPath());
			if (bMap != null) {
				ImageView imageView = (ImageView) rowView.findViewById(R.id.item_icon); 
				imageView.setImageBitmap(bMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
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