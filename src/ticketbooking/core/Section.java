package ticketbooking.core;

import java.util.Random;

public class Section {
    public static final int NUMBER_OF_ROWS = 10;
    public static final int SEATS_PER_ROW = 60;
    private int ID;
    private int maxPrice = -1; // -1 as undeclared
    private int minPrice = -1; // -1 as undeclared
    private int[] maxPriceBoundaries;
    private int[] minPriceBoundaries;
    private Ticket[][] tickets;
    private int occupiedCount;
    private double revenue;

    /* Default - No-argument Constructor */
    public Section() {
        System.out.println("You need to provide ID (int), maxPriceBoundary (int[2]), minPriceBoundary (int[2]) to create TicketBookingApplication.Core.Section.");
        System.exit(1);
    }

    /* Full Constructor */
    public Section(int ID, int[] maxPriceBoundaries, int[] minPriceBoundaries) {
        setID(ID);
        setMaxPriceBoundaries(maxPriceBoundaries);
        setMinPriceBoundaries(minPriceBoundaries);
        setMaxPrice(_declareRandomBoundaryPrice(maxPriceBoundaries));
        setMinPrice(_declareRandomBoundaryPrice(minPriceBoundaries));
        this.tickets = new Ticket[NUMBER_OF_ROWS][SEATS_PER_ROW];
        _initializeUnoccupiedTickets();
    }

    /* Copy Constructor */
    public Section(Section original) {
        setID(original.getID());
        setMaxPriceBoundaries(original.getMaxPriceBoundaries().clone()); // Copies original array.
        setMinPriceBoundaries(original.getMinPriceBoundaries().clone());
        setMaxPrice(original.getMaxPrice());
        setMinPrice(original.getMinPrice());
        this.tickets = new Ticket[NUMBER_OF_ROWS][SEATS_PER_ROW];
        // Deep copy of arrays
        setTickets(original.getTickets());
        this.occupiedCount = original.occupiedCount();
        this.revenue = original.revenue();
    }

    /*
        Method to use in constructor to initialize
        tickets array with unoccupied TicketBookingApplication.Core.Ticket objects.
     */
    private void _initializeUnoccupiedTickets() {
        for(int i = 0; i < NUMBER_OF_ROWS; i++) {
            for(int j = 0; j < SEATS_PER_ROW; j++) {
                double ticketPrice = _getRandomTicketPriceOfRow(i);
                this.tickets[i][j] = new Ticket(this.ID, i, j, ticketPrice, false);
            }
        }
    }

    /*
        Returns string representation of TicketBookingApplication.Core.Section object to
        use it in debugging or logging.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Seat occupancies in TicketBookingApplication.Core.Section ").append(this.ID + 1).append("\n");
        for(int i = 0; i < NUMBER_OF_ROWS; i++) {
            for(int j = 0; j < SEATS_PER_ROW; j++) {
                // If occupied: X, else O
                result.append(tickets[i][j].getBookingStatus() ? "X " : "O ");
            }
            result.append("\n");
        }
        result.append("\n");
        return result.toString();
    }

    /*
        Books ticket from section if given seat positions
        are valid and the seat is empty. If seat is occupied
        returns null to inform that seat is already occupied.
     */
    public Ticket bookTicket(int row, int col) {
        /*
            Preconditions:
                - Given row and col must be valid.
                - Given seat must be unoccupied.
         */
        // If given seat position is invalid
        if(row < 0 || row >= NUMBER_OF_ROWS || col < 0 || col >= SEATS_PER_ROW) {
            System.out.printf("Given seat position must be valid in %d x %d seats.", NUMBER_OF_ROWS, SEATS_PER_ROW);
            System.exit(1);
        }

        // If ticket is already booked
        if(tickets[row][col].getBookingStatus()) {
            return null;
        }

        // If given seat is not initialized yet, initialize it and occupy.
        if(tickets[row][col] == null) {
            double price = _getRandomTicketPriceOfRow(row);
            Ticket newTicket = new Ticket(this.ID, row, col, price, true);
            tickets[row][col] = newTicket;
        } else {
            tickets[row][col].setBookingStatus(true);
        }

        Ticket copiedTicket = new Ticket(tickets[row][col]);
        this.revenue += copiedTicket.getPrice();
        this.occupiedCount++;

        return copiedTicket;
    }

    /*  Returns section's revenue */
    public double revenue() {
        return revenue;
    }

    /*  Returns section's occupied seat count. */
    public int occupiedCount() {
        return this.occupiedCount;
    }

    /*
        Updates section's ID, if given id is valid.
     */
    public void setID(int id) {
        /* Precondition:
            - ID must be non-negative.
            - Since ID is 0 indexed, it needs to be
            less than Number of Sections.
        */
        if(id < 0 || id >= NUMBER_OF_ROWS) {
            System.out.println("TicketBookingApplication.Core.Section ID must be valid in between.");
            System.exit(1);
        }

        this.ID = id;
    }

    /*
        Updates section's max price, if given max price
        is in between current max price boundaries.
     */
    public void setMaxPrice(int maxPrice) {
        /* Preconditions:
            - Given price must be in between max price boundaries.
            - Boundary Declaration: [min, max)
            (min included, max excluded)
        */
        if(maxPrice < this.maxPriceBoundaries[0] || maxPrice >= this.maxPriceBoundaries[1]) {
            System.out.printf("TicketBookingApplication.Core.Section's max. price must be in this boundary: [%d, %d)\n", this.maxPriceBoundaries[0], this.maxPriceBoundaries[1]);
            System.exit(1);
        }

        this.maxPrice = maxPrice;
    }

    /*
        Updates section's min price, if given min price
        is in between current min price boundaries.
     */
    public void setMinPrice(int minPrice) {
        /* Preconditions:
            - Given price must be in between min price boundaries.
            - Boundary Declaration: [min, max)
            (min included, max excluded)
        */
        if(minPrice < this.minPriceBoundaries[0] || minPrice >= this.minPriceBoundaries[1]) {
            System.out.printf("TicketBookingApplication.Core.Section's min. price must be in this boundary: [%d, %d)", this.minPriceBoundaries[0], this.minPriceBoundaries[1]);
            System.exit(1);
        }

        this.minPrice = minPrice;
    }

    /*
        Updates section's max price boundary, if given
        boundary is valid.
     */
    public void setMaxPriceBoundaries(int[] boundary) {
        /*
            Preconditions:
                - If there is already have a max price, then
                given boundary must include the current max price.
         */
        if(!isValidBoundary(boundary)) {
            System.out.println("Given max price boundary must be valid.");
            System.exit(1);
        }

        // If current max price initialized AND boundary does not contain current max price.
        if(this.maxPrice != -1 && (this.maxPrice < boundary[1] && this.maxPrice >= boundary[0])) {
            System.out.printf("Given max price boundary should include current max price [%d]", this.maxPrice);
            System.exit(1);
        }

        this.maxPriceBoundaries = boundary.clone();
    }

    /*
        Updates section's max price boundary, if given
        boundary is valid.
     */
    public void setMinPriceBoundaries(int[] boundary) {
        /*
            Preconditions:
                - If there is already have a min price, then
                given boundary must include the current min price.
         */
        if(!isValidBoundary(boundary)) {
            System.out.println("Given min price boundary must be valid.");
            System.exit(1);
        }

        // If current min price is initialized AND does given boundary contain current max price.
        if(this.minPrice != -1 && (this.minPrice < boundary[1] && this.minPrice >= boundary[0])) {
            System.out.printf("Given min price boundary should include current max price [%d]", this.maxPrice);
            System.exit(1);
        }

        this.minPriceBoundaries = boundary.clone();
    }

    /*
        Copies given tickets array's tickets to
        our tickets array.
     */
    public void setTickets(Ticket[][] givenTickets) {
        /*
            Preconditions:
                - Given array's row count and col count must be valid. !
                - Given Tickets section id must match current section's id. !
         */
        if(givenTickets[0][0].getSectionID() != this.ID) {
            System.out.printf("Given tickets' section id must match with original section. Expected (%d) but given (%d).", this.ID, givenTickets[0][0].getSectionID());
            System.exit(1);
        }
        if(givenTickets.length != NUMBER_OF_ROWS || givenTickets[0].length != SEATS_PER_ROW) {
            System.out.printf("Given tickets array's lengths must match original array's lengths. (%d x %d)", NUMBER_OF_ROWS, SEATS_PER_ROW);
            System.exit(1);
        }

        // Deep copy given tickets
        for(int i = 0; i < NUMBER_OF_ROWS; i++) {
            for(int j = 0; j < SEATS_PER_ROW; j++) {
                if(givenTickets[i][j] != null) {
                    // Copy current ticket
                    Ticket ticket = new Ticket(givenTickets[i][j]);
                    this.tickets[i][j] = ticket;
                } else {
                    tickets[i][j] = null;
                }
            }
        }
    }

    /*  Returns section's id. */
    public int getID() {
        return this.ID;
    }

    /*  Returns section's current maximum price. */
    public int getMaxPrice() {
        return this.maxPrice;
    }

    /*  Returns section's current minimum price. */
    public int getMinPrice() {
        return this.minPrice;
    }

    /*  Returns section's current maximum price boundaries [min, max) */
    public int[] getMaxPriceBoundaries() {
        return this.maxPriceBoundaries.clone();
    }

    /*  Returns section's current minimum price boundaries [min, max) */
    public int[] getMinPriceBoundaries() {
        return this.minPriceBoundaries.clone();
    }

    /*
        Returns copy of section's tickets array.
     */
    public Ticket[][] getTickets() {
        // Copy the original array first, and then return copied one.
        Ticket[][] copyTickets = new Ticket[NUMBER_OF_ROWS][SEATS_PER_ROW];
        for(int i = 0; i < NUMBER_OF_ROWS; i++) {
            for(int j = 0; j < SEATS_PER_ROW; j++) {
                if(tickets[i][j] != null) {
                    copyTickets[i][j] = new Ticket(tickets[i][j]);
                } else {
                    copyTickets[i][j] = null;
                }
            }
        }

        return copyTickets;
    }

    /*
        Returns random ticket price according to
        given row index.
        Index 0: Max Price
        Index 1: Max Price * .80
        Index (others): Random between minimum and maximum prices.
     */
    private double _getRandomTicketPriceOfRow(int row) {
        double ticketPrice = -1;
        if(row == 0) {
            ticketPrice = this.maxPrice;
        } else if (row == 1) {
            ticketPrice = this.maxPrice * .8;
        } else {
            Random rd = new Random();
            ticketPrice = rd.nextDouble(this.minPrice, this.maxPrice);
        }

        return ticketPrice;
    }

    /*
        Randomly declares min or max price according
        to given type (Max/Min). Declares random int
        between given boundary. Returns declared random price.
     */
    private int _declareRandomBoundaryPrice(int[] boundaries) {
        Random rd = new Random();
        return rd.nextInt(boundaries[0], boundaries[1]);
    }

    /*
        Checks whether given boundary is valid or not
        and returns related boolean result.
     */
    private boolean isValidBoundary(int[] boundary) {
       /*
            Preconditions:
                - Boundary [0] must be non-negative.
                - Given boundary's [0] must be less than [1].
                - Length of boundary must be 2.
         */
        return boundary[0] >= 0 && boundary[1] > boundary[0] && boundary.length == 2;
    }

}
