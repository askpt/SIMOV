package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.estadoMarcacao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class estadoMarcacaoBDD {
	
	public static final String TABLE_ESTMARC = "estado_marcacao";
	
	public static final String COL_ID_ESTMARC = "idEstMarc";
	public static final String COL_EXPLICACAO_ESTMARC = "explicacaoEstMarc";
	
	public static final int NUM_COL_ID_ESTMARC = 0;
	public static final int NUM_COL_EXPLICACAO_ESTMARC = 1;
	
	public static final String CREATE_TABLE_ESTAMARC = "CREATE TABLE " + TABLE_ESTMARC + " ("
			+ COL_ID_ESTMARC + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_EXPLICACAO_ESTMARC + " TEXT NOT NULL);";
	
	private static final String DROP_TABLE_ESTAMARC = "DROP TABLE " + TABLE_ESTMARC + ";";
	
	private SQLiteDatabase bdd;
	private baseDeDadosInterna baseDeDados;
	
	public estadoMarcacaoBDD(){};
	
	public estadoMarcacaoBDD(Context context)
	{
		baseDeDados = new baseDeDadosInterna(context);
	}
	
	public void open()
	{
		bdd = baseDeDados.getWritableDatabase();
	}
	
	public void close()
	{
		bdd.close();
	}
	
	public long inserirEstadoMarcacao(estadoMarcacao estadoMarca)
	{
		long valueResult = 0;
		open();
		ContentValues values = new ContentValues();
		values.put(COL_EXPLICACAO_ESTMARC, estadoMarca.getExplicacaoEstMarc());
		valueResult = bdd.insert(TABLE_ESTMARC, null, values);
		close();
		return valueResult;
	}
	
	public boolean existeEstadoMarcacao(int id)
	{
		String sqlQuery = "SELECT * FROM " + TABLE_ESTMARC + " WHERE " + COL_ID_ESTMARC + " = " + id;
		open();
		Cursor cursor = bdd.rawQuery(sqlQuery, null);
		if(cursor.getCount() == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getCreateTableEstamarc() {
		return CREATE_TABLE_ESTAMARC;
	}

	public String getDropTableEstamarc() {
		return DROP_TABLE_ESTAMARC;
	}
	
	

}
