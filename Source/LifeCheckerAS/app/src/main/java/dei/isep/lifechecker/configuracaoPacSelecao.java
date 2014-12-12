package dei.isep.lifechecker;

import java.util.ArrayList;
import java.util.Collections;

import dei.isep.lifechecker.adapter.itemConfiguracaoPaciente;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class configuracaoPacSelecao extends Fragment implements OnClickListener{
	
	Button btnNovoPaciente;
	ListView lvPacientes;
	
	ProgressBar pbLoadingList;
	
	ArrayList<paciente> listaPacientes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_paciente_selecao,container, false);;
		
		listaPacientes = lifeCheckerManager.getInstance().getListaPaciente();
		
		btnNovoPaciente = (Button) myView.findViewById(R.id.bt_configuracao_selecaopaciente_novo);
		lvPacientes = (ListView) myView.findViewById(R.id.list_configuracao_selecaopaciente_pacientes);
		pbLoadingList = (ProgressBar) myView.findViewById(R.id.loading_configuracao_selecaopaciente_lista_pacientes);
		pbLoadingList.setVisibility(View.INVISIBLE);
		preencherLista();
		
		btnNovoPaciente.setOnClickListener(this);
		return myView;
	}
	
	public void preencherLista()
	{
		Collections.reverse(listaPacientes);
		itemConfiguracaoPaciente adapter = new itemConfiguracaoPaciente(getActivity().getApplicationContext(), R.layout.configuracao_item_paciente, listaPacientes);
		lvPacientes.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		dialogBoxAddPaciente def = new dialogBoxAddPaciente().newinstance(R.string.novo_paciente);
		def.setOnResultListener(dialogBoxResult);
		def.show(ft, "qweqweqwe");
		/*DialogFragment newFragment = dialogBoxAddPaciente.newinstance(R.string.novo_paciente);
		newFragment.show(ft, "xpto");*/
		// TODO Auto-generated method stub
	}
	
	interfaceResultadoDialogBox dialogBoxResult = new interfaceResultadoDialogBox() {

		@Override
		public void obterResultado(int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					pbLoadingList.setVisibility(View.VISIBLE);
					atualisarPacientes();

				}
			});

		}
	};
	
	public void atualisarPacientes()
	{
		int idResp = lifeCheckerManager.getInstance().getIdResponsavel();
		pacienteHttp paciHttp = new pacienteHttp();
		paciHttp.retornarPacientesIdResposnavel(
				idResp, pacientesResponsavel);
	}
	
	interfaceResultadoAsyncPost pacientesResponsavel = new interfaceResultadoAsyncPost() {
		@Override
		public void obterResultado(final int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(codigo == 1 && conteudo.length() > 10)
					{
						pacienteJson paciJson = new pacienteJson(conteudo);
						listaPacientes = paciJson.transformJsonPaciente();
						preencherLista();
						pbLoadingList.setVisibility(View.INVISIBLE);
					}

				}
			});
		}
	};
}
