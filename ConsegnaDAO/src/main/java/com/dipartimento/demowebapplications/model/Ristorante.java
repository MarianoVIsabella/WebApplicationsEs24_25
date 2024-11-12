package com.dipartimento.demowebapplications.model;

import lombok.*;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ristorante {

    protected String nome;
    protected String descrizione;
    protected String ubicazione;
    protected List<Piatto> piatti;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUbicazione() {
        return ubicazione;
    }

    public void setUbicazione(String ubicazione) {
        this.ubicazione = ubicazione;
    }


    public List<Piatto> getPiatti() {
        return piatti;
    }

    public void setPiatti(List<Piatto> piatti) {
        this.piatti = piatti;
    }
}
