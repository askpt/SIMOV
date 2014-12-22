package dei.isep.lifechecker.adapter;

import java.util.ArrayList;

import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.R;
import dei.isep.lifechecker.responsavelMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class itemResponsavelValidar extends ArrayAdapter<marcacao>{

	Context context;
    marcacao mar;
	
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
	
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		MarcacaoHolder holder = null;
		final marcacao rowItem = getItem(position);
        mar = rowItem;
		
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

                    alterarEstadoMarcacao(3);
				}
			});
			
			holder.valid.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
                    alterarEstadoMarcacao(1);
				}
			});
		}
		else
		{
			holder = (MarcacaoHolder) convertView.getTag();
		}

        pacienteBDD paciBDD = new pacienteBDD(getContext());
        String nomePaciente = paciBDD.getNomeApelidoPrimeiraLetPacienteById(rowItem.getIdPacienteMarc());


        holder.paciente.setText(nomePaciente);
		holder.local.setText(rowItem.getLocalMarc());
		holder.data.setText(rowItem.getDataMarc());
		holder.hora.setText(rowItem.getHoraMarc());
		holder.marcacao.setText(rowItem.getTipoMarc());

		return convertView;
	}

    private void alterarEstadoMarcacao(int idEstado)
    {
        mar.setIdEstadoMarc(idEstado);
        marcacaoHttp marHttp = new marcacaoHttp();
        marHttp.updateMarcacao(mar, resultadoUpdateMarcacao);
    }

    interfaceResultadoAsyncPost resultadoUpdateMarcacao = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {

                    if(codigo == 1) {
                        marcacaoBDD marcBDD = new marcacaoBDD(getContext());
                        marcBDD.atualizarMarcacao(mar);


                        Intent intent = new Intent(getContext(), responsavelMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                    else
                    {
                        /*Intent intent = new Intent(getContext(), responsavelMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);*/
                        //BTvalidarMarcacao.setEnabled(true);
                    }

        }
    };
}
