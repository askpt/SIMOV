package dei.isep.lifechecker;

import android.app.ActionBar;
import android.app.Activity;
import android.widget.TextView;

public class abstractActionBarPersonalisado extends Activity{

	abstractActionBarPersonalisado(){};
	
	public void createPersoActionBar(Activity act)
	{
		ActionBar actionBar = act.getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		TextView textView = (TextView) actionBar.getCustomView().findViewById(R.id.actionBar_Titulo);
		textView.setText(getResources().getString(R.string.configuracao));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}
	
	

}
