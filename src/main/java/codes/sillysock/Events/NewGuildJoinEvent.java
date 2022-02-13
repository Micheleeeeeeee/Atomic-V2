package codes.sillysock.Events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NewGuildJoinEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        Guild guild = e.getGuild();
        if (e.getMessage().getContentRaw().startsWith("do the thing")) {
            () -> test(guild)(bruh -> {
                System.out.println("hello");
                Role role = guild.getRolesByName("Muted", true).get(0);
                System.out.println("hello 2");
                for (TextChannel channel : guild.getTextChannels()) {
                    channel.createPermissionOverride(role)
                            .setDeny(Permission.MESSAGE_SEND)
                            .setDeny(Permission.MESSAGE_ADD_REACTION)
                            .queue();
                }
                for (VoiceChannel channel : guild.getVoiceChannels()) {
                    channel.createPermissionOverride(role)
                            .setDeny(Permission.VOICE_SPEAK)
                            .queue();
                }
            });
        }
    }

    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        boolean exists = false;
        Guild guild = e.getGuild();
        for (Role role : guild.getRoles()) {
            if (role.getName().equalsIgnoreCase("Muted")) exists = true;
        }

        if (!exists) {
            System.out.println("Does not exist");
            final Role[] role = new Role[1];
            try {
                CreateMutedRole(guild).thenAccept(future -> {
                    System.out.println("hello");
                    role[0] = guild.getRolesByName("Muted", true).get(0);
                    System.out.println("hello 2");
                    for (TextChannel channel : guild.getTextChannels()) {
                        channel.createPermissionOverride(role[0])
                                .setDeny(Permission.MESSAGE_SEND)
                                .setDeny(Permission.MESSAGE_ADD_REACTION)
                                .queue();
                    }

                    for (VoiceChannel channel : guild.getVoiceChannels()) {
                        channel.createPermissionOverride(role[0])
                                .setDeny(Permission.VOICE_SPEAK)
                                .queue();
                    }
                });
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void test(Guild guild) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        new Runnable() {
            @Override
            public void run() {
                guild.createRole()
                        .setName("Muted")
                        .setColor(Color.GRAY)
                        .setHoisted(false)
                        .setMentionable(false)
                        .setPermissions(Permission.EMPTY_PERMISSIONS)
                        .queue();
            }
        };

        future.complete(null);
    }
    
    public CompletableFuture<Void> CreateMutedRole(Guild guild) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            guild.createRole()
                    .setName("Muted")
                    .setColor(Color.GRAY)
                    .setHoisted(false)
                    .setMentionable(false)
                    .setPermissions(Permission.EMPTY_PERMISSIONS)
                    .queue();

            return null;
        });

        future.get();
        return future;
    }
}
