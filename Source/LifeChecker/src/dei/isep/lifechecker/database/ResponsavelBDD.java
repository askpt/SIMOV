package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.Responsavel;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ResponsavelBDD {
	private static final String TABLE_RESPONSAVEL = "responsavel";
	
	private static final String COL_ID_RESP = "idResp";
	private static final String COL_NOME_RESP = "nomeResp";
	private static final String COL_APELIDO_RESP = "apelidoResp";
	private static final String COL_CONTACTO_RESP = "contactoResp";
	private static final String COL_NOTIFICACAOMAIL_RESP = "notifMAIL";
	private static final String COL_NOTIFICACAOSMS_RESP = "notifSMS";
	private static final String COL_PERIODICIDADEDIURNA_RESP = "peridDiurnaResp";
	private static final String COL_PERIODICIDADENOTURNA_RESP = "peridNoturnaResp";
	private static final String COL_MAIL_RESP = "mailResp";
	private static final String COL_PASS_RESP = "passResp";
	private static final String COL_ESTAON_RESP = "estaOnResp";
	
	private static final int NUM_COL_ID_RESP = 0;
	private static final int NUM_NOME_RESP = 1;
	private static final int NUM_APELIDO_RESP = 2;
	private static final int NUM_CONTACTO_RESP = 3;
	private static final int NUM_NOTIFICACAOMAIL_RESP = 4;
	private static final int NUM_NOTIFICACAOSMS_RESP = 5;
	private static final int NUM_PERIODICIDADEDIURNA_RESP = 6;
	private static final int NUM_PERIODICIDADENOTURNA_RESP = 7;
	private static final int NUM_MAIL_RESP = 8;
	private static final int NUM_PASS_RESP = 9;
	private static final int NUM_ESTAON_RESP = 10;
	
	private static final String CREATE_TABLE_RESPONSAVEL = "CREATE TABLE " + TABLE_RESPONSAVEL + " ("
			+ COL_ID_RESP + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_NOME_RESP + " TEXT NOT NULL, "
			+ COL_APELIDO_RESP + " TEXT NOT NULL, " 
			+ COL_CONTACTO_RESP + " TEXT NOT NULL, "
			+ COL_NOTIFICACAOMAIL_RESP + " INTEGER NOT NULL, "
			+ COL_NOTIFICACAOSMS_RESP + " INTEGER NOT NULL, "
			+ COL_PERIODICIDADEDIURNA_RESP + " INTEGER NOT NULL, "
			+ COL_PERIODICIDADENOTURNA_RESP + " INTEGER NOT NULL, "
			+ COL_MAIL_RESP + " TEXT NOT NULL, "
			+ COL_PASS_RESP + " TEXT NOT NULL, "
			+ COL_ESTAON_RESP + " INTEGER NOT NULL);";
	
	private static final String DROP_TABLE_RESPONSAVEL = "DROP TABLE " + TABLE_RESPONSAVEL + ";";
	
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
		
		return bdd.insert(TABLE_RESPONSAVEL, null, values);
		
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
