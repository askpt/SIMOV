package dei.isep.lifechecker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.model.paciente;

public class itemResponsavelPacientes extends ArrayAdapter<paciente> implements OnClickListener {
    Context context;

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
        paciente rowItem = getItem(position);

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
        }
        else
        {
            holder = (PacienteHolder) convertView.getTag();
        }

        holder.paciente.setText(rowItem.getNomePaciente());
        holder.email.setText(rowItem.getMailPaciente());
        holder.telefone.setText(rowItem.getContactoPaciente());
        holder.removerPaciente.setOnClickListener(this);


        return convertView;
    }

    public void onClick(final View v)
    {

    };
}
