package com.example.myapplication_13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText editTextData;
    private Button buttonSave, buttonLoad;
    private RecyclerView recyclerView;
    private DataEntryAdapter adapter;
    private List<DataEntry> dataEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextData = findViewById(R.id.editTextData);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);
        recyclerView = findViewById(R.id.recyclerView);
        dataEntries = new ArrayList<>();
        adapter = new DataEntryAdapter(dataEntries);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void saveData() {
        String data = editTextData.getText().toString();
        DataEntry dataEntry = new DataEntry(data);
        dataEntries.add(dataEntry);
        adapter.notifyDataSetChanged();

        // Save to internal storage
        try {
            FileOutputStream fos = openFileOutput("data.txt", MODE_PRIVATE);
            fos.write(dataEntry.getData().getBytes());
            fos.write(dataEntry.getDateTime().getBytes());
            fos.close();
            Toast.makeText(this, "Data saved to internal storage", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save to external storage
        if (isExternalStorageWritable()) {
            File file = new File(getExternalFilesDir(null), "data.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(dataEntry.getData().getBytes());
                fos.write(dataEntry.getDateTime().getBytes());
                fos.close();
                Toast.makeText(this, "Data saved to external storage", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "External storage is not writable", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        // Load from internal storage
        try {
            FileInputStream fis = openFileInput("data.txt");
            int size;
            StringBuilder stringBuilder = new StringBuilder();
            while ((size = fis.read()) != -1) {
                stringBuilder.append((char) size);
            }
            fis.close();
            String[] dataParts = stringBuilder.toString().split(" ");
            for (int i = 0; i < dataParts.length; i += 2) {
                dataEntries.add(new DataEntry(dataParts[i] + " " + dataParts[i + 1]));
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Data loaded from internal storage", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load from external storage
        if (isExternalStorageReadable()) {
            File file = new File(getExternalFilesDir(null), "data.txt");
            try {
                FileInputStream fis = new FileInputStream(file);
                int size;
                StringBuilder stringBuilder = new StringBuilder();
                while ((size = fis.read()) != -1) {
                    stringBuilder.append((char) size);
                }
                fis.close();
                String[] dataParts = stringBuilder.toString().split(" ");
                for (int i = 0; i < dataParts.length; i += 2) {
                    dataEntries.add(new DataEntry(dataParts[i] + " " + dataParts[i + 1]));
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Data loaded from external storage", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "External storage is not readable", Toast.LENGTH_SHORT).show();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
