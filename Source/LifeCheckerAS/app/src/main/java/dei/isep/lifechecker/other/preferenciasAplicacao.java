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
}
