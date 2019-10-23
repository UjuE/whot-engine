package pink.digitally.games.whot.whotcore.validation;

import java.util.Optional;

public interface Validator {
    boolean isValid();
    Optional<String> errorMessages();
}
