package great.team.activities;

import great.team.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddTagActivity extends Activity { 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_file);
/*
		Button button = (Button)findViewById(R.id.btnSelectCatalog);
	    button.setOnClickListener(new View.OnClickListener() {

	          public void onClick(View v) {
	              openSelectionCatalogDialog();
	          }
	      });
*/
	}
	
private void openSelectionCatalogDialog() {
	
	// ArrayList<String> items = DBDataProvider.
	/*TODO: read catalog items from dbprovider
      new AlertDialog.Builder(this)
           .setTitle("R.string.dialog_title")
           .setItems("R.array.dialog_values",
            new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialoginterface,
                     int i) {
                  //startGame(i);
               }
            })
           .show();
     */
   }
}
