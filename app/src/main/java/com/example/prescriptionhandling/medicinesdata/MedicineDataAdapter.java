package com.example.prescriptionhandling.medicinesdata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MedicineDataAdapter extends RecyclerView.Adapter<MedicineDataAdapter.MedicineItemView>  implements Filterable {

    private Context context;
    private List<MedData> list;
    private List<MedData> originalList;
    private OnMedicineSelectListener listener;


    public MedicineDataAdapter(Context context, List<MedData> list) {
        this.context = context;
        this.originalList = list;
        this.list = list;
    }

    public void setOnMedicineSelectListener(OnMedicineSelectListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MedicineItemView(LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineItemView holder, final int i) {
        holder.textView.setText(this.list.get(i).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onMedicineSelected(list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MedicineItemView extends RecyclerView.ViewHolder {

        private TextView textView;

        MedicineItemView(@NonNull View itemView) {
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
                list = (List<MedData>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<MedData> filteredResults = null;
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

    private List<MedData> getFilteredResults(String constraint) {
        List<MedData> results = new ArrayList<>();

        for (MedData item : originalList) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }
}
interface OnMedicineSelectListener {
    void onMedicineSelected(MedData data);
}
