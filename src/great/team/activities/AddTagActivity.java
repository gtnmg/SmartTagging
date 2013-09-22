package great.team.activities;

import great.team.R;
import great.team.adapters.SelectedFilePathsAdapter;
import great.team.db.DataProviderFactory;
import great.team.db.IDataProvider;
import great.team.dialogs.OpenFileDialog;
import great.team.dialogs.SelectCatalogDialog;
import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;
import great.team.interfaces.ICatalogSetter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
	TextView mTvItemTerms;
	TextView mTvItemComment;
	Button mBtnSaveItems;
	IDataProvider mDataProvider;

	ListView mLstSelectedFilePaths;
	List<String> mSelectedFilePaths = null;
	SelectedFilePathsAdapter mSelectedFilePathAdapter = null;

	boolean addUriToFileList(String uri){
		if(mSelectedFilePathAdapter.getPosition(uri) == -1){ // check if uri already exists
			mSelectedFilePathAdapter.add(uri);
			return true;
		}
		return false;

	}
	
	void init(){
		mTvSelectedCatalog = (TextView) findViewById(R.id.tvSelectedCatalog);
		mBtnSelectCatalog = (Button) findViewById(R.id.btnCatalogSelect);
		mBtnSelectCatalog.setOnClickListener(this);
		
		mOpenFileDialog = new OpenFileDialog(this, null, null, this);
		mBtnOpenFileDialog = (Button) findViewById(R.id.btnOpenFileDialog);
		mBtnOpenFileDialog.setOnClickListener(this);
		
		mSelectedFilePaths = new ArrayList<String>();
		mSelectedFilePathAdapter = new SelectedFilePathsAdapter(this, mSelectedFilePaths);
		mLstSelectedFilePaths = (ListView) findViewById(R.id.lst_selected_file_paths);
		mLstSelectedFilePaths.setAdapter(mSelectedFilePathAdapter);

		mTvItemComment = (TextView) findViewById(R.id.edt_item_comment);
		mTvItemTerms = (TextView) findViewById(R.id.edt_item_tags);
		mBtnSaveItems = (Button)findViewById(R.id.btnSaveItems);
		mBtnSaveItems.setOnClickListener(this);
		
		mDataProvider = DataProviderFactory.getDataProvider(getApplicationContext());
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
			if(addUriToFileList(imageUri.getPath()))
				mSelectedFilePathAdapter.notifyDataSetChanged();
	    }
	}

	void handleSendMultipleImages(Intent intent) {
	    ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	    if (imageUris != null) {
			boolean bImagesAdded = false;
			for(Uri imageUri : imageUris){
				if(addUriToFileList(imageUri.getPath()))
					bImagesAdded = true;
			}
			if(bImagesAdded)
				mSelectedFilePathAdapter.notifyDataSetChanged();
	    }
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
		} else if(v == mBtnSaveItems){
			String strItemTerms = mTvItemTerms.getText().toString();
			if(strItemTerms == null  || strItemTerms.isEmpty() || mSelectedFilePathAdapter.getCount() == 0 || mCurrentCatalog == null)
				return;
			String itemTerms []= strItemTerms.trim().split("\\s*,\\s*");
			for(int i = 0; i < itemTerms.length; i ++){
				for(int j = 0; j < mSelectedFilePathAdapter.getCount(); j++){
					String strTermName = itemTerms[i];
					String strFilePath = mSelectedFilePathAdapter.getItem(j);
					
					Long term_id = null;
					Term term = mDataProvider.findTermByName(strTermName);
					if(term == null){
						Term newTerm = new Term(null, strTermName, null);
						term_id = mDataProvider.addTerm(newTerm);
					}
					else 
						term_id = term.getId();
					Log.d(this.toString(), "catalog: " + mCurrentCatalog + " tag: " + strTermName + " filePath: " + strFilePath );
					
					Long item_id = null;
					Item item = mDataProvider.findItemByFileURI(strFilePath);
					if(item == null){
						Item newItem = new Item(null, strFilePath, "comment");
						item_id = mDataProvider.addItem(newItem);
					} else 
						item_id = item.getId();

					mDataProvider.addData(item_id, term_id, mCurrentCatalog.getId());
				}
			}
			
		}
		
	}
	@Override
	public void onFileSelected(File f) {
		String filePath = f.getAbsolutePath();
		if(addUriToFileList(filePath))
			mSelectedFilePathAdapter.notifyDataSetChanged();
	}
}
