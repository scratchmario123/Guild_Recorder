import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Duels extends ListenerAdapter {

    long duelChannelId = 807808741402738708L;
    long duelListId = 846288744088469505L;

    String memberName;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        memberName = event.getMember().getUser().getName();
        if (event.getChannel().getIdLong() == duelChannelId) {
            if (event.getMessage().getContentRaw().contains(memberName) || event.getMessage().getAttachments().size() > 0) {
                TextChannel duelListTempId = event.getGuild().getTextChannelById(847004694407544844L);
                duelListTempId.sendMessage("`"+ event.getMember().getUser().getAsTag() + "` just dueled!").queue();
            }
        }
    }

}
