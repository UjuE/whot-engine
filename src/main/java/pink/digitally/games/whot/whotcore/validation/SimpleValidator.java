package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class SimpleValidator<T> implements Validator<T> {
    private final List<Pair<Predicate<T>, String>> failureConditions;

    SimpleValidator(List<Pair<Predicate<T>, String>> failureConditions) {
        this.failureConditions = failureConditions;
    }

    @Override
    public boolean isValid(T objectToValidate) {
        return !errorMessages(objectToValidate)
                .isPresent();
    }

    @Override
    public Optional<String> errorMessages(T objectToValidate) {
        return failureConditions
                .stream()
                .filter(it -> it.getKey().test(objectToValidate))
                .map(Pair::getValue)
                .reduce((s, s2) -> s + ",\n" + s2);
    }

    @SuppressWarnings("unused")
    public static <K> Builder<K> builder(Class<K> clazz) {
        return new Builder<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleValidator<?> that = (SimpleValidator<?>) o;
        return Objects.equals(failureConditions, that.failureConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(failureConditions);
    }

    public static class Builder<T> {
        private List<Pair<Predicate<T>, String>> failureConditionAndMessages = new ArrayList<>();

        public Builder<T> withFailureConditionAndMessage(Predicate<T> predicate, String message) {
            failureConditionAndMessages.add(new Pair<>(predicate, message));
            return this;
        }

        public SimpleValidator<T> build() {
            return new SimpleValidator<>(failureConditionAndMessages);
        }
    }
}
