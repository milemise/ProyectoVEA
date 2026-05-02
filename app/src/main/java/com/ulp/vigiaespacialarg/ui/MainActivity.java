package com.ulp.vigiaespacialarg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ulp.vigiaespacialarg.R;
import com.ulp.vigiaespacialarg.database.AppDatabase;
import com.ulp.vigiaespacialarg.model.Satellite;
import com.ulp.vigiaespacialarg.security.SecurityLogger;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private AppDatabase db;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SecurityLogger.addLog("SYSTEM", "Protocolo VIGIA v2.0 Online.");
        setupFormulario();
    }

    private void setupFormulario() {
        EditText etName = findViewById(R.id.et_name);
        EditText etHeight = findViewById(R.id.et_height);
        Button btnSave = findViewById(R.id.btn_save);
        tvResult = findViewById(R.id.tv_result);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String heightStr = etHeight.getText().toString().trim();

            if (!name.isEmpty() && !heightStr.isEmpty()) {
                // Ciberseguridad: Bloqueo de Nombres Reservados
                if (name.equalsIgnoreCase("admin") || name.equalsIgnoreCase("root")) {
                    SecurityLogger.addLog("SECURITY_BREACH", "Intento de registro no autorizado: " + name);
                    Toast.makeText(this, "ERROR: PRIVILEGIOS INSUFICIENTES", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    double height = Double.parseDouble(heightStr);
                    Satellite sat = new Satellite(name, height);
                    db.satelliteDao().insert(sat);

                    SecurityLogger.addLog("DATA_SYNC", "Misión sincronizada: " + name);

                    tvResult.setText("ID: " + name + "\nStatus: " + sat.getStatus());
                    etName.setText("");
                    etHeight.setText("");
                } catch (Exception e) {
                    SecurityLogger.addLog("ERROR", "Falla en procesamiento de datos.");
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_monitor) {
            startActivity(new Intent(this, MonitorActivity.class));
        } else if (id == R.id.nav_security) {
            startActivity(new Intent(this, SecurityActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}