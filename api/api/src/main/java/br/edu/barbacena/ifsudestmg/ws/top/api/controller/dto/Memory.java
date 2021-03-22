package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Memory {

    private double total;
    private double free;
    private double used;
}

