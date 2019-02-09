package com.example.prescriptionhandling;

import com.example.prescriptionhandling.labsdata.LabData;
import com.example.prescriptionhandling.medicinesdata.MedData;

import java.util.List;

public class ValueHandler {
    private static final ValueHandler ourInstance = new ValueHandler();

    public static ValueHandler getInstance() {
        return ourInstance;
    }

    private ValueHandler() {
    }

    private List<MedData> medDataList;
    private List<LabData> labDataList;

    public List<MedData> getMedDataList() {
        return medDataList;
    }

    public void setMedDataList(List<MedData> medDataList) {
        this.medDataList = medDataList;
    }

    public List<LabData> getLabDataList() {
        return labDataList;
    }

    public void setLabDataList(List<LabData> labDataList) {
        this.labDataList = labDataList;
    }
}
