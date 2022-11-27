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
    ArrayList<String> getArrayList;
    ArrayList<String> ingList;
    IngListener ingListener;


    ArrayList<String> arrayList_0 = new ArrayList<>();

    public IngAdapter(Context context, ArrayList<String> arrayList, IngListener ingListener) {
        this.context = context;
        this.getArrayList = arrayList;
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
        if (getArrayList != null && getArrayList.size() > 0) {
            holder.check_box.setText((CharSequence) getArrayList.get(position));
            holder.check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.check_box.isChecked()) {
                        arrayList_0.add(getArrayList.get(position));
                    } else {
                        arrayList_0.remove(getArrayList.get(position));
                    }
                    ingListener.onIngChange(arrayList_0);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return getArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    final Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {


            FilterResults filterResults = new FilterResults();
            if (charSequence.toString().isEmpty()) {
                filterResults.values = ingList;
                filterResults.count = ingList.size();
            } else {
                String searchIng = charSequence.toString().toLowerCase();
                List<String> filtered = new ArrayList<>();
                for (String ingredient : ingList) {
                    if (ingredient.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filtered.add(ingredient);
                    }
                }
                filterResults.values = filtered;
                filterResults.count = filtered.size();
            }


            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            getArrayList.clear();
            getArrayList.addAll((Collection<? extends String>) filterResults.values);
            ingListener.onIngChange(arrayList_0);
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
