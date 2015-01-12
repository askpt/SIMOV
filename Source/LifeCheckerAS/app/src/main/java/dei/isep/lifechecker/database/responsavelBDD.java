package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.responsavel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class responsavelBDD {
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
	public static final String COL_HORA_SINCRO_RESP = "horaSincroResp";
	public static final String COL_DATA_SINCRO_RESP = "dataSincroResp";
	
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
	public static final int NUM_HORA_SINCRO_RESP = 11;
	public static final int NUM_DATA_SINCRO_RESP = 12;
	
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
			+ COL_HORA_SINCRO_RESP + " TEXT NOT NULL, "
			+ COL_DATA_SINCRO_RESP + " TEXT NOT NULL); ";
	
	public static final String DROP_TABLE_RESPONSAVEL = "DROP TABLE " + TABLE_RESPONSAVEL + ";";
	
	private SQLiteDatabase bdd;
	private baseDeDadosInterna baseDeDados;
	
	public responsavelBDD(){};
	
	public responsavelBDD(Context context)
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
	
	public long inserirResponsavel(responsavel resp)
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
		values.put(COL_HORA_SINCRO_RESP, baseDeDados.horaAtual());
		values.put(COL_DATA_SINCRO_RESP, baseDeDados.dataAtual());
		valueResult = bdd.insert(TABLE_RESPONSAVEL, null, values);
		close();
		return valueResult;
		
	}

    public long inserirResponsavelComId(responsavel resp)
    {
        long valueResult = 0;
        open();
        ContentValues values = new ContentValues();
        values.put(COL_ID_RESP, resp.getIdResponsavel());
        values.put(COL_NOME_RESP, resp.getNomeResposnavel());
        values.put(COL_APELIDO_RESP, resp.getApelidoResposnavel());
        values.put(COL_CONTACTO_RESP, resp.getContactoResponsavel());
        values.put(COL_NOTIFICACAOMAIL_RESP, resp.getNotificacaoMail());
        values.put(COL_NOTIFICACAOSMS_RESP, resp.getNotificacaoSMS());
        values.put(COL_PERIODICIDADEDIURNA_RESP, resp.getPeriodicidadeDiurna());
        values.put(COL_PERIODICIDADENOTURNA_RESP, resp.getPeriodicidadeNoturna());
        values.put(COL_MAIL_RESP, resp.getMailResponsavel());
        values.put(COL_PASS_RESP, resp.getPassResponsavel());
        values.put(COL_HORA_SINCRO_RESP, baseDeDados.horaAtual());
        values.put(COL_DATA_SINCRO_RESP, baseDeDados.dataAtual());
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
	
	public int getQtdResponsavel()
	{
		int quantidade = 0;
		String sqlQuery = "SELECT COUNT(*) FROM " + TABLE_RESPONSAVEL;
		open();
		Cursor cursor = bdd.rawQuery(sqlQuery, null);
		cursor.moveToNext();
		quantidade = cursor.getInt(0);
		close();
		return quantidade;
	}

    /*
    Não testado
     */
    public responsavel getResponsavel()
    {
        int qtd = getQtdResponsavel();
        responsavel resp = new responsavel();
        if(qtd == 1)
        {
            int id;
            String nome;
            String apelido;
            String contacto;
            boolean notifMail;
            boolean notifSMS;
            int periodicidadeDiruna;
            int periodicidadeNoturna;
            String mail;
            String pass;

            String dataSincro;
            String horaSincro;

            String sqlQuery = "SELECT * FROM " + TABLE_RESPONSAVEL;
            open();
            Cursor cursor = bdd.rawQuery(sqlQuery, null);
            if(cursor.moveToFirst())
            {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID_RESP));
                nome = cursor.getString(cursor.getColumnIndex(COL_NOME_RESP));
                apelido = cursor.getString(cursor.getColumnIndex(COL_APELIDO_RESP));
                contacto = cursor.getString(cursor.getColumnIndex(COL_CONTACTO_RESP));
                apelido = cursor.getString(cursor.getColumnIndex(COL_APELIDO_RESP));
                notifMail = converterBoolean(Integer.valueOf(cursor.getString(cursor.getColumnIndex(COL_NOTIFICACAOMAIL_RESP))));
                notifSMS = converterBoolean(Integer.valueOf(cursor.getString(cursor.getColumnIndex(COL_NOTIFICACAOSMS_RESP))));
                periodicidadeDiruna = cursor.getInt(cursor.getColumnIndex(COL_PERIODICIDADEDIURNA_RESP));
                periodicidadeNoturna = cursor.getInt(cursor.getColumnIndex(COL_PERIODICIDADENOTURNA_RESP));
                mail = cursor.getString(cursor.getColumnIndex(COL_MAIL_RESP));
                pass = cursor.getString(cursor.getColumnIndex(COL_PASS_RESP));
                horaSincro = cursor.getString(cursor.getColumnIndex(COL_HORA_SINCRO_RESP));
                dataSincro = cursor.getString(cursor.getColumnIndex(COL_DATA_SINCRO_RESP));
                resp = new responsavel(id, nome, apelido, contacto, notifMail, notifSMS, periodicidadeDiruna, periodicidadeNoturna
                , mail, pass, horaSincro, dataSincro);
            }
            close();
        }
        return resp;
    }

    public int getIdResponsavel()
    {
        String sqlQuery = "SELECT " + COL_ID_RESP + " FROM " + TABLE_RESPONSAVEL;
        int idReturn = -1;
        open();
        Cursor cursor= bdd.rawQuery(sqlQuery, null);
        if(cursor.getCount() == 1 && cursor.moveToFirst())
        {
            idReturn = cursor.getInt(cursor.getColumnIndex(COL_ID_RESP));
        }
        return idReturn;
    }
	
	public String getCreateTable()
	{
		return CREATE_TABLE_RESPONSAVEL;
	}
	
	public String getDropTable()
	{
		return DROP_TABLE_RESPONSAVEL;
	}
	
    private boolean converterBoolean(int valor)
    {
        if(valor == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
