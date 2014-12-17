package dei.isep.lifechecker;

import android.location.Address;

import java.util.List;

/**
 * Created by Diogo on 17-12-2014.
 */
public interface interfaceAgendarMarcacao {
    public abstract void listaCoordenadas(int codigo, List<Address> conteudo);
}
