package dei.isep.lifechecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import dei.isep.lifechecker.model.paciente;

public class responsavelDetalhesMarcacao extends Activity implements OnClickListener {

    Button validarMarcacao = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_detalhesmarcacao);

        validarMarcacao = (Button)findViewById(R.id.bt_responsavel_detalhesmarcacao_validar);
        paciente = (EditText) findViewById(R.id.tb_responsavel_detalhesmarcacao_paciente);
        marcacao = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacao_marcacao);
        hora = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacao_hora);
        data = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacao_data);
        local = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacao_local);

        validarMarcacao.setOnClickListener(this);
    }


    public void onClick(final View v)
    {

    };

}
