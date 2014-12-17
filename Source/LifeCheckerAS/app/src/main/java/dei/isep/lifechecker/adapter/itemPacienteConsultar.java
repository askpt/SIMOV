package dei.isep.lifechecker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.model.marcacao;

public class itemPacienteConsultar extends ArrayAdapter<marcacao> {

    Context context;

    public itemPacienteConsultar(Context context, int layoutResourceId, ArrayList<marcacao> listaMarcacoes) {
        super(context, layoutResourceId, listaMarcacoes);
        this.context = context;
    }

    static class MarcacaoHolder
    {

        TextView marcacao;
        TextView hora;
        TextView local;
        TextView data;
        ImageView estado;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        MarcacaoHolder holder = null;
        marcacao rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.paciente_itemtipo_consultarmarcacoes, null);
            holder = new MarcacaoHolder();
            holder.marcacao = (TextView) convertView.findViewById(R.id.text_paciente_itemtipoconsultar_marcacao);
            holder.hora = (TextView) convertView.findViewById(R.id.text_paciente_itemtipoconsultar_hora);
            holder.data = (TextView) convertView.findViewById(R.id.text_paciente_itemtipoconsultar_data);
            holder.local = (TextView) convertView.findViewById(R.id.text_paciente_itemtipoconsultar_local);
            holder.estado = (ImageView) convertView.findViewById(R.id.image_paciente_itemtipoconsultar_estado);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MarcacaoHolder) convertView.getTag();
        }

        holder.marcacao.setText(rowItem.getTipoMarc());
        holder.hora.setText(rowItem.getHoraMarc());
        holder.local.setText(rowItem.getLocalMarc());
        holder.data.setText(rowItem.getDataMarc());
        //Falta programar a imagem do estado

        return convertView;
    }

}
