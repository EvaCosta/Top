package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.*;
import org.jetbrains.annotations.Nullable;

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

    public void addProcesses(Collection<Process> processes) {
        this.processes.addAll(processes);
    }

    @Nullable
    public Process getProcessById(int id) {
        return processes.stream()
                .filter(p -> p.getId() == id).findAny().orElse(null);
    }
}
