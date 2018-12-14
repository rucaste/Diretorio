package estruturas;

import java.io.Serializable;

public class WordSearchMessage implements Serializable {

    private String nome;

    public WordSearchMessage(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

}
