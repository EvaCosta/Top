package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Top {

    private LocalTime time;
    private String upTime;
    private int numberOfUsers;

    private int numberOfTasks;
    private int runningTasks;
    private int sleepingTasks;
    private int stoppedTasks;
    private int zombieTasks;

    private CpuUsage cpuUsage = new CpuUsage();
    private LoadAverage loadAverage = new LoadAverage();
    private SwapMemory swapMemory = new SwapMemory();
    private SystemMemory systemMemory = new SystemMemory();
    private ArrayList<Process> processes = new ArrayList<>();

    public boolean addProcess(Process process) {
        return processes.add(process);
    }

    public boolean addProcesses(Collection<Process> processes){
        return this.processes.addAll(processes);
    }
}
