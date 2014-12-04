package dei.isep.lifechecker;

import android.util.Log;

public class lifeCheckerManager {
	private static lifeCheckerManager _instance;
	
	private lifeCheckerManager(){};
	
	private int idResponsavel;
	

	private String mailResponsavel;
	
	public synchronized static lifeCheckerManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new lifeCheckerManager();
		}
		return _instance;
	}
	
	public int getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(int idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getMailResponsavel() {
		return mailResponsavel;
	}

	public void setMailResponsavel(String mailResponsavel) {
		Log.i("Singleton","SINGGGG " + mailResponsavel);
		this.mailResponsavel = mailResponsavel;
	}

}
