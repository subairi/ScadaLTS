package org.scada_lts.mango.service;

import br.org.scadabr.vo.usersProfiles.UsersProfileVO;
import com.serotonin.mango.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scada_lts.dao.DAO;
import org.scada_lts.dao.UsersProfileDAO;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsersProfileService {

    private static final Log LOG = LogFactory.getLog(UsersProfileService.class);
    private static final String LIST_SIZE_IS_GREATER_THAN_1 = "The user has more than one profile assigned. \nuserId: {0},\nprofiles: {1}\n";

    private UsersProfileDAO usersProfileDAO;

    public UsersProfileService() {
        this.usersProfileDAO = new UsersProfileDAO(DAO.getInstance().getJdbcTemp());
    }

    public UsersProfileService(UsersProfileDAO usersProfileDAO) {
        this.usersProfileDAO = usersProfileDAO;
    }

    public Optional<UsersProfileVO> getProfileByUser(User user) {
        return getProfileByUserId(user.getId());
    }

    public Optional<UsersProfileVO> getProfileByUserId(int userId) {
        List<UsersProfileVO> profiles = usersProfileDAO.selectUserProfileByUserId(userId);
        if(profiles.isEmpty())
            return Optional.empty();
        if(profiles.size() > 1) {
            LOG.warn(MessageFormat.format(LIST_SIZE_IS_GREATER_THAN_1, userId, profiles));
        }
        return profiles.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(UsersProfileVO::getId));
    }

    public List<UsersProfileVO> getProfiles(int limit) {
        return usersProfileDAO.selectProfiles(0, limit);
    }

    public Optional<UsersProfileVO> getProfileById(int usersProfileId) {
        return usersProfileDAO.selectProfileById(usersProfileId);
    }

    public List<Integer> getUsersByProfile(UsersProfileVO profile) {
        return getUsersByProfileId(profile.getId());
    }

    public List<Integer> getUsersByProfileId(int usersProfileId) {
        return usersProfileDAO.selectUsersByProfileId(usersProfileId);
    }

    public void setProfileName(String name, UsersProfileVO profile) {
        usersProfileDAO.updateProfileName(name, profile.getId());
    }

    public void removeUserProfile(User user) {
        usersProfileDAO.deleteUserProfileByUserId(user.getId());
    }

    public void removeProfile(UsersProfileVO profile) {
       removeProfile(profile.getId());
    }

    public void removeProfile(int profileId) {
        usersProfileDAO.deleteProfile(profileId);
    }

    public void createUserProfile(User user, UsersProfileVO profile) {
        usersProfileDAO.insertUserProfile(user.getId(), profile.getId());
    }

    public int createProfile(String profileXid, String profileName) {
        return usersProfileDAO.insertProfile(profileXid, profileName);
    }

    public void saveRelationalData(final UsersProfileVO usersProfile) {
        usersProfileDAO.updateDataSourceUsersProfiles(usersProfile);
        usersProfileDAO.updateDataPointUsersProfiles(usersProfile);
        usersProfileDAO.updateWatchListUsersProfiles(usersProfile);
        usersProfileDAO.updateViewUsersProfiles(usersProfile);
    }
}
