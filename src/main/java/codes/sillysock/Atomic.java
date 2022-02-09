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

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class Atomic {
    private static String token;

    public static void main(String[] args) throws LoginException {
        token = System.getenv("TOKEN");

        JDA jda = JDABuilder.createDefault(token)
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addEventListeners(new TimeoutCommand())
                .addEventListeners(new PingCommand())
                .addEventListeners(new UntimeoutCommand())
                .addEventListeners(new NewGuildJoinEvent())
                .addEventListeners(new UnmuteCommand())
                .addEventListeners(new MuteCommand())
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

        CommandData ping = new CommandData("ping", "Get ping of bot.");

        jda.updateCommands().addCommands(timeout, ping, untimeout, mute, unmute).queue();
    }
}
