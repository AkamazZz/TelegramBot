package domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Covid {
    private Document document;
    public String getEarth(){
        try {
            document = Jsoup.connect("https://www.worldometers.info/coronavirus").get();
        }catch(IOException e){
            e.printStackTrace();
        }
        return document.title();
    }
    public String getKZ(){ // method to parse statistic about Covid in KZ
        try {
            document = Jsoup.connect("https://www.worldometers.info/coronavirus/country/kazakhstan/").get();
        }catch(IOException e){
            e.printStackTrace();
        }
        return document.title();
    }
    public String getTop(){ // method to parse top 10 countries due to Covid
        try {
            document = Jsoup.connect("https://www.worldometers.info/coronavirus").get();
        }catch(IOException e){
            e.printStackTrace();
        }
        Elements elements = document.getElementsByClass("mt_a");
        String info = elements.text();
        String top = ""; int ind = 0; int num = 1;
        for(int i = 0 ; i < 60 ; i ++){
            if(info.charAt(i) == ' '){
                top+=num + ". " + info.substring(ind , i) + "\n";
                ind = i;
                num++;
            }
        }
        return top;
    }

}
