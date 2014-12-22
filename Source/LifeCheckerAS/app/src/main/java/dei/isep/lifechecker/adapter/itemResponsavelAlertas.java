package dei.isep.lifechecker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.database.alertaBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.model.historicoAlertas;

public class itemResponsavelAlertas extends ArrayAdapter<historicoAlertas> {

    Context context;

    public itemResponsavelAlertas(Context context, int layoutResourceId, ArrayList<historicoAlertas> listaAlertas) {
        super(context, layoutResourceId, listaAlertas);
        this.context = context;
    }

    static class AlertaHolder
    {
        TextView paciente;
        TextView alerta;
        TextView rua;
        TextView data;
        TextView hora;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        AlertaHolder holder = null;
        historicoAlertas rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.responsavel_itemtipo_alertas, null);
            holder = new AlertaHolder();
            holder.paciente = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoalertas_paciente);
            holder.alerta = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoalertas_alerta);
            holder.rua = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoalertas_rua);
            holder.data = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoalertas_data);
            holder.hora = (TextView) convertView.findViewById(R.id.text_responsavel_itemtipoalertas_hora);
            convertView.setTag(holder);
        }
        else
        {
            holder = (AlertaHolder) convertView.getTag();
        }
        pacienteBDD paciBDD = new pacienteBDD(getContext());
        String nomePaciente = paciBDD.getNomeApelidoPacienteById(rowItem.getIdPacienteHistAlt());

        alertaBDD alrtBDD = new alertaBDD((getContext()));
        String designacao = alrtBDD.getDesignacaoById(rowItem.getIdAlertaHistAlt());


        holder.paciente.setText(nomePaciente);
        holder.alerta.setText(designacao);
        holder.rua.setText(rowItem.getLocalHistAlt());
        holder.data.setText(rowItem.getDataHistAlt());
        holder.hora.setText(rowItem.getHoraHistAlt());

        return convertView;
    }
}
