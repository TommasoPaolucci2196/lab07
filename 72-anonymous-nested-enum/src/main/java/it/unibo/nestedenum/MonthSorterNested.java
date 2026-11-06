package it.unibo.nestedenum;

import java.util.Comparator;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public enum Month {
        JANUARY(31, "JANUARY"),
        FEBRUARY(28, "FEBRUARY"),
        MARCH(31, "MARCH"),
        APRIL(30, "APRIL"),
        MAY(31, "MAY"),
        JUNE(30, "JUNE"),
        JULY(31, "JULY"),
        AUGUST(31, "AUGUST"),
        SEPTEMBER(30, "SEPTEMBER"),
        OCTOBER(31, "OCTOBER"),
        NOVEMBER(30, "NOVEMBER"),
        DECEMBER(31, "DECEMBER");

        private final Integer days;
        private final String actualname;

        private Month(final Integer days, final String actualname) {
            this.days = days;
            this.actualname = actualname;
        }
        public static Month fromString(final String month) {
            final String upper = month.toUpperCase();
            Month match = null;
            for (final Month m: values()) {
                if (m.actualname.equals(upper)) {
                    return m;
                }
                if (m.actualname.startsWith(upper)){
                    if (match != null) {
                        throw new IllegalArgumentException("Ambiguous month: " + month);
                    }
                    match = m;
                }
            }
            if (match == null) {
                throw new IllegalArgumentException("This month does not exist: " + month);
            }
            return match;
        }


    }

    private static final class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String month1,final String month2) {
            return Month.fromString(month1).compareTo(Month.fromString(month2));
        }

    }

    private static final class SortByDate implements Comparator<String> {
        @Override
        public int compare(final String month1,final String month2) {
            return Integer.compare(Month.fromString(month1).days,Month.fromString(month2).days);
        }

    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}
