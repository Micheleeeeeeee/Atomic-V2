package codes.sillysock.Commands;

import codes.sillysock.API.CommandAPI;
import codes.sillysock.API.EmbedAPI;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class UntimeoutCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("untimeout")) return;

        InteractionHook hook = e.getHook();
        hook.setEphemeral(false);
        e.deferReply(true).queue();

        Member member = e.getMember();
        if (!CommandAPI.HasModerateMembers(member)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        Member toRemoveTimeout = e.getOption("user").getAsMember();
        if (!toRemoveTimeout.isTimedOut()) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Untimeout Error", "Silly Sock", "The selected user is not currently timed out.", Color.CYAN)).queue();
            return;
        }

        toRemoveTimeout.removeTimeout().queue();
        hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Successfully Removed Timeout", "Silly Sock", "Successfully removed timeout from " + toRemoveTimeout.getEffectiveName(), Color.CYAN)).queue();
    }
}
