package dei.isep.lifechecker;

import dei.isep.lifechecker.other.lifeCheckerManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class configuracaoRespPaciente extends Fragment implements OnClickListener{

	Button btnValidarResponsavel;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_responsavel_paciente, container, false);
		btnValidarResponsavel = (Button) myView.findViewById(R.id.bt_validar_paciente_responsavel);
		btnValidarResponsavel.setOnClickListener(this);

		
		return myView;
	}

	@Override
	public void onClick(View v) {
		String tttt = lifeCheckerManager.getInstance().getMailResponsavel();
		Toast.makeText(getActivity().getApplicationContext(), "qweqweqwe " + tttt, Toast.LENGTH_LONG).show();
		
		// TODO Auto-generated method stub
		
	}
}