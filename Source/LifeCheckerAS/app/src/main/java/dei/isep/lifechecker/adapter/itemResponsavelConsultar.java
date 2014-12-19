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

public class itemResponsavelConsultar extends ArrayAdapter<marcacao>{

	Context context;

	public itemResponsavelConsultar(Context context, int layoutResourceId, ArrayList<marcacao> listaMarcacoes) {
		super(context, layoutResourceId, listaMarcacoes);
		this.context = context;
	}

	static class MarcacaoHolder
	{
		TextView paciente;
		TextView marcacao;
		TextView hora;
		TextView local;
		TextView data;
		TextView informacao;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		MarcacaoHolder holder = null;
		marcacao rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.responsavel_itemtipo_listaconsultas, null);
			holder = new MarcacaoHolder();
			holder.paciente = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoconsultas_paciente);
			holder.marcacao = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoconsultas_marcacao);
			holder.hora = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoconsultas_hora);
			holder.data = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoconsultas_data);
			holder.local = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoconsultas_local);
			holder.informacao = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoconsultas_informacao);
			convertView.setTag(holder);
		}
		else
		{
			holder = (MarcacaoHolder) convertView.getTag();
		}

        pacienteBDD paciBDD = new pacienteBDD(getContext());
        String nomePaciente = paciBDD.getNomePacienteById(rowItem.getIdPacienteMarc());


		holder.paciente.setText(String.valueOf(nomePaciente));
		holder.marcacao.setText(rowItem.getTipoMarc());
		holder.hora.setText(rowItem.getHoraMarc());
		holder.local.setText(rowItem.getLocalMarc());
		holder.data.setText(rowItem.getDataMarc());
		holder.informacao.setText("Info");
		
		return convertView;
	}
}
