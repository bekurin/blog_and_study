package core.db;

import core.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static Map<Long, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(Long userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
