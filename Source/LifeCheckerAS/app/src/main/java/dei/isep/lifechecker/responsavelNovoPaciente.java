package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

/**
 * Created by Diogo on 21-12-2014.
 */
public class responsavelNovoPaciente extends Activity {


    EditText edtNomePaciente;
    EditText edtApelidoPaciente;
    EditText edtMailPaciente;
    EditText edtcontactoPaciente;

    TextView txtEstado;

    Button btnInserir;
    Button btnCancelar;

    ProgressBar pbLoadigNovoPaciente;

    String listaErros = "";

    paciente pacient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_novo_paciente);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.pacientes);
        Context context = getApplicationContext();

        edtNomePaciente = (EditText) findViewById(R.id.tb_responsavel_novo_paciente_nome);
        edtApelidoPaciente = (EditText) findViewById(R.id.tb_responsavel_novo_paciente_apelido);
        edtMailPaciente = (EditText) findViewById(R.id.tb_responsavel_novo_paciente_email);
        edtcontactoPaciente = (EditText) findViewById(R.id.tb_responsavel_novo_paciente_telefone);

        btnInserir = (Button) findViewById(R.id.bt_responsavel_novo_paciente_validar);
        btnCancelar = (Button) findViewById(R.id.bt_responsavel_novo_paciente_Cancelar);


        findViewById(R.id.bt_responsavel_novo_paciente_validar)
                .setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_novo_paciente_Cancelar)
                .setOnClickListener(btnCarregado);

        txtEstado = (TextView) findViewById(R.id.text_responsavel_novo_paciente_informacao);

        pbLoadigNovoPaciente = (ProgressBar) findViewById(R.id.progressBar_action_bar);

        pbLoadigNovoPaciente.setVisibility(View.INVISIBLE);
        txtEstado.setVisibility(View.INVISIBLE);

    }

    final View.OnClickListener btnCarregado = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.bt_responsavel_novo_paciente_validar:
                    addPaciente();
                    break;
                case R.id.bt_responsavel_novo_paciente_Cancelar:
                    break;


            }
        }
    };

    private void addPaciente()
    {
        txtEstado.setVisibility(View.VISIBLE);
        txtEstado.setTextColor(Color.BLACK);

        if (validarNome() == 0 && validarApelido() == 0 && validarMail() == 0 && validarContacto() == 0) {

            pbLoadigNovoPaciente.setVisibility(View.VISIBLE);
            btnInserir.setEnabled(false);
            btnCancelar.setEnabled(false);
            validarmailOnlinePaciente(edtMailPaciente.getText().toString());
        } else {
            txtEstado.setTextColor(getResources().getColor(R.color.vermelho));
            txtEstado.setText(listaErros);
        }
    }



    public void validarmailOnlinePaciente(String mail) {
        txtEstado.setText(getResources().getString(
                R.string.est_inserir_paciente));
        pacienteHttp paciHttp = new pacienteHttp();
        paciHttp.verificarMail(mail, htPostResultMailPaciente);
    }

    public void inserirPaciente() {
        txtEstado.setText(getResources().getString(R.string.est_inserir_paciente));


        Intent intentGet = getIntent();
        int idResp = intentGet.getIntExtra("idResponsavel", -1);

        pacient = new paciente(idResp,
                edtNomePaciente.getText().toString(),
                edtApelidoPaciente.getText().toString(),
                edtMailPaciente.getText().toString(),
                edtcontactoPaciente.getText().toString(), true);
        pacienteHttp addPaciente = new pacienteHttp();
        addPaciente.addPaciente(pacient, addPacienteListener);
    }

    interfaceResultadoAsyncPost htPostResultMailPaciente = new interfaceResultadoAsyncPost() {

        @Override
        public void obterResultado(int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    String resultado = conteudo.replaceAll("[\r\n]+", "");
                    boolean resultadobool = Boolean.valueOf(resultado);
                    if (resultadobool == true) {
                        listaErros += getResources().getString(R.string.verificar_mail_indisponivel) + "\n";
                        txtEstado.setText(listaErros);
						/*btnValidarResponsavel.setText(R.string.validar);
						btnValidarResponsavel.setEnabled(true);*/
                        pbLoadigNovoPaciente.setVisibility(View.INVISIBLE);
                        btnInserir.setEnabled(true);
                        btnCancelar.setEnabled(true);
                    } else {
                        inserirPaciente();
                    }

                }
            });

        }
    };

    interfaceResultadoAsyncPost addPacienteListener = new interfaceResultadoAsyncPost() {

        @Override
        public void obterResultado(final int codigo, String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1) {
						/*btnValidarResponsavel.setText(R.string.validar);
						btnValidarResponsavel.setEnabled(true);
						 */
                        pbLoadigNovoPaciente.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(responsavelNovoPaciente.this, responsavelMenu.class);
                        startActivity(intent);
                    } else {
                        btnInserir.setEnabled(true);
                        btnCancelar.setEnabled(true);
                    }

                }
            });

        }
    };


    public int validarNome()
    {
        validarDados validar = new validarDados();
        if(edtNomePaciente.getText().toString() != null
                && validar.nomeApelidoVerificar(edtNomePaciente.getText().toString()) == true )
        {

            return 0;
        }
        else
        {
            listaErros = getResources().getString(R.string.erro_nome) + "\n";
            return 1;
        }
    }

    public int validarApelido()
    {
        validarDados validar = new validarDados();
        if(edtApelidoPaciente.getText().toString() != null
                && validar.nomeApelidoVerificar(edtApelidoPaciente.getText().toString()) == true )
        {

            return 0;
        }
        else
        {
            listaErros = getResources().getString(R.string.erro_apelido) + "\n";
            return 1;
        }
    }

    public int validarMail()
    {
        validarDados validar = new validarDados();
        if(edtMailPaciente.getText().toString() != null
                && validar.mailValidacaoFormato(edtMailPaciente.getText().toString()) == true )
        {

            return 0;
        }
        else
        {
            listaErros = getResources().getString(R.string.verificar_mail_formato_errado) + "\n";
            return 1;
        }
    }

    public int validarContacto()
    {
        validarDados validar = new validarDados();
        if(edtcontactoPaciente.getText().toString() != null
                && validar.contactoVerificar(edtcontactoPaciente.getText().toString()) == true )
        {

            return 0;
        }
        else
        {
            listaErros = getResources().getString(R.string.erro_contacto) + "\n";
            return 1;
        }
    }





}
