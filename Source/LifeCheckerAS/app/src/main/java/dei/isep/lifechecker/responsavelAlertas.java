package dei.isep.lifechecker;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import dei.isep.lifechecker.adapter.itemResponsavelAlertas;
import dei.isep.lifechecker.database.historicoAlertasBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.historicoAlertasHttp;
import dei.isep.lifechecker.json.historicoAlertaJson;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelAlertas extends Activity {
	
	ListView listviewAlertas = null;
    ArrayList<historicoAlertas> listaHistoricoAlertas;
    ProgressBar PBloadingActionBarHistAlerConsultar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification.cancelAll();


		setContentView(R.layout.responsavel_alertas);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.historicoDeAlertas);
        Context context = getApplicationContext();

		listviewAlertas = (ListView)findViewById(R.id.list_responsavel_alertas);
        PBloadingActionBarHistAlerConsultar = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        listaHistoricoAlertas = new ArrayList<historicoAlertas>();

        validarNet();

	}

    private void validarNet()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null) {
            preencherHistoricoAlertas();
        }
        else
        {
            Toast.makeText(this, getResources().getString(R.string.erro_sem_net_info), Toast.LENGTH_LONG).show();
        }
    }

    private void preencherHistoricoAlertas()
    {
        PBloadingActionBarHistAlerConsultar.setVisibility(View.VISIBLE);

        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = respBDD.getIdResponsavel();

        historicoAlertasHttp histAlHttp = new historicoAlertasHttp();
        histAlHttp.retornarHistoricoAlertasIdResposnavel(idResp,historicoAlertasGetAllValidas);
    }

    private void preencherListaHistoricoAlertas()
    {
        itemResponsavelAlertas adapter = new itemResponsavelAlertas(getApplicationContext(), R.layout.responsavel_itemtipo_alertas, listaHistoricoAlertas);
        listviewAlertas.setAdapter(adapter);
        PBloadingActionBarHistAlerConsultar.setVisibility(View.INVISIBLE);
    }

    interfaceResultadoAsyncPost historicoAlertasGetAllValidas = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        historicoAlertaJson histoAlerJson = new historicoAlertaJson(conteudo);
                        listaHistoricoAlertas = histoAlerJson.transformJsonHistoricoAlerta();
                        Collections.reverse(listaHistoricoAlertas);
                        if(listaHistoricoAlertas.size() > 50)
                        {
                            int qtdApagar = listaHistoricoAlertas.size() - 50;
                            for (int i = 0; i < qtdApagar; i++) {
                                listaHistoricoAlertas.remove(listaHistoricoAlertas.size()-1);
                            }
                        }
                        preencherListaHistoricoAlertas();
                    }
                    else
                    {
                        PBloadingActionBarHistAlerConsultar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    };

}
