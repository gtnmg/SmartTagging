package great.team.activities;

import great.team.R;
import great.team.adapters.SelectedFilePathsAdapter;
import great.team.dialogs.OpenFileDialog;
import great.team.dialogs.SelectCatalogDialog;
import great.team.entity.Catalog;
import great.team.interfaces.ICatalogSetter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AddTagActivity extends Activity implements OnClickListener, ICatalogSetter, OpenFileDialog.OnFileSelectedListener { 


	private Catalog mCurrentCatalog;
	Button mBtnSelectCatalog;
	Button mBtnOpenFileDialog;
	TextView mTvSelectedCatalog;
	OpenFileDialog mOpenFileDialog;

	ListView mLstSelectedFilePaths;
	List<String> mSelectedFilePaths = null;
	SelectedFilePathsAdapter mSelectedFilePathAdapter = null;

	void init(){
		mTvSelectedCatalog = (TextView) findViewById(R.id.tvSelectedCatalog);
		mBtnSelectCatalog = (Button) findViewById(R.id.btnCatalogSelect);
		mBtnSelectCatalog.setOnClickListener(this);
		mBtnOpenFileDialog = (Button) findViewById(R.id.btnOpenFileDialog);
		mBtnOpenFileDialog.setOnClickListener(this);
		mOpenFileDialog = new OpenFileDialog(this, null, null, this);
		
		mSelectedFilePaths = new ArrayList<String>();
		mSelectedFilePathAdapter = new SelectedFilePathsAdapter(this, mSelectedFilePaths);
		mLstSelectedFilePaths = (ListView) findViewById(R.id.lst_selected_file_paths);
		mLstSelectedFilePaths.setAdapter(mSelectedFilePathAdapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tag);
		
		init();
		
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if ("text/plain".equals(type)) {
	            handleSendText(intent); // Handle text being sent
	        } else if (type.startsWith("image/")) {
	            handleSendImage(intent); // Handle single image being sent
	        }
	    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	            handleSendMultipleImages(intent); // Handle multiple images being sent
	        }
	    } else {
	        // Handle other intents, such as being started from the home screen
	    }
	}

	void handleSendText(Intent intent) {
	    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    if (sharedText != null) {
	        // Update UI to reflect text being shared
	    }
	}

	void handleSendImage(Intent intent) {
	    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	    if (imageUri != null) {
	        // Update UI to reflect image being shared
	    }
	}

	void handleSendMultipleImages(Intent intent) {
	    ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	    if (imageUris != null) {
	        // Update UI to reflect multiple images being shared
	    }
	}

	private void openSelectionCatalogDialog() {

		// ArrayList<String> items = DBDataProvider.
		/*
		 * TODO: read catalog items from dbprovider new
		 * AlertDialog.Builder(this) .setTitle("R.string.dialog_title")
		 * .setItems("R.array.dialog_values", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialoginterface, int i) { //startGame(i); }
		 * }) .show();
		 */
	}

	@Override
	public void setCatalog(Catalog cat) {
		mCurrentCatalog = cat;
		mTvSelectedCatalog.setText(cat.getName());
	}

	@Override
	public void onClick(View v) {
		if (v == mBtnSelectCatalog) {
			SelectCatalogDialog.execute(this);
		} else if(v == mBtnOpenFileDialog ){
			try{
				mOpenFileDialog.show();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
	}
	@Override
	public void onFileSelected(File f) {
		String filePath = f.getAbsolutePath();
		mSelectedFilePathAdapter.add(filePath);
		mSelectedFilePathAdapter.notifyDataSetChanged();
	}
}
