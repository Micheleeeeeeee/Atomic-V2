package codes.sillysock.Commands;

import codes.sillysock.API.CommandAPI;
import codes.sillysock.API.EmbedAPI;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class TimeoutCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("timeout")) return;

        InteractionHook hook = e.getHook();
        hook.setEphemeral(false);
        e.deferReply(true).queue();

        Member member = e.getMember();
        if (!CommandAPI.HasModerateMembers(member)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        Member toTimeOut = e.getOption("user").getAsMember();
        TimeUnit unit = TimeUnit.valueOf(e.getOption("date_type").getAsString());
        long length = e.getOption("length").getAsLong();

        try {
            toTimeOut.timeoutFor(length, unit).queue();
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Timed out " + toTimeOut.getEffectiveName(), "Silly Sock", "Timed out " + toTimeOut.getEffectiveName() + " for " + length + " " + unit, Color.CYAN)).queue();
        } catch (HierarchyException ex) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error whilst timing out", "Silly Sock", "You cannot time out someone with higher or equal highest role to you!", Color.CYAN)).queue();
        }
    }
}
