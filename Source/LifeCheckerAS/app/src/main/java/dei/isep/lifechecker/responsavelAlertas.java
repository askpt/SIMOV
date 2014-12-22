package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

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
		setContentView(R.layout.responsavel_alertas);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.historicoDeAlertas);
        Context context = getApplicationContext();

		listviewAlertas = (ListView)findViewById(R.id.list_responsavel_alertas);
        PBloadingActionBarHistAlerConsultar = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        listaHistoricoAlertas = new ArrayList<historicoAlertas>();

        preencherHistoricoAlertas();
        /*

        ArrayList<historicoAlertas> listaAlertas = new ArrayList<historicoAlertas>();

        //Dados de Teste
        listaAlertas.add(0, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(1, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(2, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(3, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(4, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));

        itemResponsavelAlertas adapter = new itemResponsavelAlertas(context, R.layout.responsavel_itemtipo_alertas, listaAlertas);
        listviewAlertas.setAdapter(adapter);*/
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
