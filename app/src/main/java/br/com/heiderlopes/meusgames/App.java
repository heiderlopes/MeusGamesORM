package br.com.heiderlopes.meusgames;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.facebook.stetho.Stetho;

import br.com.heiderlopes.meusgames.dao.GeneroDAO;
import br.com.heiderlopes.meusgames.dao.PlataformaDAO;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        ActiveAndroid.initialize(this);

        seedGenero();
        seedPlataforma();

    }

    private void seedGenero() {
        GeneroDAO generoDAO = new GeneroDAO();
        if(generoDAO.findAll().size() == 0)
            generoDAO.seed();
    }

    private void seedPlataforma() {
        PlataformaDAO plataformaDAO = new PlataformaDAO();
        if(plataformaDAO.findAll().size() == 0)
            plataformaDAO.seed();
    }
}
