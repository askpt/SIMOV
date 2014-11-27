package dei.isep.lifechecker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ConfiguracaoRespAlerta extends Fragment{
	
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
			
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("FRAGMENT", "111onResume Alertas");
		LayoutInflater mInflater = LayoutInflater.from(getActivity().getApplicationContext());
		View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		ImageButton flechaEsquerda = (ImageButton) mCustomView
				.findViewById(R.id.actionBar_btnFelchaEsquerda);
		flechaEsquerda.setVisibility(View.VISIBLE);
	}
	@Override
	public void onStart() {
		super.onStart();
		Log.i("FRAGMENT", "111onStart Alertas");
		LayoutInflater mInflater = LayoutInflater.from(getActivity().getApplicationContext());
		View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		ImageButton flechaEsquerda = (ImageButton) mCustomView
				.findViewById(R.id.actionBar_btnFelchaEsquerda);
		flechaEsquerda.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.i("FRAGMENT", "111onPause Alertas");
	}
	
	

	
	
	
}
