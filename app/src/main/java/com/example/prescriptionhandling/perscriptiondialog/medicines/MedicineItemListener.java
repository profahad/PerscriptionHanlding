package com.example.prescriptionhandling.perscriptiondialog.medicines;

public interface MedicineItemListener {
    void onMedicineNameClickListener(int pos);
    void onDeleteItem(int pos);
    void onMedicineQuantityChanged(int pos, String quantity);
}
