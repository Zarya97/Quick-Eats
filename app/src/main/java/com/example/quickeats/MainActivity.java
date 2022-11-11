package com.example.quickeats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements IngListener {

    RecyclerView recycler_view;
    IngAdapter adapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.filledButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowArray(onIngChange(arrayList).toString());
            }
        });
        recycler_view = findViewById(R.id.recycler_view);
        setRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for Ingredient");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //adapter.getFilter().filter(newText);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<String> getIngData() {
        arrayList = new ArrayList<>();
        arrayList.add("Tomato");
        arrayList.add("Beef");
        arrayList.add("Carrot");
        arrayList.add("Apple");
        arrayList.add("Kale");
        arrayList.add("Chicken");
        arrayList.add("Milk");
        return arrayList;
    }

    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IngAdapter(this, getIngData(), this);
        recycler_view.setAdapter(adapter);
    }


    public ArrayList<String> onIngChange(ArrayList<String> arrayList) {
        ArrayList<String> items = new ArrayList<>();
        for(String selected: arrayList) {
            items.addAll(Collections.singleton(selected));
        }
        return items;
    }
    private void ShowArray(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}