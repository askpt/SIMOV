package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.alerta;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class alertaBDD {
	
	public static final String TABLE_ALERTA = "alerta";
	
	public static final String COL_ID_ALERT = "idAlerta";
	public static final String COL_EXPLICACAO_ALERT = "explicacaoAlerta";
	

	public static final String COL_HORA_SINCRO_ALERT = "horaSincroAlerta";
	public static final String COL_DATA_SINCRO_ALERT = "dataSincroAlerta";
	
	public static final int NUM_COL_ID_ALERT = 0;
	public static final int NUM_COL_EXPLICACAO_ALERT = 1;
	public static final int NUM_COL_HORA_SINCRO_ALERT = 2;
	public static final int NUM_COL_DATA_SINCRO_ALERT = 3;
	
	
	public static final String CREATE_TABLE_ALERT = "CREATE TABLE " + TABLE_ALERTA + " ("
			+ COL_ID_ALERT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_EXPLICACAO_ALERT + " TEXT NOT NULL, "
			+ COL_HORA_SINCRO_ALERT + " TEXT NOT NULL, "
			+ COL_DATA_SINCRO_ALERT + " TEXT NOT NULL);";
	
	public static final String DROP_TABLE_ALERTA = "DROP TABLE " + TABLE_ALERTA + ";";
	
	private SQLiteDatabase bdd;
	private baseDeDadosInterna baseDeDados;
	
	public alertaBDD(){};
	
	public alertaBDD(Context context)
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
	
	public SQLiteDatabase getBDD()
	{
		return bdd;
	}
	
	public long inserirAlerta(alerta alerta)
	{
		long valueResult = 0;
		open();
		ContentValues values = new ContentValues();
		values.put(COL_EXPLICACAO_ALERT, alerta.getExplicacaoAlerta());
		values.put(COL_HORA_SINCRO_ALERT, alerta.getHoraSincroAlerta());
		values.put(COL_DATA_SINCRO_ALERT, alerta.getDataSincroAlerta());
		valueResult = bdd.insert(TABLE_ALERTA, null, values);
		close();
		return valueResult;
	}


    public long inserirAlertaId(alerta alerta)
    {
        long valueResult = 0;
        open();
        ContentValues values = new ContentValues();
        values.put(COL_ID_ALERT, alerta.getIdAlerta());
        values.put(COL_EXPLICACAO_ALERT, alerta.getExplicacaoAlerta());
        values.put(COL_HORA_SINCRO_ALERT, alerta.getHoraSincroAlerta());
        values.put(COL_DATA_SINCRO_ALERT, alerta.getDataSincroAlerta());
        valueResult = bdd.insert(TABLE_ALERTA, null, values);
        close();
        return valueResult;
    }
	
	public boolean existeAlerta(int id)
	{
		String sqlQuery = "SELECT * FROM " + TABLE_ALERTA + " WHERE " + COL_ID_ALERT + " = " + id;
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

    public String getDesignacaoById(int idAlerta) {
        String sqlQuery = "SELECT * FROM " + TABLE_ALERTA + " WHERE " + COL_ID_ALERT + " = " + idAlerta;
        String designacao = "";
        open();
        Cursor cursor = bdd.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            designacao = cursor.getString(cursor.getColumnIndex(COL_EXPLICACAO_ALERT));
        }
        close();
        return designacao;
    }

	public String getCreateTableAlert() {
		return CREATE_TABLE_ALERT;
	}

	public String getDropTableAlerta() {
		return DROP_TABLE_ALERTA;
	}
	
	

}
