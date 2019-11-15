package pink.digitally.games.whot.whotcore.validation;

import java.util.Optional;

public interface Validator<T> {
    boolean isValid(T objectToValidate);
    Optional<String> errorMessages(T objectToValidate);
}
