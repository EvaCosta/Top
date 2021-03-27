package br.edu.barbacena.ifsudestmg.ws.top.api.controller;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.SwapMemory;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.SystemMemory;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Top;
import br.edu.barbacena.ifsudestmg.ws.top.api.ext.StringExt;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TopController {

    public Top runTopCommand() {
        try {
            return extractData(runTop());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @NotNull
    public Top extractData(@NotNull List<String> data) throws RuntimeException {
        if (data.isEmpty())
            throw new RuntimeException("No data received");

        Top top = new Top();

        try {
            // TODO Extract first three lines
            extractFourthLine(top, data.get(3));
            extractFifthLine(top, data.get(4));

            // TODO Extract process data
            return top;
        } catch (Exception e) {
            throw new RuntimeException("Invalid data received");
        }
    }

    private static List<String> runTop() throws IOException, InterruptedException {

        final ArrayList<String> data = new ArrayList<>();

        var process = Runtime.getRuntime().exec("top -b -n 1");

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

    private static void extractFourthLine(Top top, String line) {
        try {
            SystemMemory memory = new SystemMemory();
            memory.setMeasureUnity(line.substring(0, line.indexOf(" ")));

            line = line.substring(line.indexOf(":") + 1).trim();
            var split = line.split(", ");

            memory.setTotal(StringExt.toDouble(split[0].split(" ")[0]));
            memory.setFree(StringExt.toDouble(split[1].split(" ")[0]));
            memory.setUsed(StringExt.toDouble(split[2].split(" ")[0]));
            memory.setBufferCache(StringExt.toDouble(split[3].split(" ")[0]));

            top.setSystemMemory(memory);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve system memory info");
        }
    }

    private static void extractFifthLine(Top top, String line) {
        try {
            SwapMemory memory = new SwapMemory();
            memory.setMeasureUnity(line.substring(0, line.indexOf(" ")));

            line = line.substring(line.indexOf(":") + 1).trim().replace("used.", "used,");
            var split = line.split(", ");

            memory.setTotal(StringExt.toDouble(split[0].split(" ")[0]));
            memory.setFree(StringExt.toDouble(split[1].split(" ")[0]));
            memory.setUsed(StringExt.toDouble(split[2].split(" ")[0]));
            memory.setAvailable(StringExt.toDouble(split[3].split(" ")[0]));

            top.setSwapMemory(memory);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve swap memory info");
        }
    }
}