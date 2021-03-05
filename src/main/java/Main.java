import contollers.BotController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main{
    public static void main(String[] args) { // class where the bot in initialized and registered in telegram
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new BotController());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}