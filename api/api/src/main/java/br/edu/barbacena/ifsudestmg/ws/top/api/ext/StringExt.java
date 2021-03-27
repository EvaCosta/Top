package br.edu.barbacena.ifsudestmg.ws.top.api.ext;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class StringExt {

    private StringExt() {
        // prevents instantiation
    }

    public static double toDouble(String str) throws ParseException {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        return nf.parse(str).doubleValue();
    }
}
