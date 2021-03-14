package br.edu.barbacena.ifsudestmg.ws.top.api.controller;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.*;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Process;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Mock {

    public Top mockData() {
        var top = new Top();

        top.setTime(LocalTime.of(10, 29, 5));
        top.setUpTime(25);
        top.setNumberOfUsers(3);
        top.setLoadAverage(new LoadAverage(1.51, .68, .26));

        top.setNumberOfTasks(182);
        top.setRunningTasks(1);
        top.setSleepingTasks(181);
        top.setStoppedTasks(0);
        top.setZombieTasks(0);

        top.setCpuUsage(new CpuUsage(.8,
                0,
                0,
                99.2,
                0,
                0,
                0,
                0));

        top.setSystemMemory(new SystemMemory(
                7958.0,
                6418.6,
                610.9,
                928.5));

        top.setSwapMemory(new SwapMemory(
                1873.3,
                1873.3,
                .0,
                7052.6));

        top.addProcesses(mockProcess());
        return top;
    }

    private ArrayList<Process> mockProcess() {
        ArrayList<Process> processes = new ArrayList<>();
        InputStream is = getClass().getClassLoader().getResourceAsStream("mock_data.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String line;

            int i = 1;

            while ((line = br.readLine()) != null) {
                try {
                    var split = line.split(" ");

                    Process process = new Process();

                    process.setId(Integer.parseInt(split[0]));
                    process.setUser(split[1]);
                    process.setPriority(split[2]);
                    process.setNiceLevel(Integer.parseInt(split[3]));
                    process.setVirtualMemoryUsed(Double.parseDouble(split[4].replace(",", ".")));

                    process.setResidentMemoryUsed(Double.parseDouble(split[5].replace(",", ".")));
                    process.setShareableMemory(Double.parseDouble(split[6].replace(",", ".")));
                    process.setState(split[7].charAt(0));
                    process.setPercentageOfCpuUsed(Double.parseDouble(split[8].replace(",", ".")));
                    process.setPercentageOfMemoryUsed(Double.parseDouble(split[9].replace(",", ".")));
                    process.setUpTime(split[10]);
                    process.setCommand(split[11]);

                    processes.add(process);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage() + " at line " + i);
                } finally {
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return processes;
    }
}
