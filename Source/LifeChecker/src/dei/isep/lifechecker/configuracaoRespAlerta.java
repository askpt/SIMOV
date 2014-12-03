package dei.isep.lifechecker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class configuracaoRespAlerta extends Fragment{
	
	private View myFragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myFragment = inflater.inflate(R.layout.configuracao_responsavel_alertas,container, false);
		/*ImageButton flechaEsquerda = (ImageButton) myFragment
				.findViewById(R.id.actionBar_btnFelchaEsquerda);
		flechaEsquerda.setVisibility(View.VISIBLE);
		*/
		return myFragment;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser)
		{
			Log.i("FRAGMENT", "111Visible Alertas");
		}
		else
		{
			Log.i("FRAGMENT", "111NotVisible Alertas");
		}
	}
	
	

	
	
	
}
