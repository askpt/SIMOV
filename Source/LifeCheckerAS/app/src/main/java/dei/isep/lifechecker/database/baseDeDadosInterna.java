package dei.isep.lifechecker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class baseDeDadosInterna extends SQLiteOpenHelper{
	
	private static final int VERSION_BDD = 4;
	private static final String nomeDB = "lfDataBase.db";
	
	//Objetos para Tabelas
	responsavelBDD responsavel = new responsavelBDD();
	pacienteBDD paciente = new pacienteBDD();
	alertaBDD alerta = new alertaBDD();
	historicoAlertasBDD historicoAlertas = new historicoAlertasBDD();
	estadoMarcacaoBDD estadoMarcacao = new estadoMarcacaoBDD();
	marcacaoBDD marcacao = new marcacaoBDD();
	
	
	public baseDeDadosInterna(Context context) {
		super(context, nomeDB, null, VERSION_BDD);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(estadoMarcacao.getCreateTableEstamarc());
		db.execSQL(responsavel.getCreateTable());
		db.execSQL(paciente.getCreateTable());
		db.execSQL(alerta.getCreateTableAlert());
		db.execSQL(historicoAlertas.getCreateTableHitoricoalerta());
		db.execSQL(marcacao.getCreateTableMarcacao());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(marcacao.getDropTableMarcacao());
		db.execSQL(historicoAlertas.getDropTableHistoricoalertas());
		db.execSQL(alerta.getDropTableAlerta());
		db.execSQL(paciente.getDropTable());
		db.execSQL(responsavel.getDropTable());
		db.execSQL(estadoMarcacao.getDropTableEstamarc());
		// TODO Auto-generated method stub
		
		onCreate(db);
	}

    public String dataAtual()
    {
        //4", "13:13:13", "12-12-12");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date dNow = new Date();
        String dataAtualValor = ft.format(dNow);
        return dataAtualValor;
    }

    public String horaAtual()
    {
        SimpleDateFormat ft = new SimpleDateFormat("HH:MM:ss");
        Date dNow = new Date();
        String horaAtualValor = ft.format(dNow);
        return horaAtualValor;
    }
	

}
