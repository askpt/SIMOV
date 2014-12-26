package dei.isep.lifechecker.other;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dei.isep.lifechecker.interfaceAgendarMarcacao;

/**
 * Created by Diogo on 17-12-2014.
 */
public class geoCoderToLatLongAsyncTask extends AsyncTask<Void, Void, Void>{

    interfaceAgendarMarcacao interfaceAgendarMarcacao;
    String endereco;
    Locale locale;
    Context context;

    public geoCoderToLatLongAsyncTask(String endereco, Locale locale, Context context)
    {
        this.endereco = endereco;
        this.locale = locale;
        this.context = context;
    }

    public void setOnResultListener(interfaceAgendarMarcacao interfaceAgendarMarcacao){
        if(interfaceAgendarMarcacao != null)
        {
            this.interfaceAgendarMarcacao = interfaceAgendarMarcacao;
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        Geocoder gc =new Geocoder(context, locale);
        List<Address> enderecosLista = null;
        try
        {
            enderecosLista = gc.getFromLocationName(endereco, 1);
            if(enderecosLista.size() == 0)
            {
                interfaceAgendarMarcacao.listaCoordenadas(2,enderecosLista);
            }
            interfaceAgendarMarcacao.listaCoordenadas(1,enderecosLista);
        }
        catch(IOException e)
        {
            interfaceAgendarMarcacao.listaCoordenadas(2,enderecosLista);
            e.printStackTrace();
        }



        return null;
    }
}
