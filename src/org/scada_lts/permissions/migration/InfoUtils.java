package org.scada_lts.permissions.migration;

import br.org.scadabr.vo.usersProfiles.UsersProfileVO;
import com.serotonin.mango.view.View;
import com.serotonin.mango.vo.User;

import java.text.MessageFormat;

final class InfoUtils {

    private InfoUtils() {}

    static String iterationInfo(String msg, int iteration, int max) {
        return MessageFormat.format("{0} - {1}/{2}", msg, iteration, max);
    }

    static String viewInfo(View view) {
        return MessageFormat.format("view: {0} (id: {1}, xid: {2})", view.getName(), view.getId(), view.getXid());
    }

    static <T> String verifyInfo(T permission, User user, boolean transfered, boolean exists, UsersProfileVO profile) {
        return MessageFormat.format("transferred: {0} (exists: {1}) {2} {3} {4}", transfered, exists,
                permissionInfo(permission), userInfo(user), profileInfo(profile));
    }

    static <T> String verifyInfo(T permission, User user, boolean transfered, boolean exists) {
        return MessageFormat.format("transferred: {0} (exists: {1}) {2} {3}", transfered, exists,
                permissionInfo(permission), userInfo(user));
    }

    static String userInfo(User user) {
        return MessageFormat.format("user: {0} (id: {1}, admin: {2})", user.getUsername(), user.getId(), user.isAdmin());
    }

    private static <T> String permissionInfo(T permission) {
        if(permission instanceof Integer)
            return MessageFormat.format("permission: DataSourceAccess(id={0})", permission);
        return MessageFormat.format("permission: {0}", permission);
    }

    static String profileInfo(UsersProfileVO profile) {
        return MessageFormat.format("profile: {0} (id: {1}, xid: {2})", profile.getName(), profile.getId(), profile.getXid());
    }
}
