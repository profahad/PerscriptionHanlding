package com.example.prescriptionhandling.labsdata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.prescriptionhandling.medicinesdata.MedData;

import java.util.ArrayList;
import java.util.List;

public class LabsDataAdapter extends RecyclerView.Adapter<LabsDataAdapter.LabItemView>  implements Filterable {

    private Context context;
    private List<LabData> list;
    private List<LabData> originalList;
    private OnLabTestSelectListener listener;


    public LabsDataAdapter(Context context, List<LabData> list) {
        this.context = context;
        this.originalList = list;
        this.list = list;
    }

    public void setOnLabTestSelectListener(OnLabTestSelectListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LabItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LabItemView(LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LabItemView holder, final int i) {
        holder.textView.setText(this.list.get(i).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onLabTestSelected(list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LabItemView extends RecyclerView.ViewHolder {

        private TextView textView;

        LabItemView(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(android.R.id.text1);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<LabData>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<LabData> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    private List<LabData> getFilteredResults(String constraint) {
        List<LabData> results = new ArrayList<>();

        for (LabData item : originalList) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }
}

interface OnLabTestSelectListener {
    void onLabTestSelected(LabData data);
}
