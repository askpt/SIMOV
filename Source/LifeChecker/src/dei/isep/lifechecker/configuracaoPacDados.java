package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.model.paciente;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class configuracaoPacDados extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_paciente_dados,container, false);
		
		return myView;
	}
}
