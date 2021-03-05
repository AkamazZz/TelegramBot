package domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Serials {
    private Document document;
    private Elements elements;
    public void GetSerial() {
        try {
            document = Jsoup.connect("https://www.imdb.com/list/ls056638991/").get();
        }catch(IOException e){
            e.printStackTrace();
        }
        Elements title = document.select("h3.lister-item-header");
        Elements photo = document.select("img.loadlate[src~=(?i)\\.(png|jpe?g|gif)]");
        Elements Description = document.select("div.lister-item-content mode-detail > p.");

        


    }
}
