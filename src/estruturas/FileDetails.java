package estruturas;

import java.io.Serializable;

public class FileDetails implements Comparable, Serializable {

    private String nome;
    private float tamanho;

    public FileDetails(String nome, float tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public float getTamanho() {
        return tamanho;
    }

    public boolean sameFile(FileDetails fileDetails){
        return fileDetails.getNome().equals(this.nome) && fileDetails.getTamanho() == this.tamanho;
    }

    @Override
    public String toString() {
        return nome + " (" + (int)(tamanho/1024) + " kb)";
    }

    @Override
    public int compareTo(Object o) {
        return this.nome.toLowerCase().compareTo(((FileDetails) o).nome.toLowerCase());
    }

}
