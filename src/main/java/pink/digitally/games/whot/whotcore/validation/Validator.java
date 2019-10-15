package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Validator<T> {
    private List<Pair<Predicate<T>, String>> failureConditions;

    private Validator(List<Pair<Predicate<T>, String>> failureConditions){
        this.failureConditions = failureConditions;
    }

    public boolean isValid(T t){
        return !errorMessages(t)
                .isPresent();
    }

    public Optional<String> errorMessages(T t){
        return failureConditions
                .stream()
                .filter(it -> it.getKey().test(t))
                .map(Pair::getValue)
                .reduce((s, s2) -> s + ",\n"+s2);
    }

    public static class ValidatorBuilder<T> {
        private List<Pair<Predicate<T>, String>> failureConditionAndMessages = new ArrayList<>();

        public ValidatorBuilder<T> withFailureConditionAndMessage(Predicate<T> predicate, String message){
            failureConditionAndMessages.add(new Pair<>(predicate, message));
            return this;
        }

        public Validator<T> build(){
            return new Validator<T>(failureConditionAndMessages);
        }
    }
}
