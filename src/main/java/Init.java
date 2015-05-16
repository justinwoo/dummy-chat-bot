import bot.Bot;
import plugins.Echo;
import plugins.Printer;

import java.io.IOException;

public class Init {

    public static void main(String[] args) {
        // create my bot
        Bot myBot = new Bot("botsama", "botsama");

        // subscribe to streams with my plugins
        myBot.subscribeToInputStream(Printer.create());
        myBot.subscribeToOutputStream(Printer.create());
        myBot.subscribeToInputStream(Echo.create(myBot));

        // connect to my localhost
        try {
            myBot.connect("localhost", 6667);
        } catch (IOException e) {
            System.err.println("something blew up when connecting...");
            e.printStackTrace();
            System.exit(1);
        }

        // join my channel
        myBot.join("#test");

        // start listening
        try {
            myBot.listen();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            myBot.disconnect();
        }
    }

}
