package pupr.pizza.security;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import pupr.pizza.model.Employee;
import pupr.pizza.model.Role;

public class AuthorizationService {
    private final Map<Role, Set<Action>> matrix = new EnumMap<>(Role.class);

    public AuthorizationService() {
        matrix.put(Role.MANAGER, EnumSet.allOf(Action.class));
        matrix.put(Role.CASHIER, EnumSet.of(Action.CREATE_ORDER, Action.CHECKOUT));
        matrix.put(Role.CHEF,    EnumSet.of(Action.UPDATE_ORDER_STATUS));
        matrix.put(Role.DRIVER,  EnumSet.of(Action.UPDATE_ORDER_STATUS));
    }

    public boolean can(Employee actor, Action action) {
        if (actor == null || action == null) return false;
        Set<Action> allowed = matrix.get(actor.getRole());
        return allowed != null && allowed.contains(action);
    }
}