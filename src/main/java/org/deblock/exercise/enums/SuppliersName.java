package org.deblock.exercise.enums;

public enum SuppliersName {
    CRAZYAIR("Crazy Air"),
    TOUGHJET("Tough Jet");

    private String supplierName;

    SuppliersName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }
}
