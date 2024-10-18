package ticketbooking.core;

import ticketbooking.utility.CustomerParser;

public class TicketBookingApplication {

    Venue venue;
    Customer[] customers;

    public static void main(String[] args) {
        TicketBookingApplication application = new TicketBookingApplication();
        application.run();
    }

    // Initialization of the application
    public TicketBookingApplication() {
        venue = new Venue();
        CustomerParser parser = new CustomerParser();
        customers = parser.parseFromFile(venue, "files/customers.csv");
    }

    // Running the application with the queries
    public void run() {
        Query query = new Query(venue);
        System.out.println("TicketBookingApplication.Core.Customer who pays the most: " + query.customerPaysMost(customers));
        System.out.println("Most expensive ticket: " + query.mostExpensiveTicket(customers) + "\n");
        System.out.println("Highest revenue section: " + query.highestRevenueSection());
        System.out.println("Total revenue: " + query.totalRevenue() + " TL");
        System.out.println("Occupancy rate: " + query.occupancyRate() + "%");
    }

}
