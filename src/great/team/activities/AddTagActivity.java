package great.team.activities;

import great.team.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class AddTagActivity extends Activity { 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_file);
		
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
/*
		Button button = (Button)findViewById(R.id.btnSelectCatalog);
	    button.setOnClickListener(new View.OnClickListener() {

	          public void onClick(View v) {
	              openSelectionCatalogDialog();
	          }
	      });
*/
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
}
