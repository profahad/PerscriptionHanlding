package com.example.prescriptionhandling;

public class Medicine {
    private long id;
    private String name;
    private String quantity;
    private MedData medData;


    public Medicine() {
        this.id = 0;
        this.name = "";
        this.quantity = "";
    }

    public Medicine(long id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public MedData getMedData() {
        return medData;
    }

    public void setMedData(MedData medData) {
        this.medData = medData;
    }
}
