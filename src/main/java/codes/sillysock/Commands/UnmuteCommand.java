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

public class UnmuteCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("unmute")) return;

        InteractionHook hook = e.getHook();
        Guild guild = e.getGuild();
        hook.setEphemeral(true);
        e.deferReply(true).queue();

        Member member = e.getMember();
        if (!CommandAPI.HasModerateMembers(member)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: Insufficient Permissions", "Silly Sock", "You do not have the required permission to execute this command.", Color.RED)).queue();
            return;
        }

        Member toUnmute = e.getOption("user").getAsMember();
        if (!MemberAPI.HasHighestRole(member, toUnmute)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error", "Silly Sock", "You cannot unmute a member who has a highest or equal top role to you!", Color.RED)).queue();
            return;
        }

        if (!CommandAPI.HasMutedRole(toUnmute)) {
            hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Error: /Unmute", "Silly Sock", toUnmute.getEffectiveName() + " is not muted.", Color.RED)).queue();
            return;
        }

        guild.removeRoleFromMember(toUnmute.getId(), guild.getRolesByName("Muted", true).get(0)).queue();
        hook.sendMessageEmbeds(EmbedAPI.BuildEmbed("Muted", "Silly Sock", toUnmute.getEffectiveName() + " has been unmuted.", Color.CYAN)).queue();
    }
}
