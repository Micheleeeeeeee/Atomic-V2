package codes.sillysock.API;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class MemberAPI {

    public static boolean HasHighestRole(Member member, Member compareTo) {
        return GetHighestRolePosition(member) > GetHighestRolePosition(compareTo);
    }

    public static int GetHighestRolePosition(Member member) {
        int highestPos = 0;
        for (Role role : member.getRoles()) {
            if (role.getPosition() > highestPos) {
                highestPos = role.getPosition();
            }
        }
        return highestPos;
    }
}
