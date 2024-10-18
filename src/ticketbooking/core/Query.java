package ticketbooking.core;

public class Query {
    Venue venue;

    public Query(Venue venue) {
        this.venue = new Venue(venue);
    }

    public Section highestRevenueSection() {
        Section[] sections = venue.getSections();
        Section maxSection = sections[0];
        for (int i = 1; i < sections.length; i++) {
            if (sections[i].revenue() > maxSection.revenue()) maxSection = sections[i];
        }
        return maxSection;
    }

    public double totalRevenue() {
        Section[] sections = venue.getSections();
        double sum = 0;
        for (Section currentSection: sections) {
            sum += currentSection.revenue();
        }
        return sum;
    }

    public double occupancyRate() {
        Section[] sections = venue.getSections();
        double sum = 0;
        for (Section currentSection: sections) {
            sum += currentSection.occupiedCount();
        }
        return sum / (Venue.NUMBER_OF_SECTIONS * Section.NUMBER_OF_ROWS * Section.SEATS_PER_ROW);
    }

    public Customer customerPaysMost(Customer[] customers) {
        Customer maxCustomer = customers[0];
        for (int i = 1; i < customers.length; i++) {
            if (customers[i].totalCost() > maxCustomer.totalCost()) maxCustomer = customers[i];
        }
        return new Customer(maxCustomer);
    }

    public Ticket mostExpensiveTicket(Customer[] customers) {
        Ticket[] ticketArray = customers[0].getTicketArray();
        Ticket maxTicket = ticketArray[0];
        for (Customer currentCustomer: customers) {
            ticketArray = currentCustomer.getTicketArray();
            for (Ticket currentTicket: ticketArray) {
                if (currentTicket.getPrice() > maxTicket.getPrice()) maxTicket = currentTicket;
            }
        }
        return maxTicket;
    }
}
