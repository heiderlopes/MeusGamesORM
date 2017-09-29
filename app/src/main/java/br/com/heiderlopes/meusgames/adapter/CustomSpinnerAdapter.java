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

public class CustomSpinnerAdapter<T extends ItemCustomSpinner> extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private List<T> lista;

    public CustomSpinnerAdapter(Context context, List<T> lista) {
        this.lista = lista;
        this.context = context;
    }


    public int getCount() {
        return lista.size();
    }

    public Object getItem(int i) {
        return lista.get(i);
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
        txt.setText(lista.get(position).getDescricaoSpinner());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 32, 16, 32);
        txt.setTextSize(16);
        txt.setText(lista.get(i).getDescricaoSpinner());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }
}