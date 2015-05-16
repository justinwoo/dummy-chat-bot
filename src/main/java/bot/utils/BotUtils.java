package bot.utils;

public class BotUtils {

    public static Message getMessage(String s) {
        String relevantSubstring = s.substring(s.indexOf("PRIVMSG"));
        String[] split = relevantSubstring.split(" ");
        String channel = split[1];
        String string = "";
        for (int i = 2; i < split.length; i++) {
            if (i > 2) {
                string += " ";
            }
            string += split[i];
        }

        return new Message(channel, string);
    }
}
