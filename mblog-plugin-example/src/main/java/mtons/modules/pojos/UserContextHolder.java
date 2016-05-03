package mtons.modules.pojos;

/**
 * 
 * @author langhsu
 * 
 */
public class UserContextHolder {
    private static ThreadLocal<UserProfile> local = new ThreadLocal<UserProfile>();

    public static UserProfile getUserProfile() {
        return (UserProfile) local.get();
    }

    public static void setUserProfile(UserProfile context) {
        local.set(context);
    }

    public static void removeUserContext() {
        local.remove();
    }
}
