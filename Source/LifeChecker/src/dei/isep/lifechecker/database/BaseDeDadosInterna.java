package dei.isep.lifechecker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDadosInterna extends SQLiteOpenHelper{
	
	private static final int VERSION_BDD = 3;
	private static final String nomeDB = "lfDataBase.db";
	
	//Objetos para Tabelas
	ResponsavelBDD responsavel = new ResponsavelBDD();
	PacienteBDD paciente = new PacienteBDD();
	
	
	public BaseDeDadosInterna(Context context) {
		super(context, nomeDB, null, VERSION_BDD);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(responsavel.getCreateTable());
		db.execSQL(paciente.getCreateTable());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(paciente.getDropTable());
		db.execSQL(responsavel.getDropTable());
		// TODO Auto-generated method stub
		
		onCreate(db);
	}
	

}
