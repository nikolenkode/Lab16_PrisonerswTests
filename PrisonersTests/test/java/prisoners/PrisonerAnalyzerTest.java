package prisoners;

import container.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;


public class PrisonerAnalyzerTest {

    private MyLinkedList<Prisoner> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
        list.add(new Prisoner("Ivan Petrenko", LocalDate.of(1990, 5, 10), 180, "green",
                new ArrayList<>(), LocalDate.of(2020, 1, 1), null));
        list.add(new Prisoner("Olena Koval", LocalDate.of(1992, 8, 20), 160, "gray",
                new ArrayList<>(), LocalDate.of(2021, 3, 15), null));
        list.add(new Prisoner("Taras Shevchenko", LocalDate.of(1985, 3, 9), 175, "green",
                new ArrayList<>(), LocalDate.of(2019, 1, 1), null));
    }

 
    @Test
    void testFindTallestPrisoner_normalList() {
        Prisoner tallest = PrisonerAnalyzer.findTallestPrisoner(list);
        assertNotNull(tallest);
        assertEquals("Ivan Petrenko", tallest.getFullName());
        assertEquals(180, tallest.getHeight());
    }

    @Test
    void testFindTallestPrisoner_emptyList() {
        assertNull(PrisonerAnalyzer.findTallestPrisoner(new MyLinkedList<>()));
    }

    @Test
    void testFindTallestPrisoner_singleElement() {
        MyLinkedList<Prisoner> single = new MyLinkedList<>();
        single.add(new Prisoner("Solo Person", LocalDate.of(2000, 1, 1), 170, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        Prisoner tallest = PrisonerAnalyzer.findTallestPrisoner(single);
        assertNotNull(tallest);
        assertEquals(170, tallest.getHeight());
    }

    @Test
    void testFindTallestPrisoner_allSameHeight() {
        MyLinkedList<Prisoner> same = new MyLinkedList<>();
        same.add(new Prisoner("Person A", LocalDate.of(1990, 1, 1), 175, "brown",
                new ArrayList<>(), LocalDate.now(), null));
        same.add(new Prisoner("Person B", LocalDate.of(1991, 1, 1), 175, "brown",
                new ArrayList<>(), LocalDate.now(), null));
        Prisoner tallest = PrisonerAnalyzer.findTallestPrisoner(same);
        assertNotNull(tallest);
        assertEquals(175, tallest.getHeight());
    }

    @Test
    void testFindTallestPrisoner_tallestIsLast() {
        MyLinkedList<Prisoner> reversed = new MyLinkedList<>();
        reversed.add(new Prisoner("Short", LocalDate.of(1990, 1, 1), 160, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        reversed.add(new Prisoner("Tall", LocalDate.of(1991, 1, 1), 195, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        Prisoner tallest = PrisonerAnalyzer.findTallestPrisoner(reversed);
        assertNotNull(tallest);
        assertEquals("Tall", tallest.getFullName());
        assertEquals(195, tallest.getHeight());
    }

    @Test
    void testCalculateAverageHeight_normalList() {
        double avg = PrisonerAnalyzer.calculateAverageHeight(list);
        assertEquals(171.66, avg, 0.01);
    }

    @Test
    void testCalculateAverageHeight_emptyList() {
        assertEquals(0.0, PrisonerAnalyzer.calculateAverageHeight(new MyLinkedList<>()));
    }

    @Test
    void testCalculateAverageHeight_singleElement() {
        MyLinkedList<Prisoner> single = new MyLinkedList<>();
        single.add(new Prisoner("Solo", LocalDate.of(2000, 1, 1), 170, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        assertEquals(170.0, PrisonerAnalyzer.calculateAverageHeight(single), 0.01);
    }

    @Test
    void testCalculateAverageHeight_twoElements() {
        MyLinkedList<Prisoner> two = new MyLinkedList<>();
        two.add(new Prisoner("A", LocalDate.of(2000, 1, 1), 160, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        two.add(new Prisoner("B", LocalDate.of(2000, 1, 1), 200, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        assertEquals(180.0, PrisonerAnalyzer.calculateAverageHeight(two), 0.01);
    }


    @Test
    void testCountByEyeColor_green() {
        assertEquals(2, PrisonerAnalyzer.countByEyeColor(list, "green"));
    }

    @Test
    void testCountByEyeColor_gray() {
        assertEquals(1, PrisonerAnalyzer.countByEyeColor(list, "gray"));
    }

    @Test
    void testCountByEyeColor_notPresent() {
        assertEquals(0, PrisonerAnalyzer.countByEyeColor(list, "blue"));
    }

    @Test
    void testCountByEyeColor_caseInsensitive() {
        assertEquals(2, PrisonerAnalyzer.countByEyeColor(list, "GREEN"));
        assertEquals(2, PrisonerAnalyzer.countByEyeColor(list, "Green"));
    }

    @Test
    void testCountByEyeColor_emptyList() {
        assertEquals(0, PrisonerAnalyzer.countByEyeColor(new MyLinkedList<>(), "green"));
    }

    @Test
    void testCountByEyeColor_allMatch() {
        MyLinkedList<Prisoner> blueEyes = new MyLinkedList<>();
        blueEyes.add(new Prisoner("A", LocalDate.of(1990, 1, 1), 170, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        blueEyes.add(new Prisoner("B", LocalDate.of(1991, 1, 1), 175, "blue",
                new ArrayList<>(), LocalDate.now(), null));
        assertEquals(2, PrisonerAnalyzer.countByEyeColor(blueEyes, "blue"));
    }
}
