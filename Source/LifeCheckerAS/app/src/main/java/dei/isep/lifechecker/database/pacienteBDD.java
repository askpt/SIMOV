package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.paciente;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class pacienteBDD {
	
	public static final String TABLE_PACIENTE = "paciente";
	
	public static final String COL_ID_PACI = "idPaci";
	public static final String COL_ID_RESP_PACI = "idRespPaci";
	public static final String COL_NOME_PACI = "nomePaci";
	public static final String COL_APELIDO_PACI = "apelidoPaci";
	public static final String COL_MAIL_PACI = "mailPaci";
	public static final String COL_CONTACTO_PACI = "contactoPaci";
	public static final String COL_LONG_PACI = "longPaci";
	public static final String COL_LAT_PACI = "latPaci";
	public static final String COL_NOME_LOCAL_PACI = "nomeLocalPaci";
	public static final String COL_HORA_LOCAL_PACI = "horaLocalPaci";
	public static final String COL_DATA_LOCAL_PACI = "dataLocalPaci";
	public static final String COL_ATIVO_PACI = "ativoPaci";
	public static final String COL_ESTAON_PACI = "estaOnPaci";
	public static final String COL_HORA_SINCRO_RESP = "horaSincroPaci";
	public static final String COL_DATA_SINCRO_RESP = "dataSincroPaci";
	
	public static final int NUM_COL_ID_PACI = 0;
	public static final int NUM_COL_ID_RESP_PACI = 1;
	public static final int NUM_COL_NOME_PACI = 2;
	public static final int NUM_COL_APELIDO_PACI = 3;
	public static final int NUM_COL_MAIL_PACI = 4;
	public static final int NUM_COL_CONTACTO_PACI = 5;
	public static final int NUM_COL_LONG_PACI = 6;
	public static final int NUM_COL_LAT_PACI = 7;
	public static final int NUM_COL_NOME_LOCAL_PACI = 8;
	public static final int NUM_COL_HORA_LOCAL_PACI = 9;
	public static final int NUM_COL_DATA_LOCAL_PACI = 10;
	public static final int NUM_COL_ATIVO_PACI = 11;
	public static final int NUM_COL_ESTAON_PACI = 12;
	public static final int NUM_HORA_SINCRO_PACI = 13;
	public static final int NUM_DATA_SINCRO_PACI = 14;
	
	public static final String CREATE_TABLE_PACIENTE = "CREATE TABLE " + TABLE_PACIENTE + " ("
			+ COL_ID_PACI + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_ID_RESP_PACI + " INTEGER NOT NULL REFERENCES " + responsavelBDD.TABLE_RESPONSAVEL + " (" + responsavelBDD.COL_ID_RESP + "),"  
			+ COL_NOME_PACI + " TEXT NOT NULL, "
			+ COL_APELIDO_PACI + " TEXT NOT NULL, "
			+ COL_MAIL_PACI + " TEXT NOT NULL, "
			+ COL_CONTACTO_PACI + " TEXT NOT NULL, "
			+ COL_LONG_PACI + " REAL, "
			+ COL_LAT_PACI + " REAL, "
			+ COL_NOME_LOCAL_PACI + " TEXT, "
			+ COL_HORA_LOCAL_PACI + " TEXT, "
			+ COL_DATA_LOCAL_PACI + " TEXT, "
			+ COL_ATIVO_PACI + " NUMERIC NOT NULL, "
			+ COL_HORA_SINCRO_RESP + " TEXT NOT NULL, "
			+ COL_DATA_SINCRO_RESP + " TEXT NOT NULL); ";
	
	public static final String DROP_TABLE_PACIENTE =  "DROP TABLE " + TABLE_PACIENTE + ";";

	private SQLiteDatabase bdd;
	private baseDeDadosInterna baseDeDados;
	private Context context;
	
	public pacienteBDD(){};
	
	public pacienteBDD(Context context)
	{
		baseDeDados = new baseDeDadosInterna(context);
		this.context = context;
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
	

	/*
	 * Inserir paciente Novo
	 */
	public long inserirPaciente(paciente paciente)
	{
		long valueResult = 0;
		responsavelBDD responsavel = new responsavelBDD(context);
	
		if(responsavel.existeResponsavel(paciente.getIdResponsavelPaciente()) == true)
		{
			open();
			ContentValues value = new ContentValues();
			value.put(COL_ID_RESP_PACI, paciente.getIdResponsavelPaciente());
			value.put(COL_NOME_PACI, paciente.getNomePaciente());
			value.put(COL_APELIDO_PACI, paciente.getApelidoPaciente());
			value.put(COL_MAIL_PACI, paciente.getMailPaciente());
			value.put(COL_CONTACTO_PACI, paciente.getContactoPaciente());
			value.put(COL_ATIVO_PACI, paciente.getApelidoPaciente());
            value.put(COL_HORA_SINCRO_RESP, baseDeDados.horaAtual());
            value.put(COL_DATA_SINCRO_RESP, baseDeDados.dataAtual());
			valueResult =  bdd.insert(TABLE_PACIENTE, null, value);
			close();
		}
		else
		{
			valueResult =  -1;
		}
		return valueResult;
		
	}

    public long inserirPacienteComId(paciente paciente)
    {
        long valueResult = 0;
        responsavelBDD responsavel = new responsavelBDD(context);

        if(responsavel.existeResponsavel(paciente.getIdResponsavelPaciente()) == true)
        {
            open();
            ContentValues value = new ContentValues();
            value.put(COL_ID_PACI, paciente.getIdPaciente());
            value.put(COL_ID_RESP_PACI, paciente.getIdResponsavelPaciente());
            value.put(COL_NOME_PACI, paciente.getNomePaciente());
            value.put(COL_APELIDO_PACI, paciente.getApelidoPaciente());
            value.put(COL_MAIL_PACI, paciente.getMailPaciente());
            value.put(COL_CONTACTO_PACI, paciente.getContactoPaciente());
            value.put(COL_ATIVO_PACI, paciente.getApelidoPaciente());
            value.put(COL_HORA_SINCRO_RESP, baseDeDados.horaAtual());
            value.put(COL_DATA_SINCRO_RESP, baseDeDados.dataAtual());
            valueResult =  bdd.insert(TABLE_PACIENTE, null, value);
            close();
        }
        else
        {
            valueResult =  -1;
        }
        return valueResult;

    }
	
	public boolean existePaciente(int id)
	{
		String sqlQuery = "SELECT * FROM " + TABLE_PACIENTE + " WHERE " + COL_ID_PACI + " = " + id;
		open();
		Cursor cursor = bdd.rawQuery(sqlQuery, null);
		if(cursor.getCount() == 1)
		{
            close();
			return true;
		}
		else
		{
            close();
			return false;
		}

	}

    public String getNomeApelidoPacienteById(int id)
    {
        String nomePaciente = "";
        String sqlQuery = "SELECT * FROM " + TABLE_PACIENTE + " WHERE " + COL_ID_PACI + " = " + id;
        open();
        Cursor cursor = bdd.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()) {
            nomePaciente = cursor.getString(cursor.getColumnIndex(COL_NOME_PACI));
            nomePaciente += " " + cursor.getString(cursor.getColumnIndex(COL_APELIDO_PACI));
        }
        close();
        return  nomePaciente;
    }

    public String getNomeApelidoPrimeiraLetPacienteById(int idPaciente)
    {
        String nomePaciente = "";
        String apelidoPaciente = "";
        String sqlQuery = "SELECT * FROM " + TABLE_PACIENTE + " WHERE " + COL_ID_PACI + " = " + idPaciente;
        open();
        Cursor cursor = bdd.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()) {
            nomePaciente = cursor.getString(cursor.getColumnIndex(COL_NOME_PACI));
            apelidoPaciente = cursor.getString(cursor.getColumnIndex(COL_APELIDO_PACI));
        }
        close();

        String resultado = nomePaciente + " " + apelidoPaciente.substring(0,1) + ".";
        return  resultado;
    }

    public ArrayList<paciente> listaPacientes(int id)
    {
        ArrayList<paciente> list = new ArrayList<paciente>();
        int idPaciente;
        int idResponsavelPaciente;
        String nomePaciente;
        String apelidoPaciente;
        String mailPaciente;
        String contactoPaciente;
        double latitudePaciente;
        double longitudePaciente;
        String nomeLocalPaciente;
        String horaLocalPaciente;
        String dataLocalPaciente;

        boolean ativoPaciente;
        String actPaci;

        String horaSincroPaciente;
        String dataSincroPaciente;

        String sqlQuery = "SELECT * FROM " + TABLE_PACIENTE + " where " + COL_ID_RESP_PACI + " = " + id;
        open();
        Cursor cursor = bdd.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()){
            idPaciente = cursor.getInt(cursor.getColumnIndex(COL_ID_PACI));
            idResponsavelPaciente = cursor.getInt(cursor.getColumnIndex(COL_ID_RESP_PACI));
            nomePaciente = cursor.getString(cursor.getColumnIndex(COL_NOME_PACI));
            apelidoPaciente = cursor.getString(cursor.getColumnIndex(COL_APELIDO_PACI));
            mailPaciente = cursor.getString(cursor.getColumnIndex(COL_MAIL_PACI));
            contactoPaciente = cursor.getString(cursor.getColumnIndex(COL_CONTACTO_PACI));
            latitudePaciente = cursor.getDouble(cursor.getColumnIndex(COL_LAT_PACI));
            longitudePaciente = cursor.getDouble(cursor.getColumnIndex(COL_LAT_PACI));
            nomeLocalPaciente = cursor.getString(cursor.getColumnIndex(COL_NOME_LOCAL_PACI));

            actPaci = cursor.getString(cursor.getColumnIndex(COL_ATIVO_PACI));

            horaLocalPaciente = cursor.getString(cursor.getColumnIndex(COL_HORA_SINCRO_RESP));
            dataLocalPaciente = cursor.getString(cursor.getColumnIndex(COL_DATA_SINCRO_RESP));

            boolean actBool = Boolean.valueOf(actPaci.replaceAll("[\r\n]+", ""));

            paciente paci = new paciente(idPaciente,
                    idResponsavelPaciente, nomePaciente,
                    apelidoPaciente, mailPaciente, contactoPaciente, latitudePaciente,
                    longitudePaciente, nomeLocalPaciente, horaLocalPaciente, dataLocalPaciente,actBool,
                    horaLocalPaciente, dataLocalPaciente);
            list.add(paci);
        }
        close();
        return list;
    }

    public long atualizarPaciente(paciente paciente)
    {
        String condicao = COL_ID_PACI + " = " + paciente.getIdPaciente();
        long valueResult = 0;
        open();
        ContentValues value = new ContentValues();
        value.put(COL_ID_PACI, paciente.getIdPaciente());
        value.put(COL_ID_RESP_PACI, paciente.getIdResponsavelPaciente());
        value.put(COL_NOME_PACI, paciente.getNomePaciente());
        value.put(COL_APELIDO_PACI, paciente.getApelidoPaciente());
        value.put(COL_MAIL_PACI, paciente.getMailPaciente());
        value.put(COL_CONTACTO_PACI, paciente.getContactoPaciente());
        value.put(COL_ATIVO_PACI, paciente.getApelidoPaciente());
        value.put(COL_HORA_SINCRO_RESP, baseDeDados.horaAtual());
        value.put(COL_DATA_SINCRO_RESP, baseDeDados.dataAtual());
        valueResult =  bdd.update(TABLE_PACIENTE, value, condicao, null);
        close();
        return valueResult;
    }

    public paciente getPacienteById(int idPaciente)
    {
        paciente paci = new paciente();
        String sqlQury = "SELECT * FROM "+ TABLE_PACIENTE + " WHERE " + COL_ID_PACI + " = " +idPaciente;
        open();
        Cursor cursor = bdd.rawQuery(sqlQury,null);
        if(cursor.moveToFirst())
        {
            paci.setIdPaciente(cursor.getInt(cursor.getColumnIndex(COL_ID_PACI)));;
            paci.setIdResponsavelPaciente(cursor.getInt(cursor.getColumnIndex(COL_ID_RESP_PACI)));;
            paci.setNomePaciente(cursor.getString(cursor.getColumnIndex(COL_NOME_PACI)));
            paci.setApelidoPaciente(cursor.getString(cursor.getColumnIndex(COL_APELIDO_PACI)));
            paci.setMailPaciente(cursor.getString(cursor.getColumnIndex(COL_MAIL_PACI)));
            paci.setContactoPaciente(cursor.getString(cursor.getColumnIndex(COL_CONTACTO_PACI)));
            paci.setLatitudePaciente(cursor.getDouble(cursor.getColumnIndex(COL_LAT_PACI)));
            paci.setLongitudePaciente(cursor.getDouble(cursor.getColumnIndex(COL_LONG_PACI)));
            paci.setNomeLocalPaciente(cursor.getString(cursor.getColumnIndex(COL_NOME_LOCAL_PACI)));
            paci.setHoraLocalPaciente(cursor.getString(cursor.getColumnIndex(COL_HORA_LOCAL_PACI)));
            paci.setDataLocalPaciente(cursor.getString(cursor.getColumnIndex(COL_DATA_LOCAL_PACI)));
            paci.setHoraSincroPaciente(cursor.getString(cursor.getColumnIndex(COL_HORA_SINCRO_RESP)));
            paci.setDataSincroPaciente(cursor.getString(cursor.getColumnIndex(COL_DATA_SINCRO_RESP)));
            paci.setAtivoPaciente(true);
        }
        close();
        return  paci;
    }
	
	public int getNumPacientes()
	{
		int quantidade = 0;
		String sqlQuery = "SELECT COUNT(*) FROM " + TABLE_PACIENTE;
		open();
		Cursor cursor = bdd.rawQuery(sqlQuery, null);
		cursor.moveToNext();
		quantidade = cursor.getInt(0);
		close();
		return quantidade;
	}

    public int getIdPaicente()
    {
            String sqlQuery = "SELECT " + COL_ID_PACI + " FROM " + TABLE_PACIENTE;
            int idReturn = -1;
            open();
            Cursor cursor= bdd.rawQuery(sqlQuery, null);
            if(cursor.getCount() == 1 && cursor.moveToFirst())
            {
                idReturn = cursor.getInt(cursor.getColumnIndex(COL_ID_PACI));
            }
            return idReturn;
    }

    public void deleteConteudoPaciente()
    {
        String sqlQuery = "DELTE FROM " + TABLE_PACIENTE;
        open();
        bdd.delete(TABLE_PACIENTE, null, null);
        close();
    }
	
	
	public String getCreateTable()
	{
		return CREATE_TABLE_PACIENTE;
	}
	
	public String getDropTable()
	{
		return DROP_TABLE_PACIENTE;
	}
	
	
}
