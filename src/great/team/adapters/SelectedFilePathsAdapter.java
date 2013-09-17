package great.team.adapters;

import great.team.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SelectedFilePathsAdapter extends ArrayAdapter<String>{

	List<String> mSelectedFilePaths = null;
	Context mContext;
	
	public SelectedFilePathsAdapter(Context context, List<String> filePaths){
		super(context, R.layout.selected_file, filePaths);
		mSelectedFilePaths = filePaths;
		mContext = context;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.selected_file, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.tv_selected_file_path);
        textView.setText(mSelectedFilePaths.get(position));
        Button btnDeleteSelectedFile = (Button)rowView.findViewById(R.id.btn_delete_selected_file);
        btnDeleteSelectedFile.setTag(mSelectedFilePaths.get(position));
        btnDeleteSelectedFile.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String filePath = (String)v.getTag();
				mSelectedFilePaths.remove(filePath);
				notifyDataSetChanged();
			}
		});
        return rowView;
    }

}
