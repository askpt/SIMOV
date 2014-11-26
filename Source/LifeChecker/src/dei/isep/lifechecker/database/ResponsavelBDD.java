package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.Responsavel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ResponsavelBDD {
	public static final String TABLE_RESPONSAVEL = "responsavel";
	
	public static final String COL_ID_RESP = "idResp";
	public static final String COL_NOME_RESP = "nomeResp";
	public static final String COL_APELIDO_RESP = "apelidoResp";
	public static final String COL_CONTACTO_RESP = "contactoResp";
	public static final String COL_NOTIFICACAOMAIL_RESP = "notifMAIL";
	public static final String COL_NOTIFICACAOSMS_RESP = "notifSMS";
	public static final String COL_PERIODICIDADEDIURNA_RESP = "peridDiurnaResp";
	public static final String COL_PERIODICIDADENOTURNA_RESP = "peridNoturnaResp";
	public static final String COL_MAIL_RESP = "mailResp";
	public static final String COL_PASS_RESP = "passResp";
	public static final String COL_ESTAON_RESP = "estaOnResp";
	
	public static final int NUM_COL_ID_RESP = 0;
	public static final int NUM_NOME_RESP = 1;
	public static final int NUM_APELIDO_RESP = 2;
	public static final int NUM_CONTACTO_RESP = 3;
	public static final int NUM_NOTIFICACAOMAIL_RESP = 4;
	public static final int NUM_NOTIFICACAOSMS_RESP = 5;
	public static final int NUM_PERIODICIDADEDIURNA_RESP = 6;
	public static final int NUM_PERIODICIDADENOTURNA_RESP = 7;
	public static final int NUM_MAIL_RESP = 8;
	public static final int NUM_PASS_RESP = 9;
	public static final int NUM_ESTAON_RESP = 10;
	
	public static final String CREATE_TABLE_RESPONSAVEL = "CREATE TABLE " + TABLE_RESPONSAVEL + " ("
			+ COL_ID_RESP + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_NOME_RESP + " TEXT NOT NULL, "
			+ COL_APELIDO_RESP + " TEXT NOT NULL, " 
			+ COL_CONTACTO_RESP + " TEXT NOT NULL, "
			+ COL_NOTIFICACAOMAIL_RESP + " NUMERIC NOT NULL, "
			+ COL_NOTIFICACAOSMS_RESP + " NUMERIC NOT NULL, "
			+ COL_PERIODICIDADEDIURNA_RESP + " INTEGER NOT NULL, "
			+ COL_PERIODICIDADENOTURNA_RESP + " INTEGER NOT NULL, "
			+ COL_MAIL_RESP + " TEXT NOT NULL, "
			+ COL_PASS_RESP + " TEXT NOT NULL, "
			+ COL_ESTAON_RESP + " NUMERIC NOT NULL);";
	
	public static final String DROP_TABLE_RESPONSAVEL = "DROP TABLE " + TABLE_RESPONSAVEL + ";";
	
	private SQLiteDatabase bdd;
	private BaseDeDadosInterna baseDeDados;
	
	public ResponsavelBDD(){};
	
	public ResponsavelBDD(Context context)
	{
		baseDeDados = new BaseDeDadosInterna(context);
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
	
	public long inserirResponsavel(Responsavel resp)
	{
		long valueResult = 0;
		open();
		ContentValues values = new ContentValues();
		values.put(COL_NOME_RESP, resp.getNomeResposnavel());
		values.put(COL_APELIDO_RESP, resp.getApelidoResposnavel());
		values.put(COL_CONTACTO_RESP, resp.getContactoResponsavel());
		values.put(COL_NOTIFICACAOMAIL_RESP, resp.getNotificacaoMail());
		values.put(COL_NOTIFICACAOSMS_RESP, resp.getNotificacaoSMS());
		values.put(COL_PERIODICIDADEDIURNA_RESP, resp.getPeriodicidadeDiurna());
		values.put(COL_PERIODICIDADENOTURNA_RESP, resp.getPeriodicidadeNoturna());
		values.put(COL_MAIL_RESP, resp.getMailResponsavel());
		values.put(COL_PASS_RESP, resp.getPassResponsavel());
		values.put(COL_ESTAON_RESP, resp.getEstaOnline());
		valueResult = bdd.insert(TABLE_RESPONSAVEL, null, values);
		close();
		return valueResult;
		
	}
	
	public boolean existeResponsavel(int id)
	{
		String sqlQuery = "SELECT * FROM " + TABLE_RESPONSAVEL + " WHERE " + COL_ID_RESP + " = " + id;
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
	
	public String getCreateTable()
	{
		return CREATE_TABLE_RESPONSAVEL;
	}
	
	public String getDropTable()
	{
		return DROP_TABLE_RESPONSAVEL;
	}
	

}
