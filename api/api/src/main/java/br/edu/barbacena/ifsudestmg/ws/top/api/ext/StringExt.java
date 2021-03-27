package br.edu.barbacena.ifsudestmg.ws.top.api.ext;

public class StringExt {

    private StringExt() {
        // prevents instantiation
    }

    public static double toDouble(String str) throws NumberFormatException {
        return Double.parseDouble(str.replace(",", "."));
    }
}
