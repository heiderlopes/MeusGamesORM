package br.com.heiderlopes.meusgames.dao;

import com.activeandroid.query.Select;

import java.util.List;
import br.com.heiderlopes.meusgames.model.Plataforma;

public class PlataformaDAO {

    public List<Plataforma> findAll() {
        return new Select()
                .from(Plataforma.class)
                .orderBy("descricao ASC")
                .execute();
    }

    public void seed() {
        Plataforma p = new Plataforma();
        p.setDescricao("X-Box One");
        p.save();

        p = new Plataforma();
        p.setDescricao("PS4");
        p.save();
    }

}
