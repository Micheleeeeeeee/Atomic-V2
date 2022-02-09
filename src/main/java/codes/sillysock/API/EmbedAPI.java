package codes.sillysock.API;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedAPI {

    public static MessageEmbed BuildEmbed(String title, String author, String description, Color color) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setAuthor(author);
        builder.setDescription(description);
        builder.setColor(color);

        return builder.build();
    }
}
