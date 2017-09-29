package br.com.heiderlopes.meusgames.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "game")
public class Game extends Model {

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "genero_id")
    private Genero genero;

    @Column(name = "plataforma_id")
    private Plataforma plataforma;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }
}