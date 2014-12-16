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

public class pacienteMenu extends Activity{

    Intent intent = null;

    ListView listviewProximas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_menu);
        Context context = getApplicationContext();

        listviewProximas = (ListView)findViewById(R.id.list_paciente_menu_proximasconsultas);

        findViewById(R.id.bt_paciente_menu_agendarmarcacao).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_paciente_menu_consultar).setOnClickListener(btnCarregado);

        ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        itemPacienteProximas adapter = new itemPacienteProximas(context, R.layout.paciente_itemtipo_proximasconsultas, listaMarcacoes);
        listviewProximas.setAdapter(adapter);
    }

    final View.OnClickListener btnCarregado = new View.OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_responsavel_menu_agendar:
                    intent = new Intent(pacienteMenu.this, pacienteAgendar.class);
                    break;
                case R.id.bt_responsavel_menu_consultar:
                    intent = new Intent(pacienteMenu.this, pacienteConsultar.class);
                    break;
            }

            startActivity(intent);

        }
    };
}
