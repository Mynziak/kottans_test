package pages.enums;

public enum LanguageValues {

    ALL("All"),
    PHP("PHP"),
    OBJECTIVE_C("Objective-C"),
    MATLAB("Matlab");

    private final String languageValue;

    private LanguageValues(final String languageValue) {
        this.languageValue = languageValue;
    }

    @Override
    public String toString() {
        return languageValue;
    }
}
