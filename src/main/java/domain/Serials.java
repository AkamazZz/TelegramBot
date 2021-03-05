package domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Serials {
    private Document document;
    private Elements elements;
    public String GetSerial() {
        try {
            document = Jsoup.connect("https://www.imdb.com/list/ls056638991/").get();
        }catch(IOException e){
            e.printStackTrace();
            return "Error";
        }
        Elements title = document.select("h3.lister-item-header > a");
       // Elements Description = document.select("p.");
        String s = "We recommend you to watch" +"\n" + "-------------------------------------------" + "\n";
        int i = 1;
        int random = (int) ((Math.random()*99)+1);
        for (Element element : title) {
            if(i==random) {
              s += element.text() + "\n" + "-------------------------------------------" +"\n";
                break;
            }
            ++i;
        }
        return s;


    }
}
