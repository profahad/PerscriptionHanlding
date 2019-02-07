package com.example.prescriptionhandling.perscriptiondialog.labs;


import com.example.prescriptionhandling.labsdata.LabData;
import com.example.prescriptionhandling.perscriptiondialog.medicines.Medicine;

public class LabTest {
    private long id;
    private String name;
    private LabData labData;


    public LabTest() {
        this.id = 0;
        this.name = "";
    }

    public LabTest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabData getLabData() {
        return labData;
    }

    public void setLabData(LabData labData) {
        this.labData = labData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LabTest) {
            return ((LabTest) obj).getName().equals(getName());
        }
        return false;
    }
}
