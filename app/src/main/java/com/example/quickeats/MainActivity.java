package com.example.quickeats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IngListener {

    RecyclerView recycler_view;
    IngAdapter adapter;
    ArrayList<String> ingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Tomato");
        arrayList.add("Broccoli");
        arrayList.add("Eggs");
        arrayList.add("Milk");
        arrayList.add("Chicken");
        arrayList.add("Beef");

        return arrayList;
    }

    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IngAdapter(this, getIngData(), this);
        recycler_view.setAdapter(adapter);
    }


    @Override
    public void onIngChange(ArrayList<String> arrayList) {
        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_SHORT).show();
    }
}