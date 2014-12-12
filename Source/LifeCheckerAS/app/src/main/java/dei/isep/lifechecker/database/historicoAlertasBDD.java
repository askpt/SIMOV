package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.historicoAlertas;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class historicoAlertasBDD {
	/*
	 * int idHistAlt; int idPacienteHistAlt; int idAlertaHistAlt; String
	 * horaHistAlt; String dataHistAlt; double latitudeHistAlt; double
	 * longitudeHistAlt; String LocalHistAlt; boolean estaOnlineHistAlt;
	 */

	public static final String TABLE_HISTORICO_ALERTAS = "historico_alertas";

	public static final String COL_ID_HISTO = "idHisto";
	public static final String COL_ID_PACIENTE_HISTO = "idPacienteHisto";
	public static final String COL_ID_ALERTA_HISTO = "idAlertaHisto";
	public static final String COL_HORA_HISTO = "horaAlertaHisto";
	public static final String COL_DATA_HISTO = "dataAlertaHisto";
	public static final String COL_LONG_HISTO = "longAlertaHisto";
	public static final String COL_LAT_HISTO = "latAlertaHisto";
	public static final String COL_NOME_LOCAL_HISTO = "nomeLocalHisto";
	public static final String COL_HORA_SINCRO_HISTO = "horaSincroHisto";
	public static final String COL_DATA_SINCRO_HISTO = "dataSincroHisto";

	public static final int NUM_COL_ID_HISTO = 0;
	public static final int NUM_COL_ID_PACIENTE_HISTO = 1;
	public static final int NUM_COL_ID_ALERTA_HISTO = 2;
	public static final int NUM_COL_HORA_HISTO = 3;
	public static final int NUM_COL_DATA_HISTO = 4;
	public static final int NUM_COL_LONG_HISTO = 5;
	public static final int NUM_COL_LAT_HISTO = 6;
	public static final int NUM_COL_NOME_LOCAL_HISTO = 7;
	public static final int NUM_COL_HORA_SINCRO_HISTO = 8;
	public static final int NUM_COL_DATA_SINCRO_HISTO = 9;

	public static final String CREATE_TABLE_HITORICOALERTA = "CREATE TABLE "
			+ TABLE_HISTORICO_ALERTAS + " (" + COL_ID_HISTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_ID_PACIENTE_HISTO + " INTEGER NOT NULL REFERENCES " + responsavelBDD.TABLE_RESPONSAVEL + " (" + responsavelBDD.COL_ID_RESP + "), " 
			+ COL_ID_ALERTA_HISTO + " INTEGER NOT NULL REFERENCES " + alertaBDD.TABLE_ALERTA + " ("+ alertaBDD.COL_ID_ALERT + "), " 
			+ COL_HORA_HISTO + " TEXT NOT NULL, " 
			+ COL_DATA_HISTO + " TEXT NOT NULL, "
			+ COL_LONG_HISTO + " REAL, " 
			+ COL_LAT_HISTO + " REAL NOT NULL, "
			+ COL_NOME_LOCAL_HISTO + " TEXT NOT NULL, " 
			+ COL_HORA_SINCRO_HISTO + " TEXT NOT NULL, "
			+ COL_DATA_SINCRO_HISTO + " TEXT NOT NULL); ";

	public static final String DROP_TABLE_HISTORICOALERTAS = "DROP TABLE "
			+ TABLE_HISTORICO_ALERTAS + ";";

	private SQLiteDatabase bdd;
	private baseDeDadosInterna baseDeDados;
	private Context context;

	public historicoAlertasBDD() {
	};

	public historicoAlertasBDD(Context context) {
		baseDeDados = new baseDeDadosInterna(context);
		this.context = context;
	}

	public void open() {
		bdd = baseDeDados.getWritableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long inserirHistoricoAlerta(historicoAlertas historicoAlerta) {
		/*
		 * public static final String COL_ID_PACIENTE_HISTO = "idPacienteHisto";
		 * public static final String COL_ID_ALERTA_HISTO = "idAlertaHisto";
		 * public static final String COL_HORA_HISTO = "horaAlertaHisto"; public
		 * static final String COL_DATA_HISTO = "dataAlertaHisto"; public static
		 * final String COL_LONG_HISTO = "longAlertaHisto"; public static final
		 * String COL_LAT_HISTO = "latAlertaHisto"; public static final String
		 * COL_NOME_LOCAL_HISTO = "nomeLocalHisto"; public static final String
		 * COL_ESTAON_HISTO = "estaOnHisto";
		 */
		long valueResult = 0;
		pacienteBDD paciente = new pacienteBDD(context);
		if (paciente.existePaciente(historicoAlerta.getIdPacienteHistAlt()) == true) {
			alertaBDD alerta = new alertaBDD(context);
			if (alerta.existeAlerta(historicoAlerta.getIdAlertaHistAlt()) == true) {
				open();
				ContentValues value = new ContentValues();
				value.put(COL_ID_PACIENTE_HISTO,
						historicoAlerta.getIdPacienteHistAlt());
				value.put(COL_ID_ALERTA_HISTO,
						historicoAlerta.getIdAlertaHistAlt());
				value.put(COL_HORA_HISTO, historicoAlerta.getHoraHistAlt());
				value.put(COL_DATA_HISTO, historicoAlerta.getDataHistAlt());
				value.put(COL_LONG_HISTO, historicoAlerta.getLongitudeHistAlt());
				value.put(COL_LAT_HISTO, historicoAlerta.getLatitudeHistAlt());
				value.put(COL_NOME_LOCAL_HISTO,
						historicoAlerta.getLocalHistAlt());
				value.put(COL_HORA_SINCRO_HISTO, historicoAlerta.getHoraSincroHistAlt());
				value.put(COL_DATA_SINCRO_HISTO, historicoAlerta.getDataSincroHistAlt());
				
				valueResult = bdd.insert(TABLE_HISTORICO_ALERTAS, null, value);
				close();
			}
			else
			{
				valueResult = -1;
			}
		} else {
			valueResult = -1;
		}

		return valueResult;

	}

	public String getCreateTableHitoricoalerta() {
		return CREATE_TABLE_HITORICOALERTA;
	}

	public String getDropTableHistoricoalertas() {
		return DROP_TABLE_HISTORICOALERTAS;
	}
	
	

}
