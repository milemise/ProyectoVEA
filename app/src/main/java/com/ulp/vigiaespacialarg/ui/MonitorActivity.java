package com.ulp.vigiaespacialarg.ui;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ulp.vigiaespacialarg.R;
import com.ulp.vigiaespacialarg.database.AppDatabase;
import com.ulp.vigiaespacialarg.model.Satellite;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        // 1. Vincular componentes de la interfaz
        RecyclerView rv = findViewById(R.id.rv_satellites);
        Button btnBack = findViewById(R.id.btn_back_monitor);

        // 2. Obtener datos de la base de datos Room
        AppDatabase db = AppDatabase.getInstance(this);
        List<Satellite> satelliteList = db.satelliteDao().getAllSatellites();

        // 3. Configurar el RecyclerView con el Adapter
        SatelliteAdapter adapter = new SatelliteAdapter(satelliteList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        // 4. Configurar botón de salida
        btnBack.setOnClickListener(v -> finish());
    }
}