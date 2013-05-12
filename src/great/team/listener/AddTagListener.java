package great.team.listener;

import great.team.activities.AddTagActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

// removed SystemUIHider you can revert this link 
// http://stackoverflow.com/questions/15186111/fullscreen-activity-wizard-activity-how-do-i-stop-actionbar-from-showing-when-i
public class AddTagListener implements View.OnClickListener{
	private Context mContext;
	public AddTagListener(Context context)
	{
		mContext = context;
	}

	@Override
	public void onClick(View v) {
		System.out.println("FUCK");
		Intent intent = new Intent(mContext, AddTagActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
}
