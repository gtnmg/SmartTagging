package great.team.adapters;

import great.team.R;
import great.team.entity.Catalog;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//TODO: remove this adapter??
public class CatalogSpinnerAdapter extends ArrayAdapter<Catalog>{

    private final Context mContext;
    private final List<Catalog> mCatalogs;

    public CatalogSpinnerAdapter(Context context, List<Catalog> catalogs) {
        super(context, R.layout.catalog_spinner_layout, R.id.catalogSpinnerItem, catalogs);
        this.mContext = context;
        this.mCatalogs = catalogs;
    }

    
    @Override
	public Catalog getItem(int position) {
		return mCatalogs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mCatalogs.get(position).getId();
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.catalog_spinner_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.catalogSpinnerItem);
        textView.setText(mCatalogs.get(position).getName());
        return rowView;
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
    	return getView(position, convertView, parent);
    }
}
