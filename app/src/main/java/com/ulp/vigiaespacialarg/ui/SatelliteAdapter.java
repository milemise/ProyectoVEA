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

public class SatelliteAdapter extends RecyclerView.Adapter<SatelliteAdapter.SatelliteViewHolder> {

    private List<Satellite> satellites;

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

        // Simulación de Telemetría: Variación aleatoria de +/- 0.02 km/s
        double telemetryNoise = (Math.random() * 0.04) - 0.02;
        double displayVel = sat.getVelocity() + telemetryNoise;

        holder.tvName.setText(sat.getName().toUpperCase());
        holder.tvDetails.setText(String.format(Locale.US,
                "Altitud: %.0f km | Telemetría Vel: %.3f km/s",
                sat.getHeight(), displayVel));

        holder.tvStatus.setText(sat.getStatus());

        // Alerta visual de seguridad
        if (sat.getStatus().contains("ALERTA") || sat.getStatus().contains("FUERA")) {
            holder.tvStatus.setTextColor(0xFFFF4444); // Rojo
        } else {
            holder.tvStatus.setTextColor(0xFF00FF41); // Verde Terminal
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