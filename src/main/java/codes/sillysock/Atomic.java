package codes.sillysock;

import codes.sillysock.Commands.*;
import codes.sillysock.Events.NewGuildJoinEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class Atomic {
    private static String token;

    public static void main(String[] args) throws LoginException {
        token = System.getenv("TOKEN");

        JDA jda = JDABuilder.createDefault(token)
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addEventListeners(new TimeoutCommand())
                .addEventListeners(new PingCommand())
                .addEventListeners(new UntimeoutCommand())
                .addEventListeners(new NewGuildJoinEvent())
                .addEventListeners(new UnmuteCommand())
                .addEventListeners(new MuteCommand())
                .addEventListeners(new SayCommand())
                .addEventListeners(new DisconnectCommand())
                .addEventListeners(new PMCommand())
                .build();

        CommandData timeout = new CommandData("timeout", "Timeout a user.")
                .addOptions(new OptionData(USER, "user", "User who will be timed out.")
                        .setRequired(true))
                .addOptions(new OptionData(INTEGER, "length", "Length of timeout")
                        .setRequired(true))
                .addOptions(new OptionData(STRING, "date_type", "Type of time")
                        .setRequired(true)
                        .addChoice("SECONDS", "SECONDS")
                        .addChoice("MINUTES", "MINUTES")
                        .addChoice("HOURS", "HOURS")
                        .addChoice("DAYS", "DAYS"));

        CommandData untimeout = new CommandData("untimeout", "Remove a timeout from a user.")
                .addOptions(new OptionData(USER, "user", "User to remove timeout from.")
                        .setRequired(true));

        CommandData unmute = new CommandData("unmute", "Unmute a user.")
                .addOptions(new OptionData(USER, "user", "User to unmute.")
                        .setRequired(true));

        CommandData mute = new CommandData("mute", "Mute a user.")
                .addOptions(new OptionData(USER, "user", "User to mute.")
                        .setRequired(true));

        CommandData say = new CommandData("say", "Say something in a channel!")
                .addOptions(new OptionData(CHANNEL, "channel", "Where to send the message")
                        .setRequired(true))
                .addOptions(new OptionData(STRING, "message", "Message to send!")
                        .setRequired(true));

        CommandData disconnect = new CommandData("disconnect", "Disconnect someone from a voice channel")
                .addOptions(new OptionData(USER, "user", "User to disconnect")
                        .setRequired(true));

        CommandData sayInPM = new CommandData("pmuser", "Private Message a user as Atomic.")
                .addOptions(new OptionData(USER, "user", "User to PM.")
                        .setRequired(true))
                .addOptions(new OptionData(STRING, "message", "Message to send.")
                        .setRequired(true));

        CommandData ping = new CommandData("ping", "Get ping of bot.");

        jda.updateCommands().addCommands(timeout, ping, untimeout, mute, unmute, say, disconnect, sayInPM).queue();
    }
}
