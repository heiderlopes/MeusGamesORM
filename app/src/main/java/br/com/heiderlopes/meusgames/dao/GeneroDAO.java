package br.com.heiderlopes.meusgames.dao;

import com.activeandroid.query.Select;
import java.util.List;

import br.com.heiderlopes.meusgames.model.Genero;


public class GeneroDAO {

    public List<Genero> findAll() {
        return new Select()
                .from(Genero.class)
                .orderBy("descricao ASC")
                .execute();
    }

    public void seed() {
        Genero genero = new Genero();
        genero.setDescricao("Ação");
        genero.save();

        genero = new Genero();
        genero.setDescricao("Aventura");
        genero.save();

        genero = new Genero();
        genero.setDescricao("Estratégia");
        genero.save();

        genero = new Genero();
        genero.setDescricao("RPG");
        genero.save();

        genero = new Genero();
        genero.setDescricao("Esporte");
        genero.save();

        genero = new Genero();
        genero.setDescricao("Corrida");
        genero.save();

        genero = new Genero();
        genero.setDescricao("Simulação");
        genero.save();

        genero = new Genero();
        genero.setDescricao("Outro");
        genero.save();
    }
}