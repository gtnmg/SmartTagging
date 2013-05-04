package great.team;

import great.team.entity.Item;

import java.util.List;

import android.content.Context;
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
        TextView textView = (TextView) rowView.findViewById(R.id.item_label);
//      ImageView imageView = (ImageView) rowView.findViewById(R.id.item_icon); // can change image for item 
        textView.setText(mItems.get(position).getComment());
        return rowView;
    }
}