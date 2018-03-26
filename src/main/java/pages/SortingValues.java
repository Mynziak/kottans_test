package pages;

public enum SortingValues {

    NAME("Name"),
    STARS("Stars"),
    ISSUES("Issues"),
    UPDATED("Updated");

    private final String sortingValue;

    private SortingValues(final String sortingValue) {
        this.sortingValue = sortingValue;
    }

    @Override
    public String toString() {
        return sortingValue;
    }
}
