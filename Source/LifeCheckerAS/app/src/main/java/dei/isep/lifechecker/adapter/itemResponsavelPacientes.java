package dei.isep.lifechecker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.responsavelMenu;

public class itemResponsavelPacientes extends ArrayAdapter<paciente>{
    Context context;
    paciente rowItem;

    public itemResponsavelPacientes(Context context, int layoutResourceId, ArrayList<paciente> listaPacientes) {
        super(context, layoutResourceId, listaPacientes);
        this.context = context;
    }

    static class PacienteHolder
    {
        TextView paciente;
        TextView email;
        TextView telefone;
        ImageButton removerPaciente;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        PacienteHolder holder = null;
        rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.responsavel_itemtipo_pacientes, null);
            holder = new PacienteHolder();
            holder.paciente = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipopacientes_paciente);
            holder.email = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipopacientes_email);
            holder.telefone = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipopacientes_telefone);
            holder.removerPaciente = (ImageButton) convertView.findViewById(R.id.bt_responsavel_itemtipopacientes_delete);
            convertView.setTag(holder);

            holder.removerPaciente.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    alterarEstadoPaciente();
                }
            });

        }
        else
        {
            holder = (PacienteHolder) convertView.getTag();
        }

        holder.paciente.setText(rowItem.getNomePaciente());
        holder.email.setText(rowItem.getMailPaciente());
        holder.telefone.setText(rowItem.getContactoPaciente());


        return convertView;
    }

    private void alterarEstadoPaciente()
    {
        pacienteHttp paciHttp = new pacienteHttp();
        rowItem.setAtivoPaciente(false);
        paciHttp.updatePaciente(rowItem,resultadoUpdatePacienteEstado);
    }

    interfaceResultadoAsyncPost resultadoUpdatePacienteEstado = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {

            if(codigo == 1) {
                //String idValor = conteudo.replaceAll("[\\r\\n]+", "");
                //int idMarcacao  = Integer.valueOf(idValor);
                //mar.setIdMarcacaoMarc(idMarcacao);
                
                pacienteBDD pacienBDD = new pacienteBDD(getContext());
                pacienBDD.atualizarPaciente(rowItem);

                Intent intent = new Intent(getContext(), responsavelMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
            else
            {
                //BTvalidarMarcacao.setEnabled(true);
            }

        }
    };
}
