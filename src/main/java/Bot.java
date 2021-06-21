import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.security.auth.login.LoginException;

public class Bot {

    ListenerAdapter[] listenerAdapters = new ListenerAdapter[]{new GuildCMD(),new Duels(),new Help(),new Commands()};
    String TOKEN = "ODAwOTI5NTMwNTkxNDQ1MDE0.YAZRwA.eU8i49mok6hr2InXaXu6IvX2z0M";
    public static JDA jda;


    public Bot() {

    }

    public void start() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);//token

        jdaBuilder.addEventListeners(listenerAdapters);

        jdaBuilder.setActivity(Activity.playing("Playing Epic RPG"));

        try {
            jda = jdaBuilder.build();
            jda.awaitReady();
            jda.getTextChannelById(856142430109696020L).sendMessage("Guild Recorder is online!").queue();
        } catch (LoginException | InterruptedException e){
            e.printStackTrace();
        }


    }
}
