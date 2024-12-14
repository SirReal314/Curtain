package dev.dubhe.curtain.helpers.rule;

import dev.dubhe.curtain.api.rules.CurtainRule;
import dev.dubhe.curtain.api.rules.IValidator;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RuleScheduler {

    /*
     * Some rules need world to be loaded before being able to apply there settings
     * This class will hold a list of rules that should be set once the world is loaded
     */

    private static final List<RuleChange<?>> scheduledRuleChanges = new ArrayList<>();

    public RuleScheduler() {}

    public void addRule(RuleChange<?> ruleChange) {
        scheduledRuleChanges.add(ruleChange);
    }

    /*
     * Adds a rule that needs to be called once the validator is set
     */
    public <T> void addRule(IValidator<T> validator, CurtainRule<T> currentRule, String newValue) {
        addRule(new RuleChange<>(validator, currentRule, newValue));
    }

    /*
     * Adds a rule that should be set to a default value which requires world. We do this by passing in a function
     * that will get the default value that should be used for the rule. It's a bit of a workaround, although
     * carpet does not really provide any better alternative.
     * The string value should be ignored for default values!
     */
    public <T> void addDefaultRule(IValidator<T> validator, CurtainRule<T> currentRule, String newValue, Function<MinecraftServer,String> setDefault) {
        addRule(new RuleChange<>(validator, currentRule, newValue, setDefault));
    }

    public void loadRules(MinecraftServer server) {
        System.console().printf("FFFFFFFFFFUUUUUUUUUUUUUUCCCCCCCCCCCCCCCCCKKKKKKKKKK\n");
        System.console().printf("%s\n", scheduledRuleChanges.size());
//        System.console().printf("%s\n", scheduledRuleChanges.get(0).validator);
        for (RuleChange<?> ruleChange : scheduledRuleChanges)
            ruleChange.triggerValidator(server);
        scheduledRuleChanges.clear();
    }

    static class RuleChange<T> {
        private final IValidator<T> validator;
        private final CurtainRule<T> currentRule;
        private final String newValue;
        private final Function<MinecraftServer,String> setDefault;
        public RuleChange(IValidator<T> validator, CurtainRule<T> currentRule, String newValue, Function<MinecraftServer, String> setDefault) {
            this.validator = validator;
            this.currentRule = currentRule;
            this.newValue = newValue;
            this.setDefault = setDefault;
        }

        public RuleChange(IValidator<T> validator, CurtainRule<T> currentRule, String newValue) {
            this(validator, currentRule, newValue, null);
        }

        public void triggerValidator(MinecraftServer server) {
            String newValue = this.newValue;
            if (this.setDefault != null)
                newValue = this.setDefault.apply(server);
            this.validator.validate(server.createCommandSourceStack(), this.currentRule, newValue);
        }
    }
}
