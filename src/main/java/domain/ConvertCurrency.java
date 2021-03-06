package domain;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConvertCurrency { // converter of currency using json and gson libraries

    public String convertCurrency(String value, String currency, String currency2) throws IOException {
        String url_str = "https://api.exchangeratesapi.io/latest?base=" + currency; // url to currency from which we will convert
        System.out.println(url_str);
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection(); // connecting with that url
        request.connect();
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        String multiplier = jsonobj.getAsJsonObject("rates").get(currency2).toString();
        float result = Float.parseFloat(value) * Float.parseFloat(multiplier);
        return String.valueOf(result);
    }
}
