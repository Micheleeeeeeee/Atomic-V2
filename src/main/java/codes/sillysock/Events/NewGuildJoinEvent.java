package codes.sillysock.Events;

import codes.sillysock.Atomic;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class NewGuildJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        boolean exists = false;
        for (Role role : e.getGuild().getRoles()) {
            if (role.getName().equalsIgnoreCase("Muted")) exists = true;
        }

        if (!exists) {
            e.getGuild().createRole()
                    .setName("Muted")
                    .setColor(Color.GRAY)
                    .setHoisted(false)
                    .setMentionable(false)
                    .setPermissions(Permission.EMPTY_PERMISSIONS)
                    .queue();
        }
    }
}
