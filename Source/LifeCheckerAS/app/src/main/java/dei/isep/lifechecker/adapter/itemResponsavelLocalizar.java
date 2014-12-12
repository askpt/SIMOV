package dei.isep.lifechecker.adapter;

import java.util.ArrayList;

import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class itemResponsavelLocalizar extends ArrayAdapter<paciente>{

	Context context;
	
	public itemResponsavelLocalizar(Context context, int layoutResourceId, ArrayList<paciente> listaPacientes) {
		super(context, layoutResourceId, listaPacientes);
		this.context = context;
	}

	static class PacienteHolder
	{
		TextView paciente;
		TextView rua;
		TextView cidade;
		TextView hora;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		PacienteHolder holder = null;
		paciente rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.responsavel_itemtipo_localizacaopacientes, null);
			holder = new PacienteHolder();
			holder.paciente = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipolocalizacao_paciente);
			holder.rua = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipolocalizacao_rua);
			holder.cidade = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipolocalizacao_cidade);
			holder.hora = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipolocalizacao_hora);
			convertView.setTag(holder);
		}
		else
		{
			holder = (PacienteHolder) convertView.getTag();
		}
		
		holder.paciente.setText(rowItem.getNomePaciente());
		holder.rua.setText(rowItem.getNomeLocalPaciente());
		holder.cidade.setText(rowItem.getNomeLocalPaciente());
		holder.hora.setText(rowItem.getHoraLocalPaciente());
		
		return convertView;
	}
}
