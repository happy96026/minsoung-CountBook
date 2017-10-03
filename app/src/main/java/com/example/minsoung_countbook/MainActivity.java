package com.example.minsoung_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * MainActivity class is responsible for displaying all the counter's
 * value. It also allows for adding a Counter object
 * to the array list. Although delete load and save is not initiated in
 * MainActivity, the class contains the logic for deleting a counter from
 * array list and saving information to a file using GSON.
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Counter>  counters = new ArrayList<Counter>();
    private CounterAdapter counterAdapter;
    private ListView       counterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        counterAdapter = new CounterAdapter(this, counters);
        counterListView = (ListView) findViewById(R.id.counter_list);
        counterListView.setAdapter(counterAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                onclick_add();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if ((requestCode == 1)&&(resultCode == Activity.RESULT_OK)) {
            Counter counter = (Counter) intent.getSerializableExtra("Counter");
            if (counter.getName() == null) {
                int index = counter.getIndex();
                counters.remove(index);
                reindex_counters(counters, index);
            } else {
                counters.set(counter.getIndex(), counter);
            }
            saveInFile();
            counterAdapter.notifyDataSetChanged();
        }
    }

    private void reindex_counters(ArrayList<Counter> counters, int index) {
        Counter counter;

        for (int i = index; i < counters.size(); i++) {
            counter = counters.get(i);
            counter.setIndex(counter.getIndex() - 1);
        }
    }

    private void onclick_add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.add_dialog, null);
        final EditText name    = (EditText) view.findViewById(R.id.et_name);
        final EditText value   = (EditText) view.findViewById(R.id.et_value);
        final EditText comment = (EditText) view.findViewById(R.id.et_comments);
        final Button   ok      = (Button)   view.findViewById(R.id.ok);
        final Button   cancel  = (Button)   view.findViewById(R.id.cancel);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (name.getText().toString().equals("") || value.getText().toString().equals("")) {
                    ok.setEnabled(false);
                } else {
                    ok.setEnabled(true);
                }
            }
        };
        name .addTextChangedListener(textWatcher);
        value.addTextChangedListener(textWatcher);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Counter counter = new Counter(name.getText().toString(),
                                              Integer.parseInt(value.getText().toString()),
                                              comment.getText().toString(), counters.size());
                counters.add(counter);
                saveInFile();
                dialog.dismiss();
                counterAdapter.notifyDataSetChanged();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


}
