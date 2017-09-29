package br.com.heiderlopes.meusgames.dao;

import com.activeandroid.query.Select;

import java.util.List;

import br.com.heiderlopes.meusgames.model.Game;
import br.com.heiderlopes.meusgames.model.Genero;
import br.com.heiderlopes.meusgames.model.Plataforma;

public class GameDAO {
    public List<Game> findAll() {
        return new Select()
                .from(Game.class)
                .orderBy("titulo ASC")
                .execute();
    }

    public List<Game> findBy(String titulo) {
        return new Select()
                .from(Game.class)
                .where("titulo LIKE ?", new String('%' + titulo+ '%'))
                .orderBy("titulo ASC")
                .execute();
    }

    public List<Game> findAllFields(String q) {

        return new Select()
                .from(Game.class)
                .innerJoin(Plataforma.class)
                .on("game.plataforma_id = plataforma.id")
                .innerJoin(Genero.class)
                .on("game.genero_id = genero.id")
                .where("plataforma.descricao LIKE ?", new String('%' + q + '%'))
                .or("plataforma.sigla LIKE ?", new String('%' + q + '%'))
                .or("game.titulo LIKE ?", new String('%' + q + '%'))
                .or("genero.descricao LIKE ?", new String('%' + q + '%'))
                .orderBy("descricao ASC")
                .execute();
    }
}
