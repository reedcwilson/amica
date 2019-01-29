package com.reedcwilson.amica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.reedcwilson.amica.extra.IntentStrings.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
//        Toast.makeText(this, "This is a test", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DisplayContactActivity.class);
        EditText editText = (EditText) findViewById(R.id.nameText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
