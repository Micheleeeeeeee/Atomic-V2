package codes.sillysock.API;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.NotNull;

public class CommandAPI {

    /**
     * Checks if a member has permissions to moderate a player.
     * @param member
     * @return hasAdmin
     */

    public static boolean HasModerateMembers(@NotNull Member member) {
        return member.hasPermission(Permission.MODERATE_MEMBERS);
    }

    /**
     * Checks if the member has the muted role.
     * @param member
     * @return
     */

    public static boolean HasMutedRole(@NotNull Member member) {
        for (Role role : member.getRoles()) {
            if (role.getName().equalsIgnoreCase("Muted")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a member has {@code Permissions.VOICE_MOVE_OTHERS}
     * @param member
     * @returns hasPerm
     */

    public static boolean HasDisconnectPermission(@NotNull Member member) {
        return member.hasPermission(Permission.VOICE_MOVE_OTHERS);
    }

    /**
     * checks if a member has Administrator permissions.
     * @param member
     * @returns hasAdmin
     */

    public static boolean HasAdministrator(@NotNull Member member) {
        return member.hasPermission(Permission.ADMINISTRATOR);
    }
}
