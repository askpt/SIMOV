package dei.isep.lifechecker.adapter;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.model.paciente;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class itemConfiguracaoPaciente extends ArrayAdapter<paciente>{
	
	Context context;
	
	public itemConfiguracaoPaciente(Context context, int layoutResourceId, ArrayList<paciente> listaPacientes)
	{
		super(context, layoutResourceId, listaPacientes);
		this.context = context;
	}
	
	static class PacienteHolderConfiguracao
	{
		TextView nomeApelidoPaciente;
		TextView mailPaciente;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		PacienteHolderConfiguracao holder = null;
		paciente rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.configuracao_item_paciente, null);
			holder = new PacienteHolderConfiguracao();
			holder.nomeApelidoPaciente = (TextView) convertView.findViewById(R.id.text_configuracao_itempaciente_nome_apelido);
			holder.mailPaciente = (TextView) convertView.findViewById(R.id.text_configuracao_itempaciente_mail);
			convertView.setTag(holder);
		}
		else
		{
			holder = (PacienteHolderConfiguracao) convertView.getTag();
		}
		
		holder.nomeApelidoPaciente.setText(rowItem.getNomePaciente() + " " + rowItem.getApelidoPaciente());
		holder.mailPaciente.setText(rowItem.getMailPaciente());
		
		return convertView;
	}

}
