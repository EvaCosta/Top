package br.edu.barbacena.ifsudestmg.ws.top.api.controller;


import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Process;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.*;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mock {

    public Top mockData() {
        var top = new Top();

        top.setTime(LocalTime.of(10, 29, 5));
        top.setUpTime("25");
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
                "MiB",
                7958.0,
                6418.6,
                610.9,
                928.5));

        top.setSwapMemory(new SwapMemory(
                "MiB",
                1873.3,
                1873.3,
                .0,
                7052.6));

        top.addProcesses(mockProcess());
        return top;
    }

    private Top extractData(@NonNull List<String> data) throws RuntimeException {
        if (data.isEmpty())
            throw new RuntimeException("No data received");

        Top top = new Top();

        try {
            extractFirstLine(data.get(0), top);

            top.addProcesses(mockProcess());
            return top;
        } catch (Exception e) {
            throw new RuntimeException("Invalid data received");
        }
    }

    private boolean extractFirstLine(String s, Top top) {
        String line = s.split("- ")[1];

        // gets the time
        var strTime = line.substring(0, line.indexOf("up")).trim();
        top.setTime(LocalTime.parse(strTime));
        line = line.substring(strTime.length()).trim();

        System.out.println("LIne " + line);

        var strLoadAverage = line.indexOf("load average:");
        System.out.println(line);

//        // gets up time
//        var uptime = line.substring(2, line.indexOf(",")).trim();
//        line = line.substring(line.indexOf(uptime) + uptime.length()).trim();
//        top.setUpTime(uptime);
//
//        System.out.println(top.getUpTime());
//        System.out.println("LIne " + line);

        return true;
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

    /**
     * @param top
     * @param idTopProccess
     * @return referenceProcess from id
     */
    public Process findProcessFromId(Top top, Integer idTopProccess) {

        try {
            return top.getProcesses().stream().filter(p -> p.getId() == idTopProccess).findFirst().orElseGet(null);
        } catch (Exception ex) {
            return new Process();
        }
    }//findProcessFromId
}
