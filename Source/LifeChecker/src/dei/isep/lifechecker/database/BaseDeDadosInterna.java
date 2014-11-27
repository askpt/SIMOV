package dei.isep.lifechecker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class baseDeDadosInterna extends SQLiteOpenHelper{
	
	private static final int VERSION_BDD = 2;
	private static final String nomeDB = "lfDataBase.db";
	
	//Objetos para Tabelas
	responsavelBDD responsavel = new responsavelBDD();
	pacienteBDD paciente = new pacienteBDD();
	alertaBDD alerta = new alertaBDD();
	historicoAlertasBDD historicoAlertas = new historicoAlertasBDD();
	
	
	public baseDeDadosInterna(Context context) {
		super(context, nomeDB, null, VERSION_BDD);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(responsavel.getCreateTable());
		db.execSQL(paciente.getCreateTable());
		db.execSQL(alerta.getCreateTableAlert());
		db.execSQL(historicoAlertas.getCreateTableHitoricoalerta());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(historicoAlertas.getDropTableHistoricoalertas());
		db.execSQL(alerta.getDropTableAlerta());
		db.execSQL(paciente.getDropTable());
		db.execSQL(responsavel.getDropTable());
		// TODO Auto-generated method stub
		
		onCreate(db);
	}
	

}
