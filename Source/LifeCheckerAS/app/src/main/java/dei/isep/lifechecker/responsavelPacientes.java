package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelPacientes;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelPacientes extends Activity implements OnClickListener {

    ListView listviewPacientes = null;
    Button novoPaciente = null;
    ProgressBar PBloadingActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_listapacientes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.pacientes);
        Context context = getApplicationContext();

        listviewPacientes = (ListView) findViewById(R.id.list_responsavel_listapacientes_pacientes);
        novoPaciente = (Button) findViewById(R.id.bt_responsavel_listapacientes_novo);

        novoPaciente.setOnClickListener(this);

        PBloadingActionBar = (ProgressBar)findViewById(R.id.progressBar_action_bar);
        preencherListaPacientes();
    }

    private void preencherListaPacientes() {

        PBloadingActionBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = intent.getIntExtra("idResponsavel", -1);

        pacienteHttp paciHttp = new pacienteHttp();
        paciHttp.retornarPacientesIdResposnavel(idResp, pacientesGetAllMeus);


    }


    public void onClick(final View v) {

        Intent intentGet = getIntent();
        intentGet.getIntExtra("idResponsavel", -1);
        Intent intent = new Intent(responsavelPacientes.this,responsavelNovoPaciente.class);
        intent.putExtra("idResponsavel", intentGet.getIntExtra("idResponsavel", -1));
        startActivity(intent);
    }

    ;

    interfaceResultadoAsyncPost pacientesGetAllMeus = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        ArrayList<paciente> listaPaciente = new ArrayList<paciente>();
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        listaPaciente = paciJson.transformJsonPaciente();

                        itemResponsavelPacientes adapter = new itemResponsavelPacientes(getApplicationContext(), R.layout.responsavel_itemtipo_pacientes, listaPaciente);
                        PBloadingActionBar.setVisibility(View.INVISIBLE);

                        listviewPacientes.setAdapter(adapter);

                        listviewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                startActivity(new Intent(responsavelPacientes.this, responsavelEditarPaciente.class));

                            }
                        });
                    }
                }
            });
        }
    };
}
