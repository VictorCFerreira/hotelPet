package com.hotelPet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;

public class Pet {
    private ObjectId id;
    private String nome;
    private String tipo;
    private String especie;
    private String hospedagemAtual;
    private Pessoa Tutor;
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String curso) {
        this.tipo = curso;
    }

    public Pessoa getTutor() {
        return Tutor;
    }

    public void setTutor(Pessoa Tutor) {
        this.Tutor = Tutor;
    }
    
    public Pet criarId(){
        setId(new ObjectId());
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pet other = (Pet) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.especie, other.especie)) {
            return false;
        }
        if (!Objects.equals(this.hospedagemAtual, other.hospedagemAtual)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.Tutor, other.Tutor);
    }
    
    
    
    
    
//    public Pessoa addDisciplina(Pessoa aluno, Disciplina disciplina){
//        List<Disciplina> disciplinas = aluno.getDisciplina();
//        disciplinas.add(disciplina);
//        aluno.setDisciplina(disciplinas);
//        return aluno;
//    }

    public String getHospedagemAtual() {
        return hospedagemAtual;
    }

    public void setHospedagemAtual(String hospedagemAtual) {
        this.hospedagemAtual = hospedagemAtual;
    }
}
