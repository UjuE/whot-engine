package pink.digitally.games.whot.whotcore.validation;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrValidator<T> implements Validator<T> {

    private Collection<SimpleValidator<T>> validators;

    public OrValidator(Collection<SimpleValidator<T>> validators){
        this.validators = validators;
    }

    @Override
    public boolean isValid(T objectToValidate) {
        return validators
                .stream()
                .anyMatch(it -> it.isValid(objectToValidate));
    }

    @Override
    public Optional<String> errorMessages(T objectToValidate) {
        String errorMessages = validators
                .stream()
                .map(it -> it.errorMessages(objectToValidate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining(", "));

        return Optional.of(errorMessages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrValidator that = (OrValidator) o;
        return Objects.equals(validators, that.validators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validators);
    }
}
