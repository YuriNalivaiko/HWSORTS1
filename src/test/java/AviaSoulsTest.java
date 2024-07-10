import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class AviaSoulsTest {

    private AviaSouls aviasouls;

    @BeforeEach
    public void setup() {
        aviasouls = new AviaSouls();
        aviasouls.add(new Ticket("MOW", "NYC", 1000, 800, 1200));
        aviasouls.add(new Ticket("MOW", "NYC", 1500, 900, 1300));
        aviasouls.add(new Ticket("MOW", "PAR", 2000, 700, 1100));
        aviasouls.add(new Ticket("MOW", "NYC", 800, 600, 1000));
    }

    @Test
    public void testSearchAndSortByPrice() {
        Ticket[] tickets = aviasouls.search("MOW", "NYC");
        Assertions.assertEquals(3, tickets.length);
        Assertions.assertTrue(tickets[0].getPrice() < tickets[1].getPrice());
        Assertions.assertTrue(tickets[1].getPrice() < tickets[2].getPrice());
    }

    @Test
    public void testSearchAndSortByTime() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] tickets = aviasouls.searchAndSortBy("MOW", "NYC", comparator);
        Assertions.assertEquals(3, tickets.length);
        Assertions.assertTrue(
                (tickets[0].getTimeTo() - tickets[0].getTimeFrom()) <=
                        (tickets[1].getTimeTo() - tickets[1].getTimeFrom())
        );
        Assertions.assertTrue(
                (tickets[1].getTimeTo() - tickets[1].getTimeFrom()) <=
                        (tickets[2].getTimeTo() - tickets[2].getTimeFrom())
        );
    }

    @Test
    public void testMultipleTicketsFound() {
        Ticket[] expected = {
                new Ticket("MOW", "NYC", 800, 600, 1000),
                new Ticket("MOW", "NYC", 1000, 800, 1200),
                new Ticket("MOW", "NYC", 1500, 900, 1300)
        };
        Ticket[] actual = aviasouls.search("MOW", "NYC");
        Arrays.sort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testSingleTicketFound() {
        Ticket[] expected = { new Ticket("MOW", "PAR", 2000, 700, 1100) };
        Ticket[] actual = aviasouls.search("MOW", "PAR");
        Arrays.sort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testNoTicketsFound() {
        Ticket[] expected = {};
        Ticket[] actual = aviasouls.search("MOW", "LON");
        Assertions.assertArrayEquals(expected, actual);
    }
}