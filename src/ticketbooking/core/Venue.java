package ticketbooking.core;

public class Venue {
    public static final int NUMBER_OF_SECTIONS = 4; // Fixed number of sections
    public static final int DECREASING_AMOUNT_BY_SECTION = 500; // Fixed number to decrease by each section's boundary
    public static final int DEFAULT_MAX_BOUNDARY_MAX = 5001;
    public static final int DEFAULT_MAX_BOUNDARY_MIN = 4000;
    public static final int DEFAULT_MIN_BOUNDARY_MAX = 4000;
    public static final int DEFAULT_MIN_BOUNDARY_MIN = 3000;
    private final Section[] sections; // Array of sections

    /* Constructor */
    public Venue() {
        this.sections = new Section[NUMBER_OF_SECTIONS];
        initializeSections();
    }

    /* Copy constructor */
    public Venue(Venue original) {
        this.sections = new Section[NUMBER_OF_SECTIONS];
        for (int i = 0; i < NUMBER_OF_SECTIONS; i++) {
            this.sections[i] = new Section(original.getSection(i));
        }
    }

    /* Method to initialize sections with price boundaries */
    private void initializeSections() {
        for (int i = 0; i < NUMBER_OF_SECTIONS; i++) {
            int[] maxPriceBoundary = {DEFAULT_MAX_BOUNDARY_MIN - (i * DECREASING_AMOUNT_BY_SECTION), DEFAULT_MAX_BOUNDARY_MAX - (i * DECREASING_AMOUNT_BY_SECTION)};
            int[] minPriceBoundary = {DEFAULT_MIN_BOUNDARY_MIN - (i * DECREASING_AMOUNT_BY_SECTION), DEFAULT_MIN_BOUNDARY_MAX - (i * DECREASING_AMOUNT_BY_SECTION)};
            sections[i] = new Section(i, maxPriceBoundary, minPriceBoundary);
        }
    }

    /*
        Method to book a ticket in a specific section, row, and seat
        Returns TicketBookingApplication.Core.Ticket if given seat is available, otherwise returns null
        to inform that seat is not available.
    */
    public Ticket bookTicket(int sectionIndex, int row, int seat) {
        /*
            Preconditions:
                - Given section must exist.
                - Given seat must exist.
        */
        if(sectionIndex < 0 || sectionIndex >= NUMBER_OF_SECTIONS) {
            System.out.println("TicketBookingApplication.Core.Section index out of bounds.\nTerminating.");
            System.exit(1);
        }

        if(row < 0 || row >= Section.NUMBER_OF_ROWS || seat < 0 || seat >= Section.SEATS_PER_ROW) {
            System.out.printf("Seat's indexes are out of bounds in %d x %d section.\nTerminating.", Section.NUMBER_OF_ROWS, Section.SEATS_PER_ROW);
            System.exit(1);
        }

        return sections[sectionIndex].bookTicket(row, seat); // Return copy
    }

    /*
        Returns copy of sections array.
     */
    public Section[] getSections() {
        return sections;
    }

    /* Method to retrieve a copy of specific section by its ID */
    public Section getSection(int sectionID) {
        /*
            Preconditions:
                - TicketBookingApplication.Core.Section with given ID must exist.
         */
        if (sectionID < 0 || sectionID >= NUMBER_OF_SECTIONS) {
            System.out.println("Invalid section ID.\nTerminating.");
            System.exit(1);
        }

        return new Section(sections[sectionID]);
    }
}
