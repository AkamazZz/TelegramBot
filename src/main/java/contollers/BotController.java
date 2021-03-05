package contollers;

import domain.Covid;
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
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    @Override
    public void onUpdateReceived(Update update) { // Obtain update after which fix log in ID
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
        KeyboardRow keyboardFourthRow = new KeyboardRow();
        KeyboardRow keyboardFifthRow = new KeyboardRow();
        KeyboardRow keyboardSixRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if(msg.equals("Menu") || msg.equals("/start")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Covid");
            keyboardFirstRow.add("Anime");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose:";
        }
        if(msg.equals("Covid")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Statistics");
            keyboardFirstRow.add("About you");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose:";
        }
        if(msg.equals("Serials")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Find");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose:";
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
            if(!msg.equals("Menu") || !msg.equals("Statistics") || !msg.equals("About you")) {
                addSymptom(msg); // copy that data to database
                return "Thank for your vote";
            }else{
                return "Don't get sick!";
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
    }

    @Override
    public String getBotToken() { // API KEY
        return "1607286204:AAEh9fCGXDsJdHlyFBs6az_bw8dhLd7MvcY";
    }
}


