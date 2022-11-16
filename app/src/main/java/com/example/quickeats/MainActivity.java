package com.example.quickeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements IngListener{

    RecyclerView recycler_view;
    IngAdapter adapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowArray(onIngChange(adapter.arrayList_0).toString());
            }
        });
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.arrayList_0.clear();
                adapter = null;
                setRecyclerView();
                ShowArray("Selection Cleared");
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
        InputStream is = getResources().openRawResource(R.raw.ingredients);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        try {
            while ((line = reader.readLine()) != null){
                arrayList.add(line);
            }
        }
        catch (IOException e) {
            Log.wtf("MyActivity", "Error reading ingredients", e);
            e.printStackTrace();
        }
        // Debugging Array
        /*arrayList.add("Tomato");
        arrayList.add("Milk");
        arrayList.add("Beef");
        arrayList.add("Chicken");
        arrayList.add("Sauce");
        arrayList.add("Whatever");
        arrayList.add("Powder");
        arrayList.add("Mayo");
        arrayList.add("Spice");*/
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