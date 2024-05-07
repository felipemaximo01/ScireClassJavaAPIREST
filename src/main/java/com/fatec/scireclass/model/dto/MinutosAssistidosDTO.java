package com.fatec.scireclass.model.dto;

import java.util.Date;

public class MinutosAssistidosDTO {

    private String id;

    private Date data;
    private Integer minutos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }
}
