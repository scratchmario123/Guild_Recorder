import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    //will display commands and their infos

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (event.getMessage().getContentRaw().startsWith("*help")) {
            if (args.length == 1) {

                EmbedBuilder help = new EmbedBuilder();
                help.setThumbnail(event.getMember().getUser().getAvatarUrl());
                help.setAuthor(event.getMember().getUser().getName());
                help.setTitle("Help Menu:");
                help.setDescription("My prefix is `*`");
                help.addField("Commands: ", "`help`, `info`, `stats`, `duelStatus`", false);
                help.addField("Admin commands: ", "`removeMember`, `addMember`, `warn`", false);
                help.setFooter("If you want specific information for each command, type '*help [Command Name]'.");
                event.getChannel().sendMessage(help.build()).queue();
            } else if(args.length == 2) {
                String avatarUrl = event.getMember().getUser().getAvatarUrl();
                String authorName = event.getMember().getUser().getName();
                switch (args[1]) {

                    //guild members command below

                    case "help":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*help`","Usage: *help\n\nShows the help menu.")).queue();
                        break;
                    case "info":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*info`","Usage: *info\n\nShows the info of the bot.")).queue();
                        break;
                    case "stats":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*stats`","Usage: *stats\n\nShows the amount of duels and guild upgrades/raids you've done in the recent week.")).queue();
                        break;
                    case "duelStatus":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*duelStatus`","Usage: *duelStatus\n\nCheck whether you've done all the duels required.\nReturns either `done` or `undone [duels missed]`.")).queue();
                        break;

                    //admin commands below

                    case "removeMember":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*removeMember`","Usage: *removeMember [@User]\n\nRemoves a member from the guild member database.\nRequires ADMINISTRATOR permission.")).queue();
                        break;
                    case "addMember":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*addMember`","Usage: *addMember [@User]\n\nAdds a member from the guild member database.\nRequires ADMINISTRATOR permission.")).queue();
                        break;
                    case "warn":
                        event.getChannel().sendMessage(buildEmbed(avatarUrl,authorName,"`*warn`","Usage: *warn [@User]\n\nWarn a guild member.\nRequires ADMINISTRATOR permission.")).queue();
                        break;
                    default:
                        event.getChannel().sendMessage("Sorry, but there is no such command.").queue();
                }
            } else {
                event.getChannel().sendMessage("Please provide less than 3 arguments.").queue();
            }
        }
    }

    private MessageEmbed buildEmbed(String url, String author, String name, String description) {
        EmbedBuilder info = new EmbedBuilder();
        info.setThumbnail(url);
        info.setAuthor(author);
        info.setTitle(name);
        info.setDescription(description);
        return info.build();
    }
}
