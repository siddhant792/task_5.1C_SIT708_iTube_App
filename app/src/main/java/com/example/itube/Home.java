package com.example.itube;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    EditText editTextUrl;
    Button buttonMyPlaylist, buttonPlay, buttonAddToPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonMyPlaylist = findViewById(R.id.buttonMyPlaylist);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonAddToPlaylist = findViewById(R.id.buttonAddToPlaylist);
        buttonMyPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, MyPlaylist.class);
                startActivity(i);
            }
        });
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextUrl.getText().toString();
                if(url.isEmpty()) {
                    Toast.makeText(Home.this, "Please enter the url", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(Home.this, Player.class);
                    i.putExtra("url", url);
                    startActivity(i);
                }
            }
        });
        buttonAddToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextUrl.getText().toString();
                if(url.isEmpty()) {
                    Toast.makeText(Home.this, "Please enter the url", Toast.LENGTH_SHORT).show();
                }else {
                    insertURL(url);
                }
            }
        });
    }

    public void insertURL(String url) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_URL, url);

        db.insert(DatabaseHelper.TABLE_URLS, null, values);
        db.close();
        Toast.makeText(Home.this, "Added to Playlist", Toast.LENGTH_SHORT).show();
    }
}