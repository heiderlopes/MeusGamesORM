package br.com.heiderlopes.meusgames.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import br.com.heiderlopes.meusgames.adapter.ItemCustomSpinner;

@Table(name = "plataforma")
public class Plataforma extends Model implements ItemCustomSpinner {

    @Column(name = "descricao")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getDescricaoSpinner() {
        return descricao;
    }
}