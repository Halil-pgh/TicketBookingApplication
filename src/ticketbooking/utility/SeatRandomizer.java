package ticketbooking.utility;

import ticketbooking.core.Section;
import ticketbooking.core.Venue;

import java.util.Random;

public class SeatRandomizer {
    private final Random randomizer;

    public SeatRandomizer() {
        randomizer = new Random();
    }
    public int getSectionID() {
        return randomizer.nextInt(Venue.NUMBER_OF_SECTIONS);
    }
    public int[] generateSeat(int section) {
        int[] seatInArrayForm = new int[3];
        seatInArrayForm[0] = section;
        seatInArrayForm[1] = randomizer.nextInt(Section.NUMBER_OF_ROWS);   // row number
        seatInArrayForm[2] = randomizer.nextInt(Section.SEATS_PER_ROW);    // seat number
        return seatInArrayForm;
    }
}
