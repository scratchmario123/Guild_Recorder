import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Duels extends ListenerAdapter {

    long duelChannelId = 807808741402738708L;
    long duelLogId = 857146759330267136L;

    String memberName;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        memberName = event.getMember().getUser().getName();
        if (event.getChannel().getIdLong() == duelChannelId) {
            if (event.getMessage().getContentRaw().contains(memberName) || event.getMessage().getAttachments().size() > 0) {
                event.getGuild().getTextChannelById(duelLogId).sendMessage("`"+ event.getMember().getUser().getAsTag() + "` just dueled!").queue();
            }
        }
    }

}
