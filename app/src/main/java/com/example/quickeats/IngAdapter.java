package com.example.quickeats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IngAdapter extends RecyclerView.Adapter<IngAdapter.ViewHolder> implements Filterable {

    View view;

    Context context;
    ArrayList<String> arrayList;
    List<String> ingList;

    IngListener ingListener;

    ArrayList<String> arrayList_0 = new ArrayList<>();

    public IngAdapter(Context context, ArrayList<String> arrayList, IngListener ingListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.ingListener = ingListener;
        this.ingList = new ArrayList<>(arrayList);
    }

    public View getView() {
        return view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.rv_layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (arrayList != null && arrayList.size() > 0) {
            holder.check_box.setText(arrayList.get(position));
            holder.check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.check_box.isChecked()) {
                        arrayList_0.add(arrayList.get(position));
                    } else {
                        arrayList_0.remove(arrayList.get(position));
                    }
                    ingListener.onIngChange(arrayList_0);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter () {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {


            List<String> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(ingList);
            } else {
                for (String ingredient: ingList) {
                    if (ingredient.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(ingredient);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ingList.clear();
            ingList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox check_box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_box = itemView.findViewById(R.id.check_box);
        }
    }
}
