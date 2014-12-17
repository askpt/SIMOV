package dei.isep.lifechecker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelLocalizacaoPaciente extends Activity {

    TextView txtpaciente = null;
    TextView txthora = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_localizarpaciente);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.localizacaoDoPaciente);

        txtpaciente = (TextView) findViewById(R.id.text_responsavel_localizar_paciente);
        txthora = (TextView) findViewById(R.id.text_responsavel_localizar_hora);

        paciente pac = lifeCheckerManager.getInstance().getPac();
        txtpaciente.setText(pac.getNomePaciente() + " " + pac.getApelidoPaciente());
        txthora.setText(pac.getHoraLocalPaciente());
    }


}
