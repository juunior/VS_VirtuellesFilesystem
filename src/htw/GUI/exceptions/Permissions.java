package htw.GUI.exceptions;

public class Permissions {

    private boolean locked = false;

    private boolean mountable = false;

    private final boolean lockingAllowed;

    public Permissions(final boolean lockingAllowed) {
        this.lockingAllowed = lockingAllowed;
    }

    public void toggleLock() {
        if (!lockingAllowed) return;

        locked = !locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isDeleteAllowed() {
        return !locked && !mountable;
    }

    public boolean isGetContentAllowed() {
        return !locked;
    }

    public boolean isCDAllowed() {
        return !locked;
    }

    public boolean isRenameAllowed() {
        return !locked && !mountable;
    }

    public boolean isAddAllowed() {
        return !locked && !mountable;
    }

    public boolean isSearchAllowed() {
        return !locked;
    }

    public boolean isLockingAllowed() {
        return lockingAllowed && !mountable;
    }

    public boolean isMountable() {
        return mountable;
    }

    protected void setMountable() {
        this.mountable = true;
    }

    protected void setLocked(boolean locked) {
        this.locked = locked;
    }
}
