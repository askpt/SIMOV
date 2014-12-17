package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemPacienteConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.model.marcacao;

public class pacienteConsultar extends Activity {

    ListView listviewMarcacoes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_consultarmarcacoes);
        Context context = getApplicationContext();

        listviewMarcacoes = (ListView)findViewById(R.id.list_paciente_consultarmarcacoes);

        ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        itemPacienteConsultar adapter = new itemPacienteConsultar(context, R.layout.paciente_itemtipo_consultarmarcacoes, listaMarcacoes);
        listviewMarcacoes.setAdapter(adapter);
    }

}
