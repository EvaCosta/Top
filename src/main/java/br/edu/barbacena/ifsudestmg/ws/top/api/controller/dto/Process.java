package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Process {

    private int id;
    private String user;
    private String priority;
    private int niceLevel;
    private double virtualMemoryUsed;
    private double residentMemoryUsed;
    private double shareableMemory;
    private char State;
    private double percentageOfCpuUsed;
    private double percentageOfMemoryUsed;
    private String upTime;
    private String command;
}
