package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoadAverage {

    private double oneMinute;
    private double fiveMinutes;
    private double fifteenMinutes;
}
