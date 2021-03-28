package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CpuUsage {

    private double user;
    private double system;
    private double idleProcess;
    private double niceValue;
    private double ioWait;
    private double hardwareInterrupts;
    private double softwareInterrupts;
    private double stealTime;
}
