package ticketbooking.core;

public class Ticket {
    private int sectionID;
    private int rowNumber;
    private int seatNumber;
    private double price;
    private boolean isBooked;

    /* No-argument Constructor */
    public Ticket() {
        System.out.println("You need to provide: sectionID [int], rowNumber [int], seatNumber [int], price [double], isBooked [boolean] to initialize TicketBookingApplication.Core.Ticket object.\nTerminating.");
        System.exit(1);
    }

    /* Full Constructor */
    public Ticket(int sectionID, int rowNumber, int seatNumber, double price, boolean isBooked) {
        setSectionID(sectionID);
        setRowNumber(rowNumber);
        setSeatNumber(seatNumber);
        setPrice(price);
        setBookingStatus(isBooked);
    }

    /* Copy Constructor */
    public Ticket(Ticket original) {
        if(original == null) {
            System.out.println("You need to provide already initialized TicketBookingApplication.Core.Ticket object to copy.\nTerminating.");
            System.exit(1);
        }
        setSectionID(original.getSectionID());
        setRowNumber(original.getRowNumber());
        setSeatNumber(original.getSeatNumber());
        setPrice(original.getPrice());
        setBookingStatus(original.getBookingStatus());
    }

    /* Returns ticket's section id */
    public int getSectionID() {
        return sectionID;
    }

    /* Returns ticket's row number. */
    public int getRowNumber() {
        return rowNumber;
    }

    /* Returns ticket's seat (column) number */
    public int getSeatNumber() {
        return seatNumber;
    }

    /* Returns ticket's price */
    public double getPrice() {
        return price;
    }

    /* Returns ticket's booking status */
    public boolean getBookingStatus() {
        return isBooked;
    }

    /*
        Changes TicketBookingApplication.Core.Ticket's section id (0 indexed) if
        given id is valid, otherwise terminates.
    */
    public void setSectionID(int sectionID) {
        /*
            Preconditions:
                - TicketBookingApplication.Core.Section with given ID (0 indexed) must exist.
         */
        if (sectionID < 0 || sectionID >= Venue.NUMBER_OF_SECTIONS) {
            System.out.printf("TicketBookingApplication.Core.Section ID must be in between [%d, %d).\nTerminating.", 0, Venue.NUMBER_OF_SECTIONS);
            System.exit(1);
        }
        this.sectionID = sectionID;
    }

    /*
        Changes ticket's row number (0 indexed).
    */
    public void setRowNumber(int rowNumber) {
        /*
            Preconditions:
                - Given row number (0 indexed) must be valid.
         */
        if (rowNumber < 0 || rowNumber >= Section.NUMBER_OF_ROWS) {
            System.out.println("Given row number must be valid.\nTerminating.");
            System.exit(1);
        }
        this.rowNumber = rowNumber;
    }

    /*
        Changes ticket's seat (column) number (0 indexed).
    */
    public void setSeatNumber(int seatNumber) {
        /*
            Preconditions:
                - Given seat number must be non-negative and
                less than seat count per row
         */
        if (seatNumber < 0 || seatNumber >= Section.SEATS_PER_ROW) {
            System.out.printf("Seat number must be in between [%d, %d)\nTerminating.", 0, Section.SEATS_PER_ROW);
            System.exit(1);
        }
        this.seatNumber = seatNumber;
    }

    /* Changes ticket's price. */
    public void setPrice(double newPrice) {
        /*
            Preconditions:
                - Given price must be non-negative.
         */
        if (newPrice < 0) {
            System.out.println("Price must be a positive number.\nTerminating.");
            System.exit(1);
        }
        this.price = newPrice;
    }

    /* Changes ticket's booking status */
    public void setBookingStatus(boolean newStatus) {
        this.isBooked = newStatus;
    }

    @Override
    public String toString() {
        return "TicketBookingApplication.Core.Section: " + (sectionID + 1) + " Row: " + (rowNumber + 1) + " Seat: " + (seatNumber + 1) + " - " + price + " TL";
    }
}
