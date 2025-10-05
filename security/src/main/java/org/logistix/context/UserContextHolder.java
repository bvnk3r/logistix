package org.logistix.context;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static UserContext getUserContext() {
        UserContext context = userContext.get();

        if (context == null) {
            context = new UserContext();
            userContext.set(context);
        }
        return context;
    }

    public static void setUserContext(UserContext context) {
        userContext.set(context);
    }
}
