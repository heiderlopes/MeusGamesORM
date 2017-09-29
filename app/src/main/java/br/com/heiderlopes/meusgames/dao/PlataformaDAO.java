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
        p.setSigla("XBOXONE");
        p.save();

        p = new Plataforma();
        p.setDescricao("X-Box 360");
        p.setSigla("XBOX360");
        p.save();

        p = new Plataforma();
        p.setDescricao("Playstation 4");
        p.setSigla("PS4");
        p.save();

        p = new Plataforma();
        p.setDescricao("Playstation 3");
        p.setSigla("PS3");
        p.save();

        p = new Plataforma();
        p.setDescricao("Playstation 2");
        p.setSigla("PS2");
        p.save();

        p = new Plataforma();
        p.setDescricao("Playstation 1");
        p.setSigla("PS1");
        p.save();

        p = new Plataforma();
        p.setDescricao("Super Nintendo");
        p.setSigla("SNES");
        p.save();

        p = new Plataforma();
        p.setDescricao("Mega Drive III");
        p.setSigla("MEGADRIVE");
        p.save();

        p = new Plataforma();
        p.setDescricao("Atari");
        p.setSigla("ATARI");
        p.save();

        p = new Plataforma();
        p.setDescricao("PC");
        p.setSigla("PC");
        p.save();
    }

}
