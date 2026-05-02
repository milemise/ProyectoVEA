package com.ulp.vigiaespacialarg.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ulp.vigiaespacialarg.R;
import com.ulp.vigiaespacialarg.security.SecurityLogger;
import java.util.List;

public class SecurityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        TextView tvLogs = findViewById(R.id.tv_logs);
        Button btnBack = findViewById(R.id.btn_back_terminal);

        // Efecto de carga de datos
        StringBuilder sb = new StringBuilder();
        List<String> logs = SecurityLogger.getLogs();

        for (String log : logs) {
            sb.append("> ").append(log).append("\n");
        }

        tvLogs.setText("DESCRIPTANDO PAQUETES DE AUDITORÍA...\n\n" + sb.toString());

        btnBack.setOnClickListener(v -> finish());
    }
}