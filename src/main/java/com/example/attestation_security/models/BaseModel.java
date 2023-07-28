package com.example.attestation_security.models;

public class BaseModel {
    public static boolean isBodyCategoryCorrect(Category category){
        if(category.getTitle()!=null && !category.getTitle().isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isRequestBodyGoodCorrect(Good good){
        if(good.getTitle() != null && !good.getTitle().isEmpty()
                && good.getInfo() != null
                && good.getVendorCode() !=null && !good.getVendorCode().isEmpty()
                && good.getCount()>0 && good.getPrice() > 0){
            return true;
        }
        return false;
    }

    public static boolean ifRequestBodyShipmentSupplyCorrect(Good good) {
        if(good.getGoodId()>0 && good.getCount() > 0){
            return true;
        }
        return false;
    }
}
