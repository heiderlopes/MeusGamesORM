package br.com.heiderlopes.meusgames;

import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import br.com.heiderlopes.meusgames.adapter.CustomSpinnerAdapter;
import br.com.heiderlopes.meusgames.adapter.GameAdapter;
import br.com.heiderlopes.meusgames.dao.GameDAO;
import br.com.heiderlopes.meusgames.dao.GeneroDAO;
import br.com.heiderlopes.meusgames.dao.PlataformaDAO;
import br.com.heiderlopes.meusgames.model.Game;
import br.com.heiderlopes.meusgames.model.Genero;
import br.com.heiderlopes.meusgames.model.Plataforma;

public class MainActivity extends AppCompatActivity {


    private GameAdapter mAdapter;
    private FloatingActionMenu fMenu;

    private Genero generoSelecionado;
    private Plataforma plataformaSelecionada;

    private GameDAO gameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fMenu = (FloatingActionMenu) findViewById(R.id.fMenu);
        gameDao = new GameDAO();
        setupToolbar();
        inicializaLista(new ArrayList<Game>());
        carregaMeusGames();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void inicializaLista(List<Game> games) {
        RecyclerView rvMeusGames = (RecyclerView) findViewById(R.id.rvMeusGames);
        mAdapter = new GameAdapter(this, games);
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
                Toast.makeText(this, R.string.label_apagar_sucesso, Toast.LENGTH_SHORT).show();
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

    public void novaPlataforma(View v) {
        fMenu.close(true);
        dialogPlataforma();
    }

    // Abre o dialog para adicionar um novo game na lista e no banco
    private void dialogGame(final Game game) {
        final boolean isInsert = game.getId() == null;
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.new_game);

        dialog.setTitle(R.string.label_novo_game);

        final TextInputLayout etTitulo = (TextInputLayout) dialog.findViewById(R.id.inputTitulo);

        Spinner spGenero = (Spinner)dialog.findViewById(R.id.spGenero);

        Spinner spPlataforma = (Spinner)dialog.findViewById(R.id.spPlataforma);

        initGeneroSpinner(spGenero, game.getGenero());

        initPlataformaSpinner(spPlataforma, game.getPlataforma());

        etTitulo.getEditText().setText(game.getTitulo());

        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitulo.getEditText().getText().toString();
                if(titulo.trim().equals("")){
                    etTitulo.setError(getString(R.string.label_campo_obrigatorio));
                } else {
                    etTitulo.setErrorEnabled(false);
                    game.setTitulo(etTitulo.getEditText().getText().toString());
                    game.setGenero(generoSelecionado);
                    game.setPlataforma(plataformaSelecionada);
                    game.save();

                    if (isInsert)
                        mAdapter.add(game);

                    mAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, R.string.label_gravado_com_sucesso, Toast.LENGTH_SHORT).show();
                }

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

    private void initPlataformaSpinner(Spinner spinner, Plataforma plataforma) {
        PlataformaDAO plataformaDAO = new PlataformaDAO();

        List<Plataforma> plataformas = plataformaDAO.findAll();

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, plataformas);
        spinner.setAdapter(customSpinnerAdapter);

        spinner.setSelection(plataformas.indexOf(plataforma));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                plataformaSelecionada = (Plataforma) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void dialogGenero() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_genero);
        dialog.setTitle(R.string.label_novo_genero);
        final TextInputLayout inputGenero = (TextInputLayout) dialog.findViewById(R.id.inputGenero);
        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sGenero = inputGenero.getEditText().getText().toString();
                if(sGenero.trim().equals("") ) {
                    inputGenero.setError(getString(R.string.mensagem_erro_genero_nao_preenchido));
                } else {
                    inputGenero.setErrorEnabled(false);
                    Genero genero = new Genero();
                    genero.setDescricao(sGenero);
                    genero.save();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, R.string.label_gravado_com_sucesso, Toast.LENGTH_SHORT).show();
                }
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

    private void dialogPlataforma() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_plataforma);
        dialog.setTitle(R.string.label_nova_plataforma);

        final TextInputLayout inputCodigoPlataforma = (TextInputLayout)dialog.findViewById(R.id.inputCodigoPlataforma);
        final TextInputLayout inputDescricaoPlataforma = (TextInputLayout)dialog.findViewById(R.id.inputDescricaoPlataforma);

        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = inputDescricaoPlataforma.getEditText().getText().toString();
                String sigla = inputCodigoPlataforma.getEditText().getText().toString();

                inputCodigoPlataforma.setError(getString(R.string.label_campo_obrigatorio));
                inputCodigoPlataforma.setErrorEnabled(sigla.trim().equals("") ? true : false);

                inputDescricaoPlataforma.setError(getString(R.string.label_campo_obrigatorio));
                inputDescricaoPlataforma.setErrorEnabled(descricao.trim().equals("") ? true : false);

                if(!sigla.trim().equals("") && !descricao.trim().equals("")) {
                    Plataforma plataforma = new Plataforma();
                    plataforma.setDescricao(descricao);
                    plataforma.setSigla(sigla);
                    plataforma.save();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, R.string.label_gravado_com_sucesso, Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Game> newGames = gameDao.findAllFields(newText);
                inicializaLista(newGames);
                return true;
            }
        });
        return true;
    }
}
