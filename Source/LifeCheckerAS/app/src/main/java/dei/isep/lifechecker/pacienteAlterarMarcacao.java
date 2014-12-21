package dei.isep.lifechecker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

public class pacienteAlterarMarcacao extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button validarAlteracoes = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;
    private FragmentManager supportFragmentManager;
    private validarDados vd = new validarDados();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_alterarmarcacao);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.detalhesMarcacao);

        validarAlteracoes = (Button)findViewById(R.id.bt_paciente_alterarmarcacao_validar);
        paciente = (EditText) findViewById(R.id.tb_paciente_alterarmarcacao_paciente);
        marcacao = (EditText)findViewById(R.id.tb_paciente_alterarmarcacao_marcacao);
        hora = (EditText)findViewById(R.id.tb_paciente_alterarmarcacao_hora);
        data = (EditText)findViewById(R.id.tb_paciente_alterarmarcacao_data);
        local = (EditText)findViewById(R.id.tb_paciente_alterarmarcacao_local);

//        validarAlteracoes.setOnClickListener(this);
    }

    public void clickDate(final View v)
    {
        int[] hoje = vd.getDataHoje();
        DatePickerDialog dialog = new DatePickerDialog(this, this, hoje[0], hoje[1], hoje[2]);
        dialog.show();
    };

    public void clickTime (final View v)
    {
        TimePickerDialog tp = new TimePickerDialog(this, this, 0, 0, true );
        tp.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        data.setText(vd.formatDate(year, monthOfYear, dayOfMonth));
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hh, int mm) {
        hora.setText(vd.formatTime(hh, mm));
    }
}

