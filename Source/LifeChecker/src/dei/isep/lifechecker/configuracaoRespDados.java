package dei.isep.lifechecker;

import dei.isep.lifechecker.other.genericTextWatcherConfiguracao;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class configuracaoRespDados extends Fragment {
	
	public EditText txtNomeResp;
	public EditText txtApelidoResp;
	public EditText txtTelefoneResp;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_responsavel_dados,container, false);
		
		txtNomeResp = (EditText) myView.findViewById(R.id.tb_nome_responsavel);
		txtApelidoResp = (EditText) myView.findViewById(R.id.tb_apelido_responsavel);
		txtTelefoneResp = (EditText) myView.findViewById(R.id.tb_telefone_responsavel);
		
		txtNomeResp.addTextChangedListener(new genericTextWatcherConfiguracao(txtNomeResp));
		txtApelidoResp.addTextChangedListener(new genericTextWatcherConfiguracao(txtApelidoResp));
		txtTelefoneResp.addTextChangedListener(new genericTextWatcherConfiguracao(txtTelefoneResp));
		return myView;
	}
}
