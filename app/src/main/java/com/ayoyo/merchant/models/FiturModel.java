package com.ayoyo.merchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class FiturModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id_fitur")
    private int idFitur;

    @Expose
    @SerializedName("fitur")
    private String fitur;

    public int getIdFitur() {
        return idFitur;
    }

    public void setIdFitur(int idFitur) {
        this.idFitur = idFitur;
    }

    public String getFitur() {
        return fitur;
    }

    public void setFitur(String fitur) {
        this.fitur = fitur;
    }


}
