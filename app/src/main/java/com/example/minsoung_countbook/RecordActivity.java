package com.example.minsoung_countbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * RecordActivity is used to display the information of a single
 * counter. This class also allows the user to edit the information
 * of a counter except for the date and to delete the counter
 * permanently.
 */
public class RecordActivity extends AppCompatActivity {

    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        counter = (Counter)getIntent().getSerializableExtra("Counter");
        setTitle(counter.getName());

        final EditText name          = (EditText) findViewById(R.id.record_name);
        final EditText date          = (EditText) findViewById(R.id.record_date);
        final EditText current_value = (EditText) findViewById(R.id.record_current_value);
        final EditText initial_value = (EditText) findViewById(R.id.record_initial_value);
        final EditText comment       = (EditText) findViewById(R.id.record_comment);
        Button ok     = (Button) findViewById(R.id.record_ok);
        Button cancel = (Button) findViewById(R.id.record_cancel);

        name.setText(counter.getName());
        date.setText(counter.date_toString());
        current_value.setText(Integer.toString(counter.getCurrentValue()));
        initial_value.setText(Integer.toString(counter.getInitialValue()));
        comment.setText(counter.getComment());

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    counter.setName(name.getText().toString());
                    counter.setCurrentValue(Integer.parseInt(current_value.getText().toString()));
                    counter.setInitialValue(Integer.parseInt(initial_value.getText().toString()));
                    counter.setComment(comment.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("Counter", counter);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } catch (NegativeNumber e) {
                    Toast.makeText(RecordActivity.this, "Value cannot be negative!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete:
                Intent intent = new Intent();
                counter.setName(null);
                intent.putExtra("Counter", counter);
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
