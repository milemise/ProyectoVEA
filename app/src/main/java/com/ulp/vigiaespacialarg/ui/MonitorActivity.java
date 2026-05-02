package com.ulp.vigiaespacialarg.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ulp.vigiaespacialarg.R;
import com.ulp.vigiaespacialarg.database.AppDatabase;
import com.ulp.vigiaespacialarg.model.IssResponse;
import com.ulp.vigiaespacialarg.model.Satellite;
import com.ulp.vigiaespacialarg.database.IssService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonitorActivity extends AppCompatActivity {

    private SatelliteAdapter adapter;
    private List<Satellite> displayList = new ArrayList<>();
    private Handler handler = new Handler();
    private IssService issService;
    private AppDatabase db;
    private Runnable updateRunnable;

    private final int ISS_ID = 25544;
    private final int SAOCOM_1A_ID = 43641;
    private final int SAOCOM_1B_ID = 46265;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        db = AppDatabase.getInstance(this);
        RecyclerView rv = findViewById(R.id.rv_satellites);
        Button btnBack = findViewById(R.id.btn_back_monitor);
        btnBack.setOnClickListener(v -> finish());

        adapter = new SatelliteAdapter(displayList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wheretheiss.at/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        issService = retrofit.create(IssService.class);

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                refreshAllData();
                handler.postDelayed(this, 5000);
            }
        };
    }

    private void refreshAllData() {
        List<Satellite> locales = db.satelliteDao().getAllSatellites();
        for (Satellite s : locales) {
            updateOrAdd(s);
        }

        fetchWithBackup(ISS_ID, "ISS (ESTACIÓN ESPACIAL)");
        fetchWithBackup(SAOCOM_1A_ID, "SAOCOM-1A (ARG)");
        fetchWithBackup(SAOCOM_1B_ID, "SAOCOM-1B (ARG)");
    }

    private void fetchWithBackup(int id, String name) {
        issService.getSatelliteData(id).enqueue(new Callback<IssResponse>() {
            @Override
            public void onResponse(Call<IssResponse> call, Response<IssResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IssResponse data = response.body();
                    Satellite s = new Satellite(name, data.getAltitude());
                    s.setVelocity(data.getVelocity());
                    updateOrAdd(s);
                } else {
                    handleFailure(name);
                }
            }

            @Override
            public void onFailure(Call<IssResponse> call, Throwable t) {
                handleFailure(name);
            }
        });
    }

    private void handleFailure(String name) {
        Satellite backup = new Satellite(name, 0);
        backup.setStatus("MODO RESPALDO - SIN CONEXIÓN");
        updateOrAdd(backup);
    }

    private synchronized void updateOrAdd(Satellite newSat) {
        boolean found = false;
        for (int i = 0; i < displayList.size(); i++) {
            if (displayList.get(i).getName().equals(newSat.getName())) {
                displayList.set(i, newSat);
                found = true;
                break;
            }
        }
        if (!found) {
            displayList.add(newSat);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(updateRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRunnable);
    }
}