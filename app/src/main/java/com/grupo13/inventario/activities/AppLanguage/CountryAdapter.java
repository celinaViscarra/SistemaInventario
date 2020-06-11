package com.grupo13.inventario.activities.AppLanguage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grupo13.inventario.R;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<CountryItem> {
    public CountryAdapter(Context context, ArrayList<CountryItem> countryList){
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lenguaje_spinner_row, parent, false);
        }
        ImageView imgIdioma = convertView.findViewById(R.id.imgIdioma);
        TextView textView = convertView.findViewById(R.id.txtIdioma);
        CountryItem item = getItem(position);
        if(item != null){
            imgIdioma.setImageResource(item.getBanderaLenguaje());
            textView.setText(item.getNombreLenguaje());
        }
        return convertView;
    }
}
