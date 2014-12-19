package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelDetalhesMarcacaoValidar extends Activity{

    ImageButton validarYes = null;
    ImageButton validarNo = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_detalhesmarcacaovalidar);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.validarMarcacao);

        validarYes = (ImageButton)findViewById(R.id.bt_responsavel_detalhesmarcacaovalidar_yes);
        validarNo = (ImageButton)findViewById(R.id.bt_responsavel_detalhesmarcacaovalidar_not);
        paciente = (EditText) findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_paciente);
        marcacao = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_marcacao);
        hora = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_hora);
        data = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_data);
        local = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_local);

        validarYes.setOnClickListener(btnCarregado);
        validarNo.setOnClickListener(btnCarregado);
    }


    final View.OnClickListener btnCarregado = new View.OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_responsavel_detalhesmarcacaovalidar_yes: //Yes
                    break;
                case R.id.bt_responsavel_detalhesmarcacaovalidar_not: //No
                    break;
            }
        }
    };


}
