package br.com.heiderlopes.meusgames.dao;

import com.activeandroid.query.Select;

import java.util.List;

import br.com.heiderlopes.meusgames.model.Game;

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
                .where("titulo LIKE ?", new String[]{'%' + titulo+ '%'})
                .orderBy("titulo ASC")
                .execute();
    }
}
