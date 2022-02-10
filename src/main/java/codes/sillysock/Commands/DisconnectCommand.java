package codes.sillysock.Commands;

import codes.sillysock.API.CommandAPI;
import codes.sillysock.API.EmbedAPI;
import codes.sillysock.API.MemberAPI;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class DisconnectCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("disconnect")) return;

        InteractionHook hook = e.getHook();
        hook.setEphemeral(true);
        e.deferReply(true).queue();

        Member member = e.getMember();
        if (!CommandAPI.HasDisconnectPermission(member)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        Member toDisconnect = e.getOption("user").getAsMember();
        if (!MemberAPI.HasHighestRole(member, toDisconnect)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error", "Silly Sock", "You cannot disconnect a member who has a highest or equal top role to you!", Color.RED)).queue();
            return;
        }

        if (!toDisconnect.getVoiceState().inAudioChannel()) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error", "Silly Sock", "Requested member is not in a voice channel!", Color.RED)).queue();
            return;
        }

        e.getGuild().kickVoiceMember(toDisconnect).queue();
        hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Disconnected", "Silly Sock", toDisconnect.getEffectiveName() + " has been disconnected.", Color.CYAN)).queue();
    }
}
