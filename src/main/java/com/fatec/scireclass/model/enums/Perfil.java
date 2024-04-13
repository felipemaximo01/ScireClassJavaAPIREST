package com.fatec.scireclass.model.enums;

public enum Perfil {
    
    ALUNO(1),
    INSTITUICAO(2),
    PROFESSOR(3),
    ADMINISTRADOR(4);

    private int code;

    private Perfil(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static Perfil valueOf(int code){
        for (Perfil value : Perfil.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Código de OrderStatus inválido");
    }
}
