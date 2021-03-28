package br.edu.barbacena.ifsudestmg.ws.top.api.controller;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Process;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.*;
import br.edu.barbacena.ifsudestmg.ws.top.api.ext.StringExt;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopController {

    public Top runTopCommand() {
        try {
            return extractData(runTop());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Extracts the {@link Top} data from a list of String
     *
     * @param data list containing {@link Top} data as string
     * @return the extracted data
     * @throws RuntimeException if something abnormal happens
     */
    @org.jetbrains.annotations.NotNull
    public Top extractData(@NonNull List<String> data) throws RuntimeException {
        if (data.isEmpty())
            throw new RuntimeException("No data received");

        Top top = new Top();

        try {
            extractBasicInfo(top, data.get(0));
            extractTasksData(top, data.get(1));
            extractCpuUsageData(top, data.get(2));
            extractFourthLine(top, data.get(3));
            extractFifthLine(top, data.get(4));

            top.addProcesses(extractProcessData(data));
            return top;
        } catch (Exception e) {
            System.out.println(String.join("\n", data));
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Runs the top command and retrieves the output as a {@link List} of {@link String}
     *
     * @return List containing the command output.
     * @throws IOException          if an I/O Exception occurs
     * @throws InterruptedException if the current thread is
     *                              {@linkplain Thread#interrupt() interrupted} by another
     *                              thread while it is waiting, then the wait is ended and
     *                              an {@link InterruptedException} is thrown.
     */
    private List<String> runTop() throws IOException, InterruptedException {

        final ArrayList<String> data = new ArrayList<>();

        java.lang.Process process = Runtime.getRuntime().exec("top -b -n 1");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line.trim().replaceAll(" +", " "));
            }
        }

        process.waitFor();
        if (process.exitValue() != 0) {
            throw new RuntimeException("Execution finished with exit value: " + process.exitValue());
        }

        return data;
    }

    /**
     * Extract the base high level info about the system. Such as time, up time and number of users.
     *
     * @param top  Top variable where the extracted data must be stored.
     * @param line the string containing the data to be extracted.
     */
    private void extractBasicInfo(Top top, String line) {
        Matcher matcher;

        // this line has no patterns that we can use to split variables. So we use regex to retrieve the data.

        //region Load Average
        String strLoadAverage = line.substring(line.indexOf("load average:") + "load average:".length()).trim();
        String[] splLoadAverage = strLoadAverage.split(", ");

        LoadAverage loadAverage = new LoadAverage();
        try {
            loadAverage.setOneMinute(StringExt.toDouble(splLoadAverage[0]));
            loadAverage.setFiveMinutes(StringExt.toDouble(splLoadAverage[1]));
            loadAverage.setFifteenMinutes(StringExt.toDouble(splLoadAverage[2]));
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve load average info");
        }
        top.setLoadAverage(loadAverage);
        //endregion

        //region Time
        Pattern timePattern = Pattern.compile("([0-1]?[0-9]|[2][0-3]):([0-5][0-9])(:[0-5][0-9])?");
        matcher = timePattern.matcher(line);
        if (!matcher.find())
            throw new RuntimeException("Unable to retrieve time info");

        top.setTime(LocalTime.parse(matcher.group(0)));

        //endregion

        //region Users
        Pattern usersPattern = Pattern.compile("(?<=\\s|^)\\d+(?=\\s|$) user");
        matcher = usersPattern.matcher(line);
        if (!matcher.find())
            throw new RuntimeException("Unable to retrieve users count info");

        top.setNumberOfUsers(Integer.parseInt(matcher.group().split(" ")[0]));
        //endregion

        top.setUpTime(line.substring(line.indexOf("up") + 2, line.indexOf(", ")).trim());
    }

    /**
     * Extracts the number of tasks info by states.
     *
     * @param top  Top variable where the extracted data must be stored.
     * @param line the string containing the data to be extracted.
     */
    private void extractTasksData(Top top, String line) {
        try {

            // the data we looking for is separated by ', ', so we can simple remove the line descriptor and split
            // the remaining string.

            line = line.substring(line.indexOf(":") + 1).trim();
            String[] split = line.split(", ");

            top.setNumberOfTasks(Integer.parseInt(split[0].split(" ")[0]));
            top.setRunningTasks(Integer.parseInt(split[1].split(" ")[0]));
            top.setSleepingTasks(Integer.parseInt(split[2].split(" ")[0]));
            top.setStoppedTasks(Integer.parseInt(split[3].split(" ")[0]));
            top.setZombieTasks(Integer.parseInt(split[4].split(" ")[0]));
        } catch (Exception exception) {
            throw new RuntimeException("Unable to retrieve tasks info " + exception.getMessage());
        }
    }

    /**
     * Extracts the cpu performance
     *
     * @param top  Top variable where the extracted data must be stored.
     * @param line the string containing the data to be extracted.
     */
    private void extractCpuUsageData(Top top, String line) {
        try {

            // the data we looking for is separated by ', ', so we can simple remove the line descriptor and split
            // the remaining string.

            line = line.substring(line.indexOf(":") + 1).trim();
            String[] split = line.split(", ");

            CpuUsage cpuUsage = new CpuUsage();

            cpuUsage.setUser(StringExt.toDouble(split[0].split(" ")[0]));
            cpuUsage.setSystem(StringExt.toDouble(split[1].split(" ")[0]));
            cpuUsage.setNiceValue(StringExt.toDouble(split[2].split(" ")[0]));
            cpuUsage.setIdleProcess(StringExt.toDouble(split[3].split(" ")[0]));
            cpuUsage.setIoWait(StringExt.toDouble(split[4].split(" ")[0]));
            cpuUsage.setHardwareInterrupts(StringExt.toDouble(split[5].split(" ")[0]));
            cpuUsage.setSoftwareInterrupts(StringExt.toDouble(split[6].split(" ")[0]));
            cpuUsage.setStealTime(StringExt.toDouble(split[6].split(" ")[0]));
            top.setCpuUsage(cpuUsage);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Unable to retrieve cpu usage info" + e.getMessage());
        }
    }

    /**
     * Extracts the system memory data
     *
     * @param top  Top variable where the extracted data must be stored.
     * @param line the string containing the data to be extracted.
     */
    private void extractFourthLine(Top top, String line) {
        try {
            // the data we looking for is separated by ', ', so we can simple remove the line descriptor and split
            // the remaining string.

            SystemMemory memory = new SystemMemory();
            memory.setMeasureUnity(line.substring(0, line.indexOf(" ")));

            line = line.substring(line.indexOf(":") + 1).trim();
            String[] split = line.split(", ");

            memory.setTotal(StringExt.toDouble(split[0].split(" ")[0]));
            memory.setFree(StringExt.toDouble(split[1].split(" ")[0]));
            memory.setUsed(StringExt.toDouble(split[2].split(" ")[0]));
            memory.setBufferCache(StringExt.toDouble(split[3].split(" ")[0]));

            top.setSystemMemory(memory);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve system memory info" + e.getMessage());
        }
    }

    private void extractFifthLine(Top top, String line) {
        try {
            SwapMemory memory = new SwapMemory();
            memory.setMeasureUnity(line.substring(0, line.indexOf(" ")));

            // the data we looking for is separated by ', ', so we can simple remove the line descriptor and split
            // the remaining string.

            // In some cases, the separator of the used memory comes as "." not as ",", so we need to replace
            // it before splitting
            line = line.substring(line.indexOf(":") + 1).trim().replace("used.", "used,");
            String[] split = line.split(", ");

            memory.setTotal(StringExt.toDouble(split[0].split(" ")[0]));
            memory.setFree(StringExt.toDouble(split[1].split(" ")[0]));
            memory.setUsed(StringExt.toDouble(split[2].split(" ")[0]));
            memory.setAvailable(StringExt.toDouble(split[3].split(" ")[0]));

            top.setSwapMemory(memory);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve swap memory info" + e.getMessage());
        }
    }

    /**
     * Extracts all @{@link Process} data received from the top command.
     *
     * @param data the result of the top command.
     * @return a ArrayList containing all {@link Process} extracted.
     */
    private ArrayList<Process> extractProcessData(List<String> data) {
        ArrayList<Process> processes = new ArrayList<>();

//        first 5 lines is reserved for top header (time, user, cpu, memory, etc.)
//        line 6 should be empty (separator)
//        line 7 is the process table headers
//        So the data that matters to us is from line 8 onwards and, to extract it, we must use the layout below:
//
//        PID USER PR NI VIRT RES SHR S %CPU %MEM TIME+ COMMAND
        for (int i = 7; i < data.size(); i++) {
            try {
                String[] split = data.get(i).split(" ");

                Process process = new Process();

                process.setId(Integer.parseInt(split[0]));
                process.setUser(split[1]);
                process.setPriority(split[2]);
                process.setNiceLevel(Integer.parseInt(split[3]));
                process.setVirtualMemoryUsed(StringExt.toDouble(split[4]));

                process.setResidentMemoryUsed(StringExt.toDouble(split[5]));
                process.setShareableMemory(StringExt.toDouble(split[6]));
                process.setState(split[7].charAt(0));
                process.setPercentageOfCpuUsed(StringExt.toDouble(split[8]));
                process.setPercentageOfMemoryUsed(StringExt.toDouble(split[9]));
                process.setUpTime(split[10]);
                process.setCommand(split[11]);

                processes.add(process);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " at line " + i);
            }
        }

        return processes;
    }
}