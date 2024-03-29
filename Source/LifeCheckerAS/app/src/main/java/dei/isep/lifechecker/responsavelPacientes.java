package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelPacientes;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelPacientes extends Activity implements OnClickListener {

    ListView listviewPacientes = null;
    Button novoPaciente = null;
    ProgressBar PBloadingActionBar;

    ArrayList<paciente> listaPaciente = new ArrayList<paciente>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_listapacientes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.pacientes);
        Context context = getApplicationContext();

        listviewPacientes = (ListView) findViewById(R.id.list_responsavel_listapacientes_pacientes);
        novoPaciente = (Button) findViewById(R.id.bt_responsavel_listapacientes_novo);

        novoPaciente.setOnClickListener(this);

        novoPaciente.setEnabled(false);

        PBloadingActionBar = (ProgressBar)findViewById(R.id.progressBar_action_bar);
        validarNet();
    }

    private void validarNet()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null) {
            preencherListaPacientes();
        }
        else
        {
            Toast.makeText(this, getResources().getString(R.string.erro_sem_net_info), Toast.LENGTH_LONG).show();
        }
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
                    novoPaciente.setEnabled(true);
                    PBloadingActionBar.setVisibility(View.INVISIBLE);
                    if (codigo == 1 && conteudo.length() > 10) {
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        listaPaciente = paciJson.transformJsonPaciente();
                        pacienteBDD paciBdd = new pacienteBDD(getApplication());
                        paciBdd.deleteConteudoPaciente();

                        for (int i = 0; i <listaPaciente.size(); i++) {
                            paciBdd.inserirPacienteComId(listaPaciente.get(i));
                        }

                        itemResponsavelPacientes adapter = new itemResponsavelPacientes(getApplicationContext(), R.layout.responsavel_itemtipo_pacientes, listaPaciente);


                        listviewPacientes.setAdapter(adapter);

                        listviewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                Intent intent = new Intent(responsavelPacientes.this, responsavelEditarPaciente.class);
                                int idPaciente = listaPaciente.get(position).getIdPaciente();
                                intent.putExtra("idPaciente",idPaciente);

                                startActivity(intent);

                            }
                        });
                    }
                }
            });
        }
    };

}
