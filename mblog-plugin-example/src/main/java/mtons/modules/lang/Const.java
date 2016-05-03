package mtons.modules.lang;

public interface Const {

    String KEY_LOGIN       = "profile.login";
    String KEY_SESSIONID   = "sessionId";
    String KEY_ADMIN_LOGIN = "profile.admin";

    long   ADMIN_ID        = 1;

    String ROLE_ADMIN      = "admin";

    int    IGNORE          = -1;

    int    ZERO            = 0;

    int    STATUS_NORMAL   = 0;

    int    STATUS_CLOSED   = 1;

    int    STATUS_REMOVED  = 2;

    int    AUDIT_INIT      = 0;

    int    AUDIT_PASSED    = 1;

    int    AUDIT_REJECTED  = 2;

}
