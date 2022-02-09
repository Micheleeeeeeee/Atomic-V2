package codes.sillysock.Commands;

import codes.sillysock.API.CommandAPI;
import codes.sillysock.API.EmbedAPI;
import codes.sillysock.API.MemberAPI;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class MuteCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("mute")) return;

        InteractionHook hook = e.getHook();
        Guild guild = e.getGuild();
        hook.setEphemeral(true);
        e.deferReply(true).queue();

        Member member = e.getMember();
        if (!CommandAPI.HasModerateMembers(member)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        Member toMute = e.getOption("user").getAsMember();
        if (!MemberAPI.HasHighestRole(member, toMute)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error", "Silly Sock", "You cannot mute a member who has a highest or equal top role to you!", Color.RED)).queue();
            return;
        }

        if (CommandAPI.HasMutedRole(toMute)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: /Mute", "Silly Sock", toMute.getEffectiveName() + " is already muted.", Color.RED)).queue();
            return;
        }

        guild.addRoleToMember(toMute.getId(), guild.getRolesByName("Muted", true).get(0)).queue();
        hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Muted", "Silly Sock", toMute.getEffectiveName() + " has been muted.", Color.CYAN)).queue();
    }
}
