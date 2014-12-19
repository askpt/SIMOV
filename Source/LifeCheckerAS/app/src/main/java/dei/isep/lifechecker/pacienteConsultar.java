package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemPacienteConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class pacienteConsultar extends Activity {

    ListView listviewMarcacoes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_consultarmarcacoes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.consultarMarcacao);
        Context context = getApplicationContext();

        listviewMarcacoes = (ListView)findViewById(R.id.list_paciente_consultarmarcacoes);

        ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        //Dados de Teste
        listaMarcacoes.add(0, new marcacao(1, 1, "Oftalmologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(1, new marcacao(1, 2, "Psicologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(2, new marcacao(1, 3, "Fisioterapia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(3, new marcacao(1, 2, "Oftalmologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(4, new marcacao(1, 3, "Psicologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));

        itemPacienteConsultar adapter = new itemPacienteConsultar(context, R.layout.paciente_itemtipo_consultarmarcacoes, listaMarcacoes);
        listviewMarcacoes.setAdapter(adapter);

        listviewMarcacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                startActivity(new Intent(pacienteConsultar.this, pacienteAlterarMarcacao.class));

            }
        });
    }

}
