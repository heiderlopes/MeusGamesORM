package br.com.heiderlopes.meusgames;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import br.com.heiderlopes.meusgames.adapter.CustomSpinnerAdapter;
import br.com.heiderlopes.meusgames.adapter.GameAdapter;
import br.com.heiderlopes.meusgames.dao.GameDAO;
import br.com.heiderlopes.meusgames.dao.GeneroDAO;
import br.com.heiderlopes.meusgames.model.Game;
import br.com.heiderlopes.meusgames.model.Genero;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMeusGames;
    private GameAdapter mAdapter;
    private FloatingActionMenu fMenu;

    private Genero generoSelecionado;

    private GameDAO gameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fMenu = (FloatingActionMenu) findViewById(R.id.fMenu);
        gameDao = new GameDAO();
        inicializaLista();
        carregaMeusGames();
    }

    private void inicializaLista() {
        rvMeusGames = (RecyclerView) findViewById(R.id.rvMeusGames);
        mAdapter = new GameAdapter(this, new ArrayList<Game>());
        rvMeusGames.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMeusGames.setItemAnimator(new DefaultItemAnimator());
        rvMeusGames.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvMeusGames.setAdapter(mAdapter);
    }

    private void carregaMeusGames() {
        mAdapter.addAll(gameDao.findAll());
        mAdapter.notifyDataSetChanged();
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_apagar:
                mAdapter.getGameSelected().delete();
                mAdapter.removeGameSelected();
                Toast.makeText(this, "Game apagado com sucesso", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_editar:
                dialogGame(mAdapter.getGameSelected());
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void novoGame(View v) {
        fMenu.close(true);
        dialogGame(new Game());
    }

    public void novoGenero(View v) {
        fMenu.close(true);
        dialogGenero();
    }

    // Abre o dialog para adicionar um novo game na lista e no banco
    private void dialogGame(final Game game) {
        final boolean isInsert = game.getId() == null ? true : false;
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.new_game);

        dialog.setTitle("Novo Game");

        final EditText etTitulo = (EditText)dialog.findViewById(R.id.etTitulo);

        Spinner spinnerGenero = (Spinner)dialog.findViewById(R.id.spGenero);

        initGeneroSpinner(spinnerGenero, game.getGenero());

        etTitulo.setText(game.getTitulo());

        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setTitulo(etTitulo.getText().toString());
                game.setGenero(generoSelecionado);
                game.save();

                if (isInsert)
                    mAdapter.add(game);

                mAdapter.notifyDataSetChanged();

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Dado gravado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btCancelar = (Button) dialog.findViewById(R.id.btCancelar);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //Inicializa o nosso spinner carregando os dados do banco de dados
    private void initGeneroSpinner(Spinner spinner, Genero genero) {
        GeneroDAO generoDAO = new GeneroDAO();

        List<Genero> generos = generoDAO.findAll();

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, generos);
        spinner.setAdapter(customSpinnerAdapter);

        spinner.setSelection(generos.indexOf(genero));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generoSelecionado = (Genero) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void dialogGenero() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_genero);
        dialog.setTitle("Novo Genero");
        final EditText etGenero = (EditText)dialog.findViewById(R.id.etGenero);
        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genero genero = new Genero();
                genero.setDescricao(etGenero.getText().toString());
                genero.save();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Dado gravado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btCancelar = (Button) dialog.findViewById(R.id.btCancelar);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
