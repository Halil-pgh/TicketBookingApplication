package ticketbooking.core;

import ticketbooking.utility.SeatRandomizer;

public class Customer {
    private String name;
    private int ticketCount; /* Always smaller than tickets.length */
    private int sectionID;
    private Ticket[] tickets;
    private static final SeatRandomizer randomSeatSelector = new SeatRandomizer();

    // Full constructor
    public Customer(String name, int ticketsToBook) {
        this.name = name;
        this.ticketCount = 0;
        this.tickets = new Ticket[ticketsToBook];
        this.sectionID = randomSeatSelector.getSectionID();
    }
    // Empty constructor (which is invalid)
    public Customer() {
        System.out.println("Empty constructor is not allowed for class TicketBookingApplication.Core.Customer. Exiting program");
        System.exit(1);
    }
    // Copy constructor
    public Customer(Customer customerToCopy) {
        this.name = customerToCopy.getName();
        this.ticketCount = customerToCopy.getTicketCount();
        this.tickets = customerToCopy.getTicketArray().clone();
        this.sectionID = customerToCopy.getSectionIndex() + 1;
    }
    /**
     *  Preconditions: ticket array is not full
     *
     *  Postconditions: a ticket is added to the array, the ticket is marked as booked in the venue, ticketCount incremented
     *  Returns the booked ticket
     */
    public Ticket bookTicket(Venue venue) {
        Ticket ticket = null;
        if (ticketCount < tickets.length) {
            while (ticket == null) {
                int[] seatInArrayForm = randomSeatSelector.generateSeat(sectionID);
                ticket = venue.bookTicket(seatInArrayForm[0], seatInArrayForm[1], seatInArrayForm[2]);
            }
            tickets[ticketCount] = ticket;
            ticketCount++;
        }
        else {
            System.out.println("Error occured due to booking more tickets than intended. Exiting program.");
            System.exit(1);
        }
        return ticket;
    }
    /**
     * Preconditions: ticket array is full
     *
     * Postcontitions: ticket array is untouched
     * Returns total cost of all tickets booked by this customer
     */
    public double totalCost() {
        double sum = 0;
        if (ticketCount == tickets.length) {
            for (Ticket currentTicket : tickets) {
                sum += currentTicket.getPrice();
            }
        }
        else {
            System.out.println("TicketBookingApplication.Core.Ticket array is not filled properly, exiting program");
            System.exit(1);
        }
        return sum;
    }
    /**
     * Preconditions: class intialized properly, ticketCount does not exceed ticket capacity
     *
     * Postcontitions: ticket array is untouched
     * Returns how many tickets this customer has
     */
    public int getTicketCount() {
        if (ticketCount <= tickets.length) {
            return this.ticketCount;
        }
        else {
            System.out.println("Fatal error: ticketCount is larger than array size");
            System.exit(1);
            return 0;
        }
    }
    /**
     * Preconditions: class initialized properly
     *
     * Postconditions: -
     * Returns name of the customer
     */
    public String getName() {
        return this.name;
    }
    /**
     * Preconditions: class initialized properly
     *
     * Postcontitions: -
     * Returns a copy of the ticketArray for this customer
     */
    public Ticket[] getTicketArray() {
        return tickets.clone();
    }
    /**
     * Preconditions: class initialized properly
     *
     * Postcontitions: -
     * Returns which section the customer is booking tickets from
     */
    public int getSectionIndex() {
        return sectionID - 1;
    }

    /**
     * Preconditions: class initialized properly, ticket array is full
     *
     * Postconditions: -
     * Prints information of the customer (name, tickets)
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (ticketCount == tickets.length) {
            result.append(name).append("'s Booked Tickets:\n");
            for (Ticket ticket: tickets) {
                result.append(ticket);
                result.append("\n");
            }
        }
        else {
            System.out.println("TicketBookingApplication.Core.Ticket array is not filled properly, exiting program");
            System.exit(1);
        }
        return result.toString();
    }
}
