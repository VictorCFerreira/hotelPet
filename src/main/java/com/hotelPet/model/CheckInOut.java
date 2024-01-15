/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotelPet.model;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Aluno
 */
public class CheckInOut {
    private ObjectId id;
    private String tipo;
    private ObjectId idPet;
    private String nomePet;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataCheck;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ObjectId getIdPet() {
        return idPet;
    }

    public void setIdPet(ObjectId idPet) {
        this.idPet = idPet;
    }

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public Date getDataCheck() {
        return dataCheck;
    }

    public void setDataCheck(Date dataCheck) {
        this.dataCheck = dataCheck;
    }
    
    public CheckInOut criarId(){
        setId(new ObjectId());
        return this;
    }
    
    
    
    
}
