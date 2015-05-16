package plugins;

import bot.Bot;
import bot.utils.BotUtils;
import bot.utils.Message;
import rx.functions.Action1;

public class Echo {

    public static Action1 create(final Bot bot) {
        return new Action1<String>() {
            public void call(String s) {
                if (s.contains("PRIVMSG")) {
                    Message message = BotUtils.getMessage(s);
                    if (message.channel.equalsIgnoreCase(bot.nick)) {
                        System.out.println("oh my god no thank you");
                    }
                    bot.send("PRIVMSG " + message.channel + " " + message.message);
                }
            }
        };
    }

}
