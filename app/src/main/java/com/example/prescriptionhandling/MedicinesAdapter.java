package com.example.prescriptionhandling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MedicineVH> {

    private Context context;
    private List<Medicine> list;
    private MedicineItemListener listener;

    public MedicinesAdapter(@NonNull Context context, List<Medicine> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(MedicineItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MedicineVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicine_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineVH holder, final int i) {
        Medicine medicine = list.get(i);
        holder.mMedicinceView.setPosId((i + 1));
        holder.mMedicinceView.setMedicineName(medicine.getName());
        holder.mMedicinceView.setQuantity(medicine.getQuantity());
        holder.mMedicinceView.setMedicineClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onMedicineNameClickListener(i);
            }
        });
        holder.mMedicinceView.setDeleteButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onDeleteItem(i);
            }
        });
        holder.mMedicinceView.setTextWatcher(new OnTextChangedListener() {
            @Override
            public void OnTextChanged(String quantity) {
                if (listener != null)
                    listener.onMedicineQuantityChanged(i, quantity);
            }

            @Override
            public void afterTextChanged(String quantity) {
                if (listener != null)
                    listener.onMedicineQuantityChanged(i, quantity);
            }
        });

        if (i == (getItemCount() - 1))
            holder.mMedicinceView.setNameFocus();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class MedicineVH extends RecyclerView.ViewHolder {

        View view;
        PerscriptionItemView mMedicinceView;

        MedicineVH(View itemView) {
            super(itemView);
            this.view = itemView;
            this.mMedicinceView = itemView.findViewById(R.id.medicinceView);
        }
    }
}

interface MedicineItemListener {
    void onMedicineNameClickListener(int pos);

    void onDeleteItem(int pos);

    void onMedicineQuantityChanged(int pos, String quantity);
}
