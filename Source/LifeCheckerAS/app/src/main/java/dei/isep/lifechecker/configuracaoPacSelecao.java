package dei.isep.lifechecker;

import java.util.ArrayList;
import java.util.Collections;

import dei.isep.lifechecker.adapter.itemConfiguracaoPaciente;
import dei.isep.lifechecker.alarme.alarmeMicrofone;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.json.responsavelJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.preferenciasAplicacao;
import dei.isep.lifechecker.other.validarDados;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class configuracaoPacSelecao extends Fragment implements OnClickListener{
	//************Microfone
    private static final int INTERVALO_MICRO = 300;
    private alarmeMicrofone micro;

    private double calibragem;

    private int mThreshold;
    private PowerManager.WakeLock mWakeLock;
    private Handler mHandler = new Handler();

    private boolean alarmeLancado = false;


    private Runnable mSleepTask = new Runnable() {
        public void run() {
            //Log.i("Noise", "runnable mSleepTask");
                start();
        }
    };

    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = micro.getAmplitude();
            Log.i("calibra", Double.toString(amp));
            Log.i("calibracao", "calib A");
            if(amp > -10000 && calibragem > amp)
            {
                calibragem = amp;
            }

            //Log.i("Noise", "runnable mPollTask");

            if ((amp > mThreshold)) {
                callForHelp(amp);
                //Log.i("Noise", "==== onCreate ===");
            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, INTERVALO_MICRO);
        }
    };

    //************* FIM microfone
	Button btnNovoPaciente;
	ListView lvPacientes;
	
	ProgressBar pbLoadingList;



    ProgressDialog progressDialogCaliba;
	
	ArrayList<paciente> listaPacientes;
    int positionSelect = -1;
    Thread t;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_paciente_selecao,container, false);;

        mThreshold = 8;
        calibragem = 0;

		listaPacientes = lifeCheckerManager.getInstance().getListaPaciente();
		
		btnNovoPaciente = (Button) myView.findViewById(R.id.bt_configuracao_selecaopaciente_novo);
		lvPacientes = (ListView) myView.findViewById(R.id.list_configuracao_selecaopaciente_pacientes);
		pbLoadingList = (ProgressBar) myView.findViewById(R.id.loading_configuracao_selecaopaciente_lista_pacientes);
		pbLoadingList.setVisibility(View.INVISIBLE);
		preencherLista();
		
		btnNovoPaciente.setOnClickListener(this);

        micro = new alarmeMicrofone();
        PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");

		return myView;
	}

    @Override
    public void onResume() {
        super.onResume();
        if(!alarmeLancado)
        {
            alarmeLancado = true;
            start();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        stop();
    }

    public void preencherLista()
	{
		Collections.reverse(listaPacientes);
		itemConfiguracaoPaciente adapter = new itemConfiguracaoPaciente(getActivity().getApplicationContext(), R.layout.configuracao_item_paciente, listaPacientes);
		lvPacientes.setAdapter(adapter);
        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                positionSelect = position;
                pbLoadingList.setVisibility(View.VISIBLE);
                responsavelHttp respHttp = new responsavelHttp();
                respHttp.getIdResponsavelByIdPaciente(listaPacientes.get(position).getIdResponsavelPaciente(), getPacienteListener);


            }
        });
	}
	
	@Override
	public void onClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		dialogBoxAddPaciente def = new dialogBoxAddPaciente().newinstance(R.string.novo_paciente);
		def.setOnResultListener(dialogBoxResult);
		def.show(ft, "qweqweqwe");
		/*DialogFragment newFragment = dialogBoxAddPaciente.newinstance(R.string.novo_paciente);
		newFragment.show(ft, "xpto");*/
		// TODO Auto-generated method stub
	}
	
	interfaceResultadoDialogBox dialogBoxResult = new interfaceResultadoDialogBox() {

		@Override
		public void obterResultado(int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					pbLoadingList.setVisibility(View.VISIBLE);
					atualisarPacientes();

				}
			});

		}
	};
	
	public void atualisarPacientes()
	{
		int idResp = lifeCheckerManager.getInstance().getIdResponsavel();
		pacienteHttp paciHttp = new pacienteHttp();
		paciHttp.retornarPacientesIdResposnavel(
				idResp, pacientesResponsavel);
	}
	
	interfaceResultadoAsyncPost pacientesResponsavel = new interfaceResultadoAsyncPost() {
		@Override
		public void obterResultado(final int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(codigo == 1 && conteudo.length() > 10)
					{
						pacienteJson paciJson = new pacienteJson(conteudo);
						listaPacientes = paciJson.transformJsonPaciente();
						preencherLista();
						pbLoadingList.setVisibility(View.INVISIBLE);
					}

				}
			});
		}
	};

    interfaceResultadoAsyncPost getPacienteListener = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        pacienteBDD paciBDD = new pacienteBDD(getActivity().getApplicationContext());
                        paciBDD.deleteConteudoPaciente();
                        paciBDD.inserirPacienteComId(listaPacientes.get(positionSelect));

                        lifeCheckerManager.getInstance().setPac(listaPacientes.get(positionSelect));

                        responsavelJson respJson = new responsavelJson(conteudo);
                        responsavelBDD respBDD = new responsavelBDD(getActivity().getApplicationContext());
                        responsavel resp = respJson.transformJsonResponsavel();
                        respBDD.inserirResponsavelComId(resp);


                        lifeCheckerManager.getInstance().setPeriodicidadeDiurna(resp.getPeriodicidadeDiurna());
                        lifeCheckerManager.getInstance().setPeriodicidadeNoturna(resp.getPeriodicidadeNoturna());
                        lifeCheckerManager.getInstance().setEnviarMail(resp.getNotificacaoMail());
                        lifeCheckerManager.getInstance().setEnviarSMS(resp.getNotificacaoSMS());

                        configurarMicrofone();
                        //**********************
                    }

                }
            });
        }
    };

    private void configurarMicrofone()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getResources().getString(R.string.micro_calmo_info));
        alertDialog.setMessage(getResources().getString(R.string.micro_calmo_lugar));
        alertDialog.setButton("OK",  new DialogInterface.OnClickListener()
        {
            public  void onClick(DialogInterface dialog, int which)
            {
                start();
                lancarCalibracao();
            }
        });
        alertDialog.show();


    }

    private void lancarCalibracao()
    {
        progressDialogCaliba = new ProgressDialog(getActivity());
        progressDialogCaliba.setMax(60);
        progressDialogCaliba.setMessage(getResources().getString(R.string.esperar));
        progressDialogCaliba.setTitle(getResources().getString(R.string.micro_calibrar));
        progressDialogCaliba.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialogCaliba.show();
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (progressDialogCaliba.getProgress() <= progressDialogCaliba.getMax())
                    {
                        Thread.sleep(1000);
                        handle.sendMessage(handle.obtainMessage());
                        if(progressDialogCaliba.getProgress() == progressDialogCaliba.getMax())
                        {

                            Log.i("Valor calibr","sssssssssssss");
                            progressDialogCaliba.dismiss();
                            passarMenuPaciente();
                            finalize();
                            //onStop();
                        }
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        t.start();


    }

    private void passarMenuPaciente()
    {
        stop();
        t.interrupt();
        //t.stop();
        preferenciasAplicacao prefApp = new preferenciasAplicacao(getActivity().getApplicationContext());
        prefApp.setTipoUser(2);
        prefApp.setCalibracao(calibragem);
        double teste = prefApp.getCalibracao();

        //********************



        Log.i("Calibracao", Double.toString(calibragem));
        Intent intent = new Intent(getActivity(), pacienteMenu.class);
        intent.putExtra("idPaciente", listaPacientes.get(positionSelect).getIdPaciente());
        startActivity(intent);
        t.stop();
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialogCaliba.incrementProgressBy(1);
        }
    };

    //MICROFONE METODOS
    private void start() {
        Log.i("Noise", "==== start ===");
        micro.start();
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        //Noise monitoring start
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandler.postDelayed(mPollTask, INTERVALO_MICRO);
    }

    private void stop() {
        Log.i("Noise", "==== Stop Noise Monitoring===");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        micro.stop();
        alarmeLancado = false;

    }

    private void callForHelp(double signalEMA) {

        Log.d("SONUND", String.valueOf(signalEMA));
        //tv_noice.setText(signalEMA+"dB");
    }

    //FIM MICROFONE METODOS

}
