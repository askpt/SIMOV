package dei.isep.lifechecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;

import static android.view.View.OnClickListener;

public class pacienteAgendar extends Activity implements OnClickListener{

    Button agendarMarcacao = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_agendarmarcacao);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.agendarMarcacao);

        agendarMarcacao = (Button)findViewById(R.id.bt_paciente_agendarmarcacao_agendar);
        paciente = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_paciente);
        marcacao = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_marcacao);
        hora = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_hora);
        data = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_data);
        local = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_local);

        agendarMarcacao.setOnClickListener(this);
    }


    public void onClick(final View v)
    {

    };
}
