package com.example.prescriptionhandling.perscriptiondialog.labs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appslandz.prescriptionview.LabView;
import com.appslandz.prescriptionview.MedicineView;
import com.appslandz.prescriptionview.OnTextChangedListener;
import com.example.prescriptionhandling.R;
import com.example.prescriptionhandling.perscriptiondialog.medicines.Medicine;
import com.example.prescriptionhandling.perscriptiondialog.medicines.MedicineItemListener;

import java.util.List;

public class LabsAdapter extends RecyclerView.Adapter<LabsAdapter.LabVH> {

    private List<LabTest> list;
    private LabsItemListener listener;

    public LabsAdapter(@NonNull Context context, List<LabTest> list) {
        this.list = list;
    }

    public void setListener(LabsItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LabVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LabVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lab_row_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LabVH holder, final int i) {
        LabTest labTest = list.get(i);
        holder.mLabView.setPosId((i + 1));
        holder.mLabView.setLabTestName(labTest.getName());
        holder.mLabView.setLabTestClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onLabsNameClickListener(i);
            }
        });
        holder.mLabView.setDeleteButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onDeleteItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class LabVH extends RecyclerView.ViewHolder {

        View view;
        LabView mLabView;

        LabVH(View itemView) {
            super(itemView);
            this.view = itemView;
            this.mLabView = itemView.findViewById(R.id.labView);
        }
    }
}

