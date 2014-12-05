package dei.isep.lifechecker;

import dei.isep.lifechecker.other.genericTextWatcherConfiguracao;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class configuracaoRespPeriodicidade  extends Fragment {

	private EditText txtPeriodicidadeDiurna;
	private EditText txtPeriodicidadeNoturna;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_responsavel_periodicidade,container, false);
		
		txtPeriodicidadeDiurna = (EditText) myView.findViewById(R.id.tb_diurno_responsavel);
		txtPeriodicidadeNoturna = (EditText) myView.findViewById(R.id.tb_noturno_responsavel);
		
		txtPeriodicidadeDiurna.addTextChangedListener(new genericTextWatcherConfiguracao(txtPeriodicidadeDiurna));
		txtPeriodicidadeNoturna.addTextChangedListener(new genericTextWatcherConfiguracao(txtPeriodicidadeNoturna));
		
		return myView;
	}
}
