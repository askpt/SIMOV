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
import dei.isep.lifechecker.model.paciente;

/**
 * Created by Diogo on 16-12-2014.
 */
public class spinnerPaciente extends ArrayAdapter<paciente> {

    Context contex;

    public spinnerPaciente(Context context, int layoutResourceId, ArrayList<paciente> listaPacientes)
    {
        super(context, layoutResourceId, listaPacientes);
        this.contex = contex;
    }

    static class PacienteHolderSpinner
    {
        ImageView imagemPaciente;
        TextView nomePaciente;
        TextView apelidoPaciente;
    }

   public View getView(int position, View convertView, ViewGroup parent)
   {
       PacienteHolderSpinner holder = null;
       paciente rowItem = getItem(position);

       LayoutInflater mInflater = (LayoutInflater) contex.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
       if(convertView == null)
       {
           convertView = mInflater.inflate(R.layout.spinner_paciente, null);
           holder = new PacienteHolderSpinner();
           holder.imagemPaciente = (ImageView) convertView.findViewById(R.id.img_foto_paciente);
           holder.nomePaciente = (TextView) convertView.findViewById(R.id.txt_spiner_nome_paciente);
           holder.apelidoPaciente = (TextView) convertView.findViewById(R.id.txt_spiner_apelido_paciente);
       }
       else
       {
           holder = (PacienteHolderSpinner) convertView.getTag();
       }

       holder.imagemPaciente.setImageResource(R.drawable.human);
       holder.nomePaciente.setText(rowItem.getNomePaciente());
       holder.apelidoPaciente.setText(rowItem.getApelidoPaciente());

       return convertView;
   }

}
