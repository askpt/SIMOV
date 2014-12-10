package dei.isep.lifechecker.adapter;

import java.util.ArrayList;

import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class itemResponsavelValidar extends ArrayAdapter<marcacao>{

	Context context;
	
	public itemResponsavelValidar(Context context, int layoutResourceId, ArrayList<marcacao> listaMarcacoes) {
		super(context, layoutResourceId, listaMarcacoes);
		this.context = context;
	}

	static class MarcacaoHolder
	{
		TextView paciente;
		TextView local;
		TextView data;
		TextView hora;
		TextView marcacao;
		ImageButton invalid;
		ImageButton valid;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		MarcacaoHolder holder = null;
		marcacao rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.responsavel_itemtipo_validarmarcacoes, null);
			holder = new MarcacaoHolder();
			holder.paciente = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipovalidar_paciente);
			holder.local = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipovalidar_local);
			holder.data = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipovalidar_data);
			holder.hora = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipovalidar_hora);
			holder.marcacao = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipovalidar_marcacao);
			holder.invalid = (ImageButton) convertView.findViewById(R.id.bt_responsavel_itemtipovalidar_not);
			holder.valid = (ImageButton) convertView.findViewById(R.id.bt_responsavel_itemtipovalidar_yes);
			convertView.setTag(holder);
			
			holder.invalid.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			
			holder.valid.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
		}
		else
		{
			holder = (MarcacaoHolder) convertView.getTag();
		}
		
		holder.paciente.setText(rowItem.getIdPacienteMarc());
		holder.local.setText(rowItem.getLocalMarc());
		holder.data.setText(rowItem.getDataMarc());
		holder.hora.setText(rowItem.getHoraMarc());
		holder.marcacao.setText(rowItem.getTipoMarc());
		
		
		
		
		return convertView;
	}
}
