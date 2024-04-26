package com.example.itube;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyPlaylist extends AppCompatActivity {

    RecyclerView rvMyPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_playlist);
        rvMyPlaylist = findViewById(R.id.rvMyPlaylist);
        List<String> urls = getAllURLs();
        UrlAdapter urlAdapter = new UrlAdapter(urls);
        rvMyPlaylist.setLayoutManager(new LinearLayoutManager(this));
        rvMyPlaylist.setAdapter(urlAdapter);
    }

    public List<String> getAllURLs() {
        List<String> urls = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_URLS,
                new String[]{DatabaseHelper.COLUMN_URL},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL));
                    urls.add(url);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return urls;
    }

    private class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {

        private List<String> urlList;

        public UrlAdapter(List<String> urlList) {
            this.urlList = urlList;
        }

        @NonNull
        @Override
        public UrlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_url, parent, false);
            return new UrlViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UrlViewHolder holder, int position) {
            String url = urlList.get(position);
            holder.bind(url);
        }

        @Override
        public int getItemCount() {
            return urlList.size();
        }

        // ViewHolder class
        public class UrlViewHolder extends RecyclerView.ViewHolder {
            TextView textViewUrl;

            public UrlViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewUrl = itemView.findViewById(R.id.textViewUrl);
            }

            public void bind(String url) {
                textViewUrl.setText(url);
            }
        }
    }

}