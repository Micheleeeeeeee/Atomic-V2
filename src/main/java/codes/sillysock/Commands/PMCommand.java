package codes.sillysock.Commands;

import codes.sillysock.API.CommandAPI;
import codes.sillysock.API.EmbedAPI;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class PMCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("pmuser")) return;

        InteractionHook hook = e.getHook();
        hook.setEphemeral(true);
        e.deferReply(true).queue();

        Member member = e.getMember();
        assert member != null;
        if (!CommandAPI.HasAdministrator(member)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        User toSend;
        String message;

        try {
            toSend = e.getOption("user").getAsMember().getUser();
            message = e.getOption("message").getAsString();
        } catch (NullPointerException ex) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error", "Silly Sock", "The member must be in this guild!", Color.RED)).queue();
            return;
        }

        toSend.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(message))
                .queue();

        hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Message Sent", "Silly Sock", message + " has been sent to " + toSend.getName(), Color.CYAN)).queue();
    }
}
