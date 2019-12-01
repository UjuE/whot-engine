package pink.digitally.games.whot.whotcore.error;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ErrorMessage {
    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .toString();
    }
}
