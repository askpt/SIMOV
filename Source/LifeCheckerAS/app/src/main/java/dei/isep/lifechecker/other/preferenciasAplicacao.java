package dei.isep.lifechecker.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Diogo on 15-12-2014.
 */
public class preferenciasAplicacao {

    Context context;
    SharedPreferences preference;

    public preferenciasAplicacao(Context context)
    {
        this.context = context;
        preference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setTipoUser(int tipo)
    {
        preference.edit().putInt("tipoConfiguracao", tipo).commit();
    }

    public int getTipoUser()
    {
        int tipoUser = preference.getInt("tipoConfiguracao", 0);
        return tipoUser;
    }

    public void setCalibracao(double valor)
    {
        String valorDoub = String.valueOf(valor);
        preference.edit().putString("calibracao",valorDoub ).commit();
    }

    public double getCalibracao()
    {
        String valorCalib = preference.getString("calibracao","0");
        double valorResult = Double.parseDouble(valorCalib);
        return  valorResult;
    }

    public void setAlarmeMicroNocturno(boolean valor)
    {
        preference.edit().putBoolean("microNoturno", valor).commit();
    }

    public boolean getAlarmeMicroNocturno()
    {
        return  preference.getBoolean("microNoturno",false);
    }

    public void setDBMaxNoturno(double valor)
    {
        String valorDoub = String.valueOf(valor);
        preference.edit().putString("dbMaxNot",valorDoub ).commit();
    }

    public double getDBMaxNoturno()
    {
        String valorCalib = preference.getString("dbMaxNot","0");
        double valorResult = Double.parseDouble(valorCalib);
        return  valorResult;
    }
}
