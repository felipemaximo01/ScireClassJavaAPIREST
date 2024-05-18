package com.fatec.scireclass.model.dto;

import java.util.ArrayList;
import java.util.List;

public class CursoFilterDTO {
    String nomeCurso;
    List<String> categoriasID = new ArrayList<>();
    String duracao;
    Integer distancia;
    Integer precoMin;
    Integer precoMax;

    public List<String> getCategoriasID() {
        return categoriasID;
    }

    public void setCategoriasID(List<String> categoriasID) {
        this.categoriasID = categoriasID;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public Integer getPrecoMin() {
        return precoMin;
    }

    public void setPrecoMin(Integer precoMin) {
        this.precoMin = precoMin;
    }

    public Integer getPrecoMax() {
        return precoMax;
    }

    public void setPrecoMax(Integer precoMax) {
        this.precoMax = precoMax;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}
