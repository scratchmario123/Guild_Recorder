import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.util.*;


public class GuildCMD extends ListenerAdapter {

    long guildChannelId = 847004694407544844L;
    long guildLogId = 857147303264649216L;

    long epicRpg = 555955826880413696L;

    public static HashMap<Long,Integer> memberTimes = new HashMap<>();//reworked with db

    Member member;
    Member members;
    String author;

    List<Long> queue = new ArrayList<>();//add to db




    public GuildCMD() {

    }





    /*public void update() {
        System.out.println(guildMessageId + " entered update()");
        if (needPut) {

            String msg = "";
            for(long id : memberTimes.keySet()) {
                msg += "<@!" + id + "> --- Upgraded/Raided " + memberTimes.get(id) + " times\n";
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Guild Upgrades/Raids List");
            embedBuilder.setDescription("**Description**\n" +
                    "__It is the number of guild commands done by guild members\n" +
                    "If Stealth below 96, then \"rpg guild upgrade\".__ \uD83C\uDD99\n" +
                    "__If Stealth equals 96 or above then\n" +
                    "\"rpg guild raid\" :crossed_swords:\n" +
                    "\n" +
                    "If you don't know how many stealth or energy does our Guild have\n" +
                    "use \"rpg guild\" in  <#808720588671680572>\n__\n\n\n"+ msg);
            //you can only ping someone in there
            embedBuilder.setFooter("Last Updated: time...");

            MessageEmbed guildEmbed = embedBuilder.build();
            Bot.jda.getTextChannelById(guildListId).sendMessage(guildEmbed).queue((message) -> {
                guildMessageId = message.getIdLong();
                System.out.println(guildMessageId);

            });
            needPut = false;

        } else {
            System.out.println(guildMessageId);
            String msg = "";
            for(long id : memberTimes.keySet()) {
                msg += "<@!" + id + "> --- Upgraded/Raided " + memberTimes.get(id) + " times\n";
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Guild Upgrades/Raids List");
            embedBuilder.setDescription("**Description**\n" +
                    "__It is the number of guild commands done by guild members\n" +
                    "If Stealth below 96, then \"rpg guild upgrade\".__ \uD83C\uDD99\n" +
                    "__If Stealth equals 96 or above then \"rpg guild raid\" :crossed_swords:\n" +
                    "\n" +
                    "If you don't know how many stealth or energy does our Guild have\n" +
                    "use \"rpg guild\" in  <#808720588671680572>\n__\n\n"+ msg);
            //you can only ping someone in there
            embedBuilder.setFooter("Last Updated: time...");

            MessageEmbed guildEmbed = embedBuilder.build();
            Bot.jda.getTextChannelById(guildListId).sendMessage(guildEmbed).queue();

        }

    }

    public void put() {

    }*/

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Timer timer = new Timer();
        if (event.getChannel().getIdLong() == guildChannelId) {
            if ((event.getMessage().getContentRaw().startsWith("rpg guild upgrade") || event.getMessage().getContentRaw().startsWith("rpg guild raid"))) {//what if rpg bot down
                member = event.getMember();
                queue.add(member.getIdLong());
                System.out.println(queue);
                //start timer task
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Bot.jda.retrieveUserById(queue.get(0)).map(User::getAsTag).queue(authorIdTag -> {
                            event.getGuild().getTextChannelById(guildLogId).sendMessage("`"+ authorIdTag + "` raided/upgraded! (2 hours ago)").queue();
                        });
                        queue.clear();
                        //add the person remaining in the list to the database
                        //send updated message
                    }
                };
                timer.schedule(task,71999999L);




            } else if(event.getMessage().getEmbeds().size() > 0 && event.getMessage().getMember().getIdLong() == epicRpg) {
                try {
                    if (event.getMessage().getEmbeds().get(0).getTitle().startsWith("Your guild has already raided or been upgraded")) {
                        author = event.getMessage().getEmbeds().get(0).getAuthor().getName().split("'s")[0];

                        for (int i = 0; i < event.getGuild().getMembers().size(); i++) {
                            members = event.getGuild().getMembers().get(i);
                            if (members.getUser().getName().equals(author)) {
                                queue.remove(member.getIdLong());
                                System.out.println(queue);
                            }
                        }
                    }
                } catch (NullPointerException E) {
                    System.out.println(E.getMessage());
                }
                if (queue.size() == 0) {
                    timer.cancel();
                    //stop the timer task!!!
                }
            } else if(event.getMessage().getContentRaw().contains("end your previous command")&& event.getMessage().getMember().getIdLong() == epicRpg) {
                queue.remove(event.getMessage().getMentionedMembers().get(0).getUser().getIdLong());


                if (queue.size() == 0) {
                    timer.cancel();
                    //stop the timer task!!!
                }

            }
        }
    }


}
