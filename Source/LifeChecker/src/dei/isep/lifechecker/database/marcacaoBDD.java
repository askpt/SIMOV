package dei.isep.lifechecker.database;

import dei.isep.lifechecker.model.marcacao;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class marcacaoBDD {

	public static String TABLE_MARCACAO = "marcacao";

	public static final String COL_ID_MARCACAO_MARCA = "idMarcacao";
	public static final String COL_ID_PACIENTE_MARCA = "idPacienteMarca";
	public static final String COL_ID_ESTADO_MARCA = "idEstadoMarca";
	public static final String COL_TIPO_MARCA = "tipoMarca";
	public static final String COL_HORA_MARCA = "horaMarca";
	public static final String COL_DATA_MARCA = "dataMarca";
	public static final String COL_LONG_MARCA = "longMarca";
	public static final String COL_LAT_MARCA = "larMarca";
	public static final String COL_NOME_LOCAL_MARCA = "nomeLocalMarca";
	public static final String COL_HORA_SINCRO_MARCA = "horaSincroMarca";
	public static final String COL_DATA_SINCRO_MARCA = "dataSincroMarca";

	public static final int NUM_COL_ID_MARCACAO_MARCA = 0;
	public static final int NUM_COL_ID_PACIENTE_MARCA = 1;
	public static final int NUM_COL_ID_ESTADO_MARCA = 2;
	public static final int NUM_COL_TIPO_MARCA = 3;
	public static final int NUM_COL_HORA_MARCA = 4;
	public static final int NUM_COL_DATA_MARCA = 5;
	public static final int NUM_COL_LONG_MARCA = 6;
	public static final int NUM_COL_LAT_MARCA = 7;
	public static final int NUM_COL_NOME_LOCAL_MARCA = 8;
	public static final int NUM_COL_HORA_SINCRO_MARCA = 9;
	public static final int NUM_COL_DATA_SINCRO_MARCA = 10;

	public static final String CREATE_TABLE_MARCACAO = "CREATE TABLE "
			+ TABLE_MARCACAO + " (" + COL_ID_MARCACAO_MARCA
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ID_PACIENTE_MARCA
			+ " INTEGER NOT NULL REFERENCES " + pacienteBDD.TABLE_PACIENTE
			+ " (" + pacienteBDD.COL_ID_PACI + "), " + COL_ID_ESTADO_MARCA
			+ " INTEGER NOT NULL REFERENCES " + estadoMarcacaoBDD.TABLE_ESTMARC
			+ " (" + estadoMarcacaoBDD.COL_ID_ESTMARC + "), " + COL_TIPO_MARCA
			+ " TEXT NOT NULL, " + COL_HORA_MARCA + " TEXT NOT NULL, "
			+ COL_DATA_MARCA + " TEXT NOT NULL, " + COL_LONG_MARCA + " REAL, "
			+ COL_LAT_MARCA + " REAL, " + COL_NOME_LOCAL_MARCA + " TEXT, "
			+ COL_HORA_SINCRO_MARCA + " TEXT NOT NULL, "
			+ COL_DATA_SINCRO_MARCA + " TEXT NOT NULL);";

	public static String DROP_TABLE_MARCACAO = "DROP TABLE " + TABLE_MARCACAO
			+ ";";

	private SQLiteDatabase bdd;
	private baseDeDadosInterna baseDeDados;
	private Context context;

	public marcacaoBDD() {
	};

	public marcacaoBDD(Context context) {
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

	public long inserirMarcacao(marcacao marcacao)
	{
		long valueResult = 0;
		pacienteBDD paciente = new pacienteBDD(context);
		if(paciente.existePaciente(marcacao.getIdPacienteMarc()) == true)
		{
			estadoMarcacaoBDD estado = new estadoMarcacaoBDD(context);
			if(estado.existeEstadoMarcacao(marcacao.getIdEstadoMarc()) == true)
			{
				open();
				ContentValues value = new ContentValues();
				value.put(COL_ID_PACIENTE_MARCA, marcacao.getIdPacienteMarc());
				value.put(COL_ID_ESTADO_MARCA, marcacao.getIdEstadoMarc());
				value.put(COL_TIPO_MARCA, marcacao.getTipoMarc());
				value.put(COL_HORA_MARCA, marcacao.getHoraMarc());
				value.put(COL_DATA_MARCA, marcacao.getDataMarc());
				value.put(COL_LONG_MARCA, marcacao.getLongitudeMarc());
				value.put(COL_LAT_MARCA, marcacao.getLatitudeMarc());
				value.put(COL_NOME_LOCAL_MARCA, marcacao.getLocalMarc());
				value.put(COL_HORA_MARCA, marcacao.getHoraSincroMarc());
				value.put(COL_DATA_MARCA, marcacao.getDataSincroMarc());
				
			}
			else
			{
				valueResult = -1;
			}
		}
		else
		{
			valueResult = -1;
		}
		return valueResult;
	}

	public String getDropTableMarcacao() {
		return DROP_TABLE_MARCACAO;
	}

	public String getCreateTableMarcacao() {
		return CREATE_TABLE_MARCACAO;
	}
	
	
	
}
