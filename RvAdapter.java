package com.example.behealthy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    List<Patient> patients;

    public RvAdapter(List<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
      holder.tv_name.setText(patients.get(position).getName());
      holder.tv_bmi.setText("BMI : "+String.valueOf(patients.get(position).getBmi()));
      holder.tv_height.setText("Height : "+String.valueOf(patients.get(position).getHeight()));
      holder.tv_weight.setText("Weight : "+String.valueOf(patients.get(position).getWeight()));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_bmi,tv_height,tv_weight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.fm_name);
            tv_bmi=itemView.findViewById(R.id.fm_bmi);
            tv_height=itemView.findViewById(R.id.fm_height);
            tv_weight=itemView.findViewById(R.id.fm_weight);
        }
    }
}
