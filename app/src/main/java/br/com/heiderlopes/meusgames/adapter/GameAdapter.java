package br.com.heiderlopes.meusgames.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.heiderlopes.meusgames.R;
import br.com.heiderlopes.meusgames.model.Game;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> games;
    private Activity activity;
    private int lastPositionSelected;

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{

        public TextView tvTitulo, tvGenero;

        public GameViewHolder(View view) {
            super(view);
            tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            tvGenero = (TextView) view.findViewById(R.id.tvGenero);
            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            activity.openContextMenu(view);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            lastPositionSelected = getLayoutPosition();
            menu.setHeaderIcon(R.mipmap.ic_launcher);
            menu.setHeaderTitle(activity.getString(R.string.app_name));
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_lista, menu);
        }
    }

    public int getLastPositionSelected() {
        return lastPositionSelected;
    }

    public GameAdapter(Activity activity, List<Game> games) {
        this.activity = activity;
        this.games = games;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_row, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game movie = games.get(position);
        holder.tvTitulo.setText(movie.getTitulo());
        holder.tvGenero.setText(movie.getGenero().getDescricao());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void add(Game game) {
        games.add(game);
        notifyDataSetChanged();
    }

    public void addAll(List<Game> gamesList) {
        games.addAll(gamesList);
        notifyDataSetChanged();
    }

    public Game getGameSelected() {
        return games.get(lastPositionSelected);
    }

    public void removeGameSelected() {
        games.remove(games.get(lastPositionSelected));
        notifyItemRemoved(lastPositionSelected);
    }
}