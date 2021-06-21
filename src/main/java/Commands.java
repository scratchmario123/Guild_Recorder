import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
    long memberId;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");


        if (event.getMessage().getContentRaw().startsWith("*removeMember")) {
            if (args.length != 2 || event.getMessage().getMentionedMembers().size() == 0) {//how can i know if there are no pings
                event.getChannel().sendMessage("Correct Usage: `*removeMember [@user]`\nFor more commands and info, do `*help`").queue();
            } else {
                if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
                    event.getChannel().sendMessage("deleted person from database ||jk||").queue();
                    //remove person from db
                } else {
                    event.getChannel().sendMessage("You need to have the ADMINISTRATOR permission to use this command.\nFor more commands and info, do `*help`").queue();
                }

            }
        }

    }


}
