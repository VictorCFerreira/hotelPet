package com.hotelPet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

public class Pessoa {
    private ObjectId id;
    private String nome;
    private String telefone;
    //Outros dados serão add posteriormente


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    
    
    public Pessoa criarId(){
        setId(new ObjectId());
        return this;
    }
    
//    public Pessoa addDisciplina(Pessoa aluno, Disciplina disciplina){
//        List<Disciplina> disciplinas = aluno.getDisciplina();
//        disciplinas.add(disciplina);
//        aluno.setDisciplina(disciplinas);
//        return aluno;
//    }
}
