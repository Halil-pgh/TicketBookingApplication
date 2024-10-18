package ticketbooking.utility;

import ticketbooking.core.Customer;
import ticketbooking.filesystem.FileIO;
import ticketbooking.core.Venue;

public class CustomerParser {

    public CustomerParser() {}

    /**
     * Parses the customer data and creates TicketBookingApplication.Core.Customer objects.
     * For each customer, books the tickets. The number of tickets is read from the customer data.
     * Adds the customers to the venue by booking the tickets.
     * @return an array of TicketBookingApplication.Core.Customer objects
     */
    public Customer[] parseFromFile(Venue venue, String filename) {
        String[][] customerData = FileIO.read(filename);
        Customer[] customers = new Customer[customerData.length];
        for (int i = 0; i < customerData.length; i++) {
            try {
                int numberOfTickets = Integer.parseInt(customerData[i][1]); // try catch ?
                customers[i] = new Customer(customerData[i][0], numberOfTickets);
                for (int j = 0; j < numberOfTickets; j++) {
                    customers[i].bookTicket(venue);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid data in " + filename + "'s " + i + 1 + " row.");
                System.out.println("Expected an integer, but found " + customerData[i][1] + ".");
                System.exit(1);
            }
        }
        return customers;
    }

}
