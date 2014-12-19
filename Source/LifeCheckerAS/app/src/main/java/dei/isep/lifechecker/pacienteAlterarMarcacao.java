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

public class pacienteAlterarMarcacao extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button validarAlteracoes = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;
    private FragmentManager supportFragmentManager;

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

        validarAlteracoes.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v)
    {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    };

    public void clickTime (final View v)
    {

        TimePickerDialog tp = new TimePickerDialog(this, this, 1, 1, true );
        tp.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        data.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i2) {
        hora.setText(i+":"+i2);
    }
}

