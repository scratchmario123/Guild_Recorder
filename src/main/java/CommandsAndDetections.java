import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommandsAndDetections extends ListenerAdapter {
    long memberID;
    HashMap<Member, Integer> playerTimes = new HashMap<>();
    HashMap<String, Member> userMember = new HashMap<>();
    HashMap<Member, Long> memberMemberID = new HashMap<>();
    ArrayList<Long> memberIDs = new ArrayList<>();

    int tempNumber;
    Member member;
    int isThere;
    String message;
    String messageID;
    Long latestMessageID;
    String author;
    String userName;
    Member didNotOnTime;




    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        int num = 0;
        if ((event.getMessage().getContentRaw().equalsIgnoreCase("rpg guild upgrade")||event.getMessage().getContentRaw().equalsIgnoreCase("rpg guild raid")) && event.getChannel().getIdLong() == 757056995407691789L) {
            try {
                //get member
                member = event.getMember();
                //add 1 to member's value
                addNumber(member);
                //test
                System.out.println(member);
                //send to channel
                updateMessage();
                //get message and edit all to new String message
                TextChannel textChannel = event.getGuild().getTextChannelsByName("test",false).get(0);
                //latestMessageID = textChannel.getLatestMessageIdLong();
                //textChannel.deleteMessageById(latestMessageID).queue();
                textChannel.sendMessage(message).queue();

            } catch (NullPointerException E) {
                event.getChannel().sendMessage("You are not registered in this week's guild and raid. Please do `/joinguild` command in <#802447472315465748>").queue();
                //delete own message below

                //add emoji
                event.getMessage().addReaction("U+274E").queue();//cross
            }
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("joinguild")) {
            isThere = 0;
            if (event.getChannel().getIdLong() == 802447472315465748L) {
                member = event.getMember();
                memberID = event.getMember().getIdLong();
                userName = event.getMember().getUser().getName();

                for (int id = 0; id < memberIDs.size(); id++) {
                    if (memberIDs.get(id) == memberID) {
                        isThere = 1;
                        break;
                    }
                } if (isThere == 1) {//there
                event.getChannel().sendMessage("You're already in the guild!").queue();
                event.getMessage().addReaction("U+274E").queue();//cross
                System.out.println(memberIDs);
                } else {//not there
                    playerTimes.put(this.member,0);
                    memberIDs.add(memberID);
                    userMember.put(this.userName,this.member);
                    memberMemberID.put(this.member, memberID);
                    updateMessage();
                    event.getMessage().addReaction("U+2705").queue();//tick
                    System.out.println(memberIDs);
                }
            } else if(event.getChannel().getIdLong() != 802447472315465748L){
                System.out.println(memberIDs);
                event.getMessage().addReaction("U+274E").queue();//cross
                event.getChannel().sendMessage("This command is locked here. Please do this command in <#802447472315465748>\nFor more commands, do `/help`.").queue();
            }

        } else if (event.getMessage().getEmbeds().size() > 0 && event.getChannel().getIdLong() == 757056995407691789L) {//detect if who did late
            messageID = event.getMessageId();
            try {
                if (event.getMessage().getEmbeds().get(0).getTitle().startsWith("Your guild has already raided or been upgraded")) {
                    author = event.getMessage().getEmbeds().get(0).getAuthor().getName();
                    author = author.split("'s")[0];

                    System.out.println(author);
                    try {
                        didNotOnTime = userMember.get(author);
                        deductNumber(didNotOnTime);
                        updateMessage();
                        TextChannel textChannel = event.getGuild().getTextChannelsByName("test", false).get(0);
                        //latestMessageID = textChannel.getLatestMessageIdLong();
                        //textChannel.deleteMessageById(latestMessageID).queue();
                        textChannel.sendMessage(message).queue();
                    } catch (NullPointerException Exception) {
                        //event.getChannel().sendMessage("You are not registered in this week's guild and raid. Please do `/joinguild` command in <#802447472315465748>\nFor more commands, do `help`.").queue();
                    }
                event.getChannel().deleteMessageById(messageID).queue();
                }
            } catch (NullPointerException Exception) {
                //
            }

        } else if (event.getMessage().getContentRaw().equalsIgnoreCase( "my")) {
            member = event.getMember();
            try {
                event.getChannel().sendMessage("You raided and upgraded " + getMemberTimes(member) + " times.").queue();
            } catch (NullPointerException E) {
                event.getMessage().addReaction("U+274E").queue();//cross
                event.getChannel().sendMessage("You are not registered in this week's guild and raid. Please do `/joinguild` command in <#802447472315465748>\nFor more commands, do `/help`").queue();
            }

        } else if (args[0].equalsIgnoreCase("add")) {
            if (args.length <= 1) {
                event.getChannel().sendMessage("Correct Usage: `/add [@user]`\nFor more commands, do `/help`").queue();
            } else {
                if (args.length < 3) {
                    if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
                        try {
                            addNumber(event.getMessage().getMentionedMembers().get(0));
                            updateMessage();
                            TextChannel textChannel = event.getGuild().getTextChannelsByName("test", false).get(0);
                            //latestMessageID = textChannel.getLatestMessageIdLong();
                            //textChannel.deleteMessageById(latestMessageID).queue();
                            textChannel.sendMessage(message).queue();
                        } catch (IndexOutOfBoundsException Exception) {
                            event.getChannel().sendMessage("Correct Usage: `/add [@user]`\nFor more commands, do `/help`").queue();
                        }
                    } else {
                        event.getChannel().sendMessage("You need to have the ADMINISTRATOR permission to use this command.\nFor more commands, do `help`").queue();
                    }
                } else {
                    event.getChannel().sendMessage("Correct Usage: `/add [@user]`\nFor more commands, do `/help`").queue();
                }
            }
        } else if (args[0].equalsIgnoreCase("deduct")) {
            if (args.length <= 1) {
                event.getChannel().sendMessage("Correct Usage: `/deduct [@user]`\nFor more commands, do `/help`").queue();
            } else {
                if (args.length < 3) {
                    if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
                        try {
                            deductNumber(event.getMessage().getMentionedMembers().get(0));
                            updateMessage();
                            TextChannel textChannel = event.getGuild().getTextChannelsByName("test", false).get(0);
                            //latestMessageID = textChannel.getLatestMessageIdLong();
                            //textChannel.deleteMessageById(latestMessageID).queue();
                            textChannel.sendMessage(message).queue();
                        } catch (IndexOutOfBoundsException Exception) {
                            event.getChannel().sendMessage("Correct Usage: `/deduct [@user]`\nFor more commands, do `/help`").queue();
                        }
                    } else {
                        event.getChannel().sendMessage("You need to have the ADMINISTRATOR permission to use this command.\nFor more commands, do `help`").queue();
                    }
                } else {
                    event.getChannel().sendMessage("Correct Usage: `/deduct [@user]`\nFor more commands, do `/help`").queue();
                }
            }
        }
    }
    private void reset(){//every one week reset
        playerTimes.clear();

    }
    private void updateMessage() {
        //get all keys

        message = "";
        for (Member key : playerTimes.keySet()) {
            //get corresponding pair
            message = message + "<@!" + memberMemberID.get(key) + "> - " + playerTimes.get(key) + "\n";
        }
    }

    private int getMemberTimes(Member member) {
        //get the times member raided or upgraded
        return playerTimes.get(member);
    }
    private void addNumber(Member member) {
        //add 1 if member raided or upgraded
        tempNumber = playerTimes.get(member);
        playerTimes.remove(member);
        playerTimes.put(member,tempNumber + 1);
    }

    private void deductNumber(Member member) {
        //deduct one if member did not raided or upgraded
        tempNumber = playerTimes.get(member);
        playerTimes.remove(member);
        playerTimes.put(member,tempNumber - 1);
    }

}

