package org.scada_lts.permissions.migration;

import br.org.scadabr.vo.permission.ViewAccess;
import br.org.scadabr.vo.permission.WatchListAccess;
import br.org.scadabr.vo.usersProfiles.UsersProfileVO;
import com.serotonin.mango.vo.permission.DataPointAccess;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Accesses {
    private Set<ViewAccess> viewAccesses;
    private Set<WatchListAccess> watchListAccesses;
    private Set<DataPointAccess> dataPointAccesses;
    private Set<Integer> dataSourceAccesses;

    Accesses(Set<ViewAccess> viewAccesses,
                    Set<WatchListAccess> watchListAccesses,
                    Set<DataPointAccess> dataPointAccesses,
                    Set<Integer> dataSourceAccesses) {
        this.viewAccesses = viewAccesses;
        this.watchListAccesses = watchListAccesses;
        this.dataPointAccesses = dataPointAccesses;
        this.dataSourceAccesses = dataSourceAccesses;
    }

    Accesses(UsersProfileVO usersProfile) {
        this.dataPointAccesses = new HashSet<>(usersProfile.getDataPointPermissions());
        this.dataSourceAccesses = new HashSet<>(usersProfile.getDataSourcePermissions());
        this.watchListAccesses = new HashSet<>(usersProfile.getWatchlistPermissions());
        this.viewAccesses = new HashSet<>(usersProfile.getViewPermissions());
    }

    public static Accesses empty() {
        return new Accesses(new UsersProfileVO());
    }

    public Set<ViewAccess> getViewAccesses() {
        return viewAccesses;
    }

    public Set<WatchListAccess> getWatchListAccesses() {
        return watchListAccesses;
    }

    public Set<DataPointAccess> getDataPointAccesses() {
        return dataPointAccesses;
    }

    public Set<Integer> getDataSourceAccesses() {
        return dataSourceAccesses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accesses)) return false;
        Accesses accesses = (Accesses) o;
        return Objects.equals(getViewAccesses(), accesses.getViewAccesses()) &&
                Objects.equals(getWatchListAccesses(), accesses.getWatchListAccesses()) &&
                Objects.equals(getDataPointAccesses(), accesses.getDataPointAccesses()) &&
                Objects.equals(getDataSourceAccesses(), accesses.getDataSourceAccesses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getViewAccesses(), getWatchListAccesses(), getDataPointAccesses(), getDataSourceAccesses());
    }

    public boolean isEmpty() {
        return getViewAccesses().isEmpty() && getDataPointAccesses().isEmpty() && getDataSourceAccesses().isEmpty()
                && getWatchListAccesses().isEmpty();
    }
}
