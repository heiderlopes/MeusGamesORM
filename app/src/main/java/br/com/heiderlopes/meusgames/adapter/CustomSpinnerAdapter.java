package br.com.heiderlopes.meusgames.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.heiderlopes.meusgames.model.Genero;


public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private List<Genero> generos;

    public CustomSpinnerAdapter(Context context, List<Genero> generos) {
        this.generos = generos;
        this.context = context;
    }


    public int getCount() {
        return generos.size();
    }

    public Object getItem(int i) {
        return generos.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(generos.get(position).getDescricao());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 32, 16, 32);
        txt.setTextSize(16);
        txt.setText(generos.get(i).getDescricao());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }
}