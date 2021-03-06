package contollers;

import domain.Covid;
import domain.Serials;
import domain.Symptom;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import repositories.SymptomRepository;
import java.util.ArrayList;

public class BotController extends TelegramLongPollingBot {
    private long chat_id;

    String lastMessage = ""; // global variable which will be used to check some messages
    SymptomRepository sr = new SymptomRepository(); // object related to symptom
    Covid covid = new Covid();
    Serials serials = new Serials();
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); // keyboard
    @Override
    public void onUpdateReceived(Update update) { // after each message of user update and send message
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(getMessage(update.getMessage().getText()));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try{
            execute(sendMessage);
        } catch(TelegramApiException e){
            e.printStackTrace();
        }
    }
    public String getTopSymptom(){
        return sr.getTopSymptom();
    }
    public Symptom addSymptom(String sympName){
        Symptom symptom = sr.addSymptom(sympName);
        return symptom;
    }
    public String getMessage(String msg){ // method which will works a receiver of message
        SendMessage answerMessage = new SendMessage();
        ArrayList<KeyboardRow> keyboard = new ArrayList<>(); // declairing list in order to create objects which will be exploited as rows of keyboard
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if(msg.equals("Menu") || msg.equals("/start")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Covid");
            keyboardFirstRow.add("Serials");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose:";
        }
        if(msg.equals("Serials")){
            return serials.GetSerial();
        }
       /* if(msg.equals("Currency")){   // For future development
            for(int i=0; i<3;++i){
                switch (i){
                    case 0:
                        String value = getMessage();
                        convert.setValue(value);
                        convert.getValue();
                        break;
                    case 1:
                        String cur = String.valueOf(answerMessage);
                        convert.setCurrency(cur);
                        break;
                    case 2:
                        String cur2 = String.valueOf(answerMessage);
                        convert.setCurrency2(cur2);
                        break;
                }

            }
            return "Input quantity of cash";
        }
        if(msg.equals("Currency")){
            lastMessage = msg;
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("50");
            keyboardFirstRow.add("100");
            keyboardFirstRow.add("200");
            keyboardFirstRow.add("400");
            keyboardSecondRow.add("500");
            keyboardSecondRow.add("1000");
            keyboardSecondRow.add("1500");
            keyboardSecondRow.add("2000");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            for(int i=0; i<3;++i){
                switch (i){
                    case 0:
                        return "Input quantity of cash";
                    case 1:
                        return "From currency";
                    case 2:
                        return "To currency";
                }

            }
            return "Input quantity of cash";
        }
        if(lastMessage.equals("Currency")){
            lastMessage = msg;
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("USD");
            keyboardFirstRow.add("JPY");
            keyboardFirstRow.add("CAD");
            keyboardFirstRow.add("CNY");
            keyboardSecondRow.add("RUB");
            keyboardSecondRow.add("CZK");
            keyboardSecondRow.add("GBP");
            keyboardSecondRow.add("PLN");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "From currency";
        }if(lastMessage.equals("Input quantity of cash")) {
            lastMessage = msg;
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("USD");
            keyboardFirstRow.add("JPY");
            keyboardFirstRow.add("CAD");
            keyboardFirstRow.add("CNY");
            keyboardSecondRow.add("RUB");
            keyboardSecondRow.add("CZK");
            keyboardSecondRow.add("GBP");
            keyboardSecondRow.add("PLN");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "To currency";
        }
            if(lastMessage.equals("Input quantity of cash")){
            convert.setValue(msg);
            return convert.getValue();
        }*/
        if(msg.equals("Covid")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Statistics");
            keyboardFirstRow.add("About you");
            keyboardFirstRow.add("Frequent symptom");
            keyboardSecondRow.add("Menu");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "What do you want to know?";
        }
        if(msg.equals("Frequent symptom")){
            return getTopSymptom(); // since statistic is made we can obtain most popular symptom
        }
        if(msg.equals("About you")){
            lastMessage = msg; // check next inputed answer after about you in order to write data in DB
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Fever");
            keyboardFirstRow.add("Fatigue");
            keyboardFirstRow.add("dry cough");
            keyboardFirstRow.add("Loss of appetite");
            keyboardSecondRow.add("Body aches");
            keyboardSecondRow.add("Shortness of breath");
            keyboardSecondRow.add("Mucus or phlegm");
            keyboardSecondRow.add("Headache");
            keyboardThirdRow.add("Menu");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose on of the symptomes of coronavirus you experienced";
        }
        if(lastMessage.equals("About you")){ // checking whether about you is written then call keyboard and add symptom
            if(!msg.equals("Menu") && !msg.equals("Statistics") && !msg.equals("Among the earth") && !msg.equals("Kazakhstan")  && !msg.equals("Top 10")) {
                addSymptom(msg); // copy that data to database
                return "Thank for your answer";
            }
        }
        if(msg.equals("Statistics")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Among the earth");
            keyboardFirstRow.add("Kazakhstan");
            keyboardFirstRow.add("Top 10");
            keyboardSecondRow.add("Menu");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose:";
        }
        if(msg.equals("Among the earth")){
            return covid.getEarth();
        }
        if(msg.equals("Kazakhstan")){
            return covid.getKZ();
        }
        if(msg.equals("Top 10")){
            return covid.getTop();
        }

        return "Напишите /start";
    }


    @Override
    public String getBotUsername() { // tag name of bot
        return "AkmzZz_bot";
    } // tag of bot

    @Override
    public String getBotToken() { // API KEY
        return "1607286204:AAEh9fCGXDsJdHlyFBs6az_bw8dhLd7MvcY";
    } // api key
}


