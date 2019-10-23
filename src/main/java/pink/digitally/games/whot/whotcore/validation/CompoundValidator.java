package pink.digitally.games.whot.whotcore.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CompoundValidator implements Validator {

    private Collection<SimpleValidator> validators;

    private CompoundValidator(CompoundValidatorBuilder builder) {
        validators = builder.validators;
    }

    @Override
    public boolean isValid() {
        return validators
                .stream()
                .allMatch(Validator::isValid);
    }

    @Override
    public Optional<String> errorMessages() {
        return validators
                .stream()
                .map(SimpleValidator::errorMessages)
                .reduce(Optional.empty(), (s, s2) ->
                        //TODO This is wrong but I do not care now
                        s.flatMap(it -> Optional.of(it + ", \t" + s2.orElse(null))
                        ));
    }

    public static class CompoundValidatorBuilder {
        private Collection<SimpleValidator> validators = new ArrayList<>();

        public CompoundValidatorBuilder withValidator(SimpleValidator validator) {
            validators.add(validator);
            return this;
        }

        public CompoundValidator build() {
            return new CompoundValidator(this);
        }
    }
}
