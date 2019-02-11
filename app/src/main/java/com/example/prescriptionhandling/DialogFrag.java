package com.example.prescriptionhandling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prescriptionhandling.labsdata.LabData;
import com.example.prescriptionhandling.labsdata.LabsActivity;
import com.example.prescriptionhandling.medicinesdata.MedData;
import com.example.prescriptionhandling.medicinesdata.MedicinesActivity;
import com.example.prescriptionhandling.perscriptiondialog.labs.LabTest;
import com.example.prescriptionhandling.perscriptiondialog.labs.LabsAdapter;
import com.example.prescriptionhandling.perscriptiondialog.labs.LabsItemListener;
import com.example.prescriptionhandling.perscriptiondialog.medicines.Medicine;
import com.example.prescriptionhandling.perscriptiondialog.medicines.MedicineItemListener;
import com.example.prescriptionhandling.perscriptiondialog.medicines.MedicinesAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.prescriptionhandling.Utils.hideKeyboard;

public class DialogFrag extends DialogFragment {

    private AppCompatActivity context;
    private List<Medicine> medicineList;
    private List<LabTest> labTestList;
    private MedicinesAdapter medicinesAdapter;
    private LabsAdapter labsAdapter;
    private View.OnClickListener clickListener;

    public DialogFrag() { }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.WideDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container, false);
        initView(v);
        return v;
    }

    private void initView(View view) {

        this.medicineList = new ArrayList<>();
        this.labTestList = new ArrayList<>();
        this.medicinesAdapter = new MedicinesAdapter(getActivity(), this.medicineList);
        this.labsAdapter = new LabsAdapter(getActivity(), this.labTestList);

        final RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView mRecyclerViewLabs = view.findViewById(R.id.recyclerViewLabs);
        Button mButtonSubmit = view.findViewById(R.id.buttonSubmit);
        Button mButtonNewMed = view.findViewById(R.id.buttonNewMed);
        Button mButtonNewLab = view.findViewById(R.id.buttonNewLab);
        ImageButton mCloseLab = view.findViewById(R.id.buttonClose);
        final ConstraintLayout mLabsLayout = view.findViewById(R.id.labLayout);
        TextView mLabViewButton = view.findViewById(R.id.buttonAddLabTestLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(medicinesAdapter);

        mRecyclerViewLabs.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewLabs.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewLabs.setAdapter(labsAdapter);

        medicinesAdapter.setListener(new MedicineItemListener() {
            @Override
            public void onMedicineNameClickListener(int pos) {
                hideKeyboard(mRecyclerView);
                Intent intent = new Intent(getActivity(), MedicinesActivity.class);
                intent.putExtra("pos", pos);
                startActivityForResult(intent, 100);
            }

            @Override
            public void onDeleteItem(int pos) {
                medicineList.remove(pos);
                medicinesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onMedicineQuantityChanged(int pos, String quantity) {
                medicineList.get(pos).setQuantity(quantity);
            }
        });

        labsAdapter.setListener(new LabsItemListener() {
            @Override
            public void onLabsNameClickListener(int pos) {
                hideKeyboard(mRecyclerView.getContext());
                Intent intent = new Intent(getActivity(), LabsActivity.class);
                intent.putExtra("pos", pos);
                startActivityForResult(intent, 200);
            }
            @Override
            public void onDeleteItem(int pos) {
                labTestList.remove(pos);
                labsAdapter.notifyDataSetChanged();
            }
        });

        mButtonNewMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineList.add(new Medicine());
                medicinesAdapter.notifyDataSetChanged();
            }
        });
        mLabViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLabsLayout.setVisibility(View.VISIBLE);
            }
        });

        mButtonNewLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labTestList.add(new LabTest());
                labsAdapter.notifyDataSetChanged();
            }
        });

        mCloseLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labTestList.clear();
                labsAdapter.notifyDataSetChanged();
                mLabsLayout.setVisibility(View.GONE);
            }
        });
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validMedicines()) {
                    Toast.makeText(getActivity(), "Please fillup all medicines data", Toast.LENGTH_SHORT).show();
                    return;
                } else if (hasDuplicatedItems(true)){
                    Toast.makeText(getActivity(), "Please first remove dublicated from medicines", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (validLabTests()) {
                    Toast.makeText(getActivity(), "Please fillup all labs data", Toast.LENGTH_SHORT).show();
                    return;
                } else if (hasDuplicatedItems(false)){
                    Toast.makeText(getActivity(), "Please first remove dublicated from lab tests", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getActivity(), "All data are valid", Toast.LENGTH_SHORT).show();
                if (hasItems(medicineList) && hasItems(labTestList)) {
                    Toast.makeText(getActivity(), "Both prodcuts are in prescription", Toast.LENGTH_SHORT).show();
                } else if (hasItems(medicineList)) {
                    Toast.makeText(getActivity(), "Only Medicines are referred", Toast.LENGTH_SHORT).show();
                } else if (hasItems(labTestList)) {
                    Toast.makeText(getActivity(), "Only Lab Test are referred", Toast.LENGTH_SHORT).show();
                }
                clickListener.onClick(v);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("pos", 0);
                MedData medData = (MedData) data.getSerializableExtra("medicine");
                medicineList.get(pos).setMedData(medData);
                medicineList.get(pos).setName(medData.getName());
                medicinesAdapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {

            }
        } else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("pos", 0);
                LabData labTest = (LabData) data.getSerializableExtra("lab");
                labTestList.get(pos).setLabData(labTest);
                labTestList.get(pos).setName(labTest.getName());
                labsAdapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private boolean hasItems(List<?> arrayList) {
        return arrayList.size() > 0;
    }

    private boolean validMedicines() {

        boolean validMed = false;

        for (Medicine medicine : medicineList) {
            if (medicine.getName().trim().isEmpty() || medicine.getQuantity().trim().isEmpty()) {
                validMed = true;
                break;
            }
        }

        return validMed;

    }

    private boolean validLabTests() {

        boolean validLab = false;

        for (LabTest lab : labTestList) {
            if (lab.getName().trim().isEmpty()) {
                validLab = true;
                break;
            }
        }

        return validLab;

    }

    private boolean hasDuplicatedItems(boolean isMedicine) {
        boolean has = false;
        if (isMedicine) {
            Set set = new TreeSet<Medicine>(new Comparator<Medicine>() {
                @Override
                public int compare(Medicine o1, Medicine o2) {
                    if(o1.equals(o2)){
                        return 0;
                    }
                    return 1;
                }
            });
            set.addAll(medicineList);
            if (set.size() != medicineList.size())
                has = true;
        } else {
            Set set = new TreeSet<LabTest>(new Comparator<LabTest>() {
                @Override
                public int compare(LabTest o1, LabTest o2) {
                    if(o1.equals(o2)){
                        return 0;
                    }
                    return 1;
                }
            });
            set.addAll(labTestList);
            if (set.size() != labTestList.size())
                has = true;
        }

        return has;
    }
}
