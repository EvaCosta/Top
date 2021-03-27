package br.edu.barbacena.ifsudestmg.ws.top.api.controller;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Top;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * class for the generation of fictitious data
 */
public class Mock {

    private final TopController topController;

    public Mock(TopController topController) {
        this.topController = topController;
    }

    public Top mockData() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("mock_data.txt");
        var data = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String line;

            while ((line = br.readLine()) != null) {
                data.add(line.trim().replaceAll(" +", " "));
            }

            return topController.extractData(data);
        } catch (Exception e) {
            return null;
        }
    }
}
