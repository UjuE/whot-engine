package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Validator<T> {
    private final T objectToValidate;
    private final List<Pair<Predicate<T>, String>> failureConditions;

    private Validator(T t, List<Pair<Predicate<T>, String>> failureConditions){
        objectToValidate = t;
        this.failureConditions = failureConditions;
    }

    public boolean isValid(){
        return !errorMessages()
                .isPresent();
    }

    public Optional<String> errorMessages(){
        return failureConditions
                .stream()
                .filter(it -> it.getKey().test(objectToValidate))
                .map(Pair::getValue)
                .reduce((s, s2) -> s + ",\n"+s2);
    }

    public static class ValidatorBuilder<T> {
        private List<Pair<Predicate<T>, String>> failureConditionAndMessages = new ArrayList<>();

        public ValidatorBuilder<T> withFailureConditionAndMessage(Predicate<T> predicate, String message){
            failureConditionAndMessages.add(new Pair<>(predicate, message));
            return this;
        }

        public Validator<T> build(T objectToValidate){
            return new Validator<>(objectToValidate, failureConditionAndMessages);
        }
    }
}