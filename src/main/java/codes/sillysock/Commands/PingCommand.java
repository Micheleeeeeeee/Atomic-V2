package codes.sillysock.Commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class PingCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("ping")) return;

        InteractionHook hook = e.getHook();
        e.deferReply(true).queue();
        hook.setEphemeral(true);

        long currentTime = System.currentTimeMillis();
        hook.sendMessage("Ping...")
                .flatMap(v ->
                    v.editMessageFormat("Pong! (" + (System.currentTimeMillis() - currentTime) + "ms)"))
                .queue();
    }
}
