package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemPacienteProximas;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class pacienteMenu extends Activity{

    Intent intent = null;
    ListView listviewProximas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_menu);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.lifechecker);
        Context context = getApplicationContext();

        listviewProximas = (ListView)findViewById(R.id.list_paciente_menu_proximasconsultas);

        findViewById(R.id.bt_paciente_menu_agendarmarcacao).setOnClickListener(btnClick);
        findViewById(R.id.bt_paciente_menu_consultar).setOnClickListener(btnClick);

        ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        //Dados de Teste
        listaMarcacoes.add(0, new marcacao(1, 1, "Oftalmologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(1, new marcacao(1, 2, "Psicologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(2, new marcacao(1, 3, "Fisioterapia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(3, new marcacao(1, 1, "Oftalmologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(4, new marcacao(1, 2, "Psicologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));

        itemPacienteProximas adapter = new itemPacienteProximas(context, R.layout.paciente_itemtipo_proximasconsultas, listaMarcacoes);
        listviewProximas.setAdapter(adapter);
    }

    final View.OnClickListener btnClick = new View.OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_paciente_menu_agendarmarcacao:
                    intent = new Intent(pacienteMenu.this, pacienteAgendar.class);
                    break;
                case R.id.bt_paciente_menu_consultar:
                    intent = new Intent(pacienteMenu.this, pacienteConsultar.class);
                    break;
            }
            startActivity(intent);

        }
    };
}
