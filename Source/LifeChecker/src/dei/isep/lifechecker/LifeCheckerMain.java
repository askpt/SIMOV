package dei.isep.lifechecker;

import dei.isep.lifechecker.database.ResponsavelBDD;
import dei.isep.lifechecker.model.Responsavel;
import android.app.Activity;
import android.os.Bundle;

public class LifeCheckerMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.teste);
		
		ResponsavelBDD respBDD = new ResponsavelBDD(getApplicationContext());
		Responsavel resp = new Responsavel("Diogo", "Leite", "912955395", true, true, 10, 10, "diogo@hotmail.com", "1234", false);
		
		respBDD.open();
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.close();
	}
}