package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

public class responsavelEditarPaciente extends Activity implements View.OnClickListener{

    Button validarAlteracoes = null;
    EditText nome = null;
    EditText apelido = null;
    EditText email = null;
    EditText telefone = null;
    ProgressBar PBLoadingPacienteDados;
    paciente paciDados;

    int idPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_editarpaciente);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.editarPaciente);

        validarAlteracoes = (Button)findViewById(R.id.bt_responsavel_editarpaciente_validar);
        nome = (EditText)findViewById((R.id.tb_responsavel_editarpaciente_nome));
        apelido = (EditText)findViewById((R.id.tb_responsavel_editarpaciente_apelido));
        email = (EditText)findViewById((R.id.tb_responsavel_editarpaciente_email));
        telefone = (EditText)findViewById((R.id.tb_responsavel_editarpaciente_telefone));


        PBLoadingPacienteDados = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        PBLoadingPacienteDados.setVisibility(View.VISIBLE);
        validarAlteracoes.setEnabled(false);


        validarAlteracoes.setOnClickListener(this);

        preencherInformacoes();
    }

    private void preencherInformacoes()
    {
        Intent intent = getIntent();
        idPaciente = intent.getIntExtra("idPaciente", -1);
        if(idPaciente != -1)
        {
            pacienteHttp paciHttp = new pacienteHttp();
            paciHttp.retornarPacienteById(idPaciente,pacienteSearchListener);

            //paciDados = pacibdd.g
        }
    }

    interfaceResultadoAsyncPost pacienteSearchListener = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        paciDados = paciJson.transformOnePaciente();
                        nome.setText(paciDados.getNomePaciente());
                        apelido.setText(paciDados.getApelidoPaciente());
                        email.setText(paciDados.getMailPaciente());
                        telefone.setText(paciDados.getContactoPaciente());

                        PBLoadingPacienteDados.setVisibility(View.INVISIBLE);
                        validarAlteracoes.setEnabled(true);
                    }
                }
            });
        }
    };



    public void onClick(final View v)
    {
        String nomePaci = nome.getText().toString();
        String apelidoPaci = apelido.getText().toString();
        String mailPaci = email.getText().toString();
        String tlmPaci = telefone.getText().toString();
        validarDados validar = new validarDados();
        if(validar.nomeApelidoVerificar(nomePaci) &&
                validar.nomeApelidoVerificar(apelidoPaci) &&
                validar.mailValidacaoFormato(mailPaci) &&
                validar.contactoVerificar(tlmPaci))
        {
            paciDados.setNomePaciente(nomePaci);
            paciDados.setApelidoPaciente(apelidoPaci);
            paciDados.setMailPaciente(mailPaci);
            paciDados.setContactoPaciente(tlmPaci);
            PBLoadingPacienteDados.setVisibility(View.VISIBLE);
            validarAlteracoes.setEnabled(false);
            pacienteHttp paciHttp = new pacienteHttp();
            paciHttp.updatePaciente(paciDados, pacientesUpdatePaciente);
        }

    };

    interfaceResultadoAsyncPost pacientesUpdatePaciente = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1) {
                        PBLoadingPacienteDados.setVisibility(View.INVISIBLE);

                        pacienteBDD paci = new pacienteBDD(getApplication());
                        paci.atualizarPaciente(paciDados);
                        Intent intent = new Intent(getApplication(), responsavelMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }

                }
            });
        }
    };

}
