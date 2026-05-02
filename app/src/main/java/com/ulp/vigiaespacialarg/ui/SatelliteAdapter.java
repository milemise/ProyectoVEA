package com.ulp.vigiaespacialarg.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ulp.vigiaespacialarg.R;
import com.ulp.vigiaespacialarg.model.Satellite;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SatelliteAdapter extends RecyclerView.Adapter<SatelliteAdapter.SatelliteViewHolder> {

    private List<Satellite> satellites;
    private Random random = new Random();

    public SatelliteAdapter(List<Satellite> satellites) {
        this.satellites = satellites;
    }

    @NonNull
    @Override
    public SatelliteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_satellite, parent, false);
        return new SatelliteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SatelliteViewHolder holder, int position) {
        Satellite sat = satellites.get(position);

        // 1. Telemetría de Velocidad (lo que ya teníamos)
        double telemetryNoise = (Math.random() * 0.04) - 0.02;
        double displayVel = sat.getVelocity() + telemetryNoise;

        // 2. NUEVO: Simulación de Salud en Tiempo Real
        int battery = 80 + random.nextInt(21); // Entre 80% y 100%
        int temp = 20 + random.nextInt(15);    // Entre 20°C y 35°C
        int signal = 90 + random.nextInt(11);  // Entre 90% y 100%

        holder.tvName.setText(sat.getName().toUpperCase());

        // Formateamos los detalles para incluir la salud
        String detalles = String.format(Locale.US,
                "Altitud: %.0f km | Vel: %.3f km/s\n" +
                        "SISTEMAS: 🔋%d%%  |  🌡️%d°C  |  📡%d%%",
                sat.getHeight(), displayVel, battery, temp, signal);

        holder.tvDetails.setText(detalles);
        holder.tvStatus.setText(sat.getStatus());

        if (sat.getStatus().contains("ALERTA") || sat.getStatus().contains("FUERA")) {
            holder.tvStatus.setTextColor(0xFFFF4444);
        } else {
            holder.tvStatus.setTextColor(0xFF00FF41);
        }
    }

    @Override
    public int getItemCount() {
        return satellites.size();
    }

    static class SatelliteViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails, tvStatus;

        public SatelliteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_name);
            tvDetails = itemView.findViewById(R.id.item_details);
            tvStatus = itemView.findViewById(R.id.item_status);
        }
    }
}