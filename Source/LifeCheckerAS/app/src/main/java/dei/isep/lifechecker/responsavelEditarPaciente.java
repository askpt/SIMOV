package dei.isep.lifechecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelEditarPaciente extends Activity implements View.OnClickListener{

    Button validarAlteracoes = null;
    EditText nome = null;
    EditText apelido = null;
    EditText email = null;
    EditText telefone = null;

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

        validarAlteracoes.setOnClickListener(this);
    }


    public void onClick(final View v)
    {

    };
}
