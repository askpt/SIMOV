package dei.isep.lifechecker.adapter;

import java.util.ArrayList;

import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class itemResponsavelHoje extends ArrayAdapter<marcacao>{

	Context context;
	
	public itemResponsavelHoje(Context context, int layoutResourceId, ArrayList<marcacao> listaMarcacoes) {
		super(context, layoutResourceId, listaMarcacoes);
		this.context = context;
	}

	static class MarcacaoHolder
	{
		TextView paciente;
		TextView marcacao;
		TextView hora;
		TextView local;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		MarcacaoHolder holder = null;
		marcacao rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.responsavel_itemtipo_hoje, null);
			holder = new MarcacaoHolder();
			holder.paciente = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipohoje_paciente_value);
			holder.marcacao = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipohoje_marcacao_value);
			holder.hora = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipohoje_hora_value);
			holder.local = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipohoje_local_value);
			convertView.setTag(holder);
		}
		else
		{
			holder = (MarcacaoHolder) convertView.getTag();
		}

        pacienteBDD paciBDD = new pacienteBDD(getContext());
        String nomePaciente = paciBDD.getNomePacienteById(rowItem.getIdPacienteMarc());
		
		holder.paciente.setText(nomePaciente);
		holder.marcacao.setText(rowItem.getTipoMarc());
		holder.hora.setText(rowItem.getHoraMarc());
		holder.local.setText(rowItem.getLocalMarc());
		
		return convertView;
	}
}
