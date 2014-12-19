package dei.isep.lifechecker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.model.paciente;

/**
 * Created by Diogo on 16-12-2014.
 */


public class spinnerPacienteAdapter extends BaseAdapter
{
    Context c;
    ArrayList<paciente> dados;

    public spinnerPacienteAdapter(Context context, ArrayList<paciente> dados)
    {
        super();
        this.c = context;
        this.dados = dados;
    }

    @Override
    public int getCount()
    {
        return dados.size();
    }

    @Override
    public Object getItem(int position)
    {
        return dados.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return dados.get(position).getIdPaciente();
    }

    @Override
    public View getView(int position, View converterView, ViewGroup parent)
    {
        paciente paciAtual = dados.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_paciente, parent, false);
        ImageView imagemPaciente = (ImageView) row.findViewById(R.id.img_spinner_foto_paciente);
        TextView nomePaciente = (TextView) row.findViewById(R.id.txt_spiner_nome_paciente);
        TextView apelidoPaciente = (TextView) row.findViewById(R.id.txt_spiner_apelido_paciente);

        imagemPaciente.setImageResource(R.drawable.human);
        nomePaciente.setText(paciAtual.getNomePaciente());
        apelidoPaciente.setText(paciAtual.getApelidoPaciente());
        return row;
    }
}

/*
public class spinnerPacienteAdapter extends ArrayAdapter<paciente> {

    Context context;

    public spinnerPacienteAdapter(Context context, int layoutResourceId, ArrayList<paciente> listaPacientes)
    {
        super(context, layoutResourceId, listaPacientes);
        this.context = context;
    }

    static class PacienteHolderSpinner
    {
        ImageView imagemPaciente;
        TextView nomePaciente;
        TextView apelidoPaciente;
    }

    @Override
   public View getView(int position, View convertView, ViewGroup parent)
   {
       PacienteHolderSpinner holder = null;
       paciente rowItem = getItem(position);

       LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
       if(convertView == null)
       {
           convertView = mInflater.inflate(R.layout.spinner_paciente, null);
           holder = new PacienteHolderSpinner();
           holder.imagemPaciente = (ImageView) convertView.findViewById(R.id.img_spinner_foto_paciente);
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
*/