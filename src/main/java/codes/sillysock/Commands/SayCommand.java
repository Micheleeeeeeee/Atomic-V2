package codes.sillysock.Commands;

import codes.sillysock.API.EmbedAPI;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class SayCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("say")) return;

        InteractionHook hook = e.getHook();
        hook.setEphemeral(true);
        e.deferReply(true).queue();

        Member member = e.getMember();
        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        MessageChannel channel = e.getOption("channel").getAsMessageChannel();

        String toSay = e.getOption("message").getAsString();
        channel.sendMessage(toSay).queue();
        hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Sent message!", "Silly Sock", "'" + toSay + "' has been sent in #" + channel.getName(), Color.CYAN)).queue();
    }
}
