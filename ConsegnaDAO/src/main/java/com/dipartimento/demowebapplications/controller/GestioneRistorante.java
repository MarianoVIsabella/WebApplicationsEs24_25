package com.dipartimento.demowebapplications.controller;

import com.dipartimento.demowebapplications.model.Ristorante;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestioneRistorante {
    @GetMapping("/addRistorante")
    public String aggiungiRistorante(@RequestBody Ristorante ristorante) {
        System.out.println("ristorante: " + ristorante.getNome());
        return "OK";
    }


}
