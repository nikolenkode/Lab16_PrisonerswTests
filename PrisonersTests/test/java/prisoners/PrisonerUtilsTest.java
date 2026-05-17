package prisoners;

import container.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class PrisonerUtilsTest {

    private MyLinkedList<Prisoner> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
        list.add(new Prisoner("Ivan Petrenko", LocalDate.of(1990, 1, 1), 180, "green",
                Arrays.asList("scar on face", "tattoo on arm"), LocalDate.now(), null));
        list.add(new Prisoner("Olena Koval", LocalDate.of(1992, 1, 1), 165, "gray",
                Arrays.asList("burn mark"), LocalDate.now(), null));
        list.add(new Prisoner("Taras Sydorenko", LocalDate.of(1985, 6, 15), 172, "brown",
                Arrays.asList("tattoo on neck", "piercing"), LocalDate.now(), null));
    }


    @Test
    void testValidFullName_latinValid() {
        assertTrue(PrisonerUtils.isValidFullName("Ivan Petrenko"));
        assertTrue(PrisonerUtils.isValidFullName("Olena Koval"));
        assertTrue(PrisonerUtils.isValidFullName("Anna Smith"));
    }

    @Test
    void testValidFullName_lowercaseFirstLetter() {
        assertFalse(PrisonerUtils.isValidFullName("ivan petrenko"));
        assertFalse(PrisonerUtils.isValidFullName("Ivan petrenko"));
    }

    @Test
    void testValidFullName_digitsPresent() {
        assertFalse(PrisonerUtils.isValidFullName("Ivan123"));
        assertFalse(PrisonerUtils.isValidFullName("Ivan1 Petrenko"));
    }

    @Test
    void testValidFullName_singleWord() {
        assertFalse(PrisonerUtils.isValidFullName("Ivan"));
    }

    @Test
    void testValidFullName_nullInput() {
        assertFalse(PrisonerUtils.isValidFullName(null));
    }

    @Test
    void testValidFullName_emptyString() {
        assertFalse(PrisonerUtils.isValidFullName(""));
    }

    @Test
    void testValidFullName_extraSpaces() {
        assertFalse(PrisonerUtils.isValidFullName("Ivan Ivanov Petrenko"));
    }


    @Test
    void testValidHeight_normalValues() {
        assertTrue(PrisonerUtils.isValidHeight("180"));
        assertTrue(PrisonerUtils.isValidHeight("165"));
        assertTrue(PrisonerUtils.isValidHeight("210"));
        assertTrue(PrisonerUtils.isValidHeight("100"));
    }

    @Test
    void testValidHeight_twoDigits() {
        assertFalse(PrisonerUtils.isValidHeight("99"));
        assertFalse(PrisonerUtils.isValidHeight("50"));
    }

    @Test
    void testValidHeight_threeHundredPlus() {
        assertFalse(PrisonerUtils.isValidHeight("300")); 
        assertFalse(PrisonerUtils.isValidHeight("350"));
        assertTrue(PrisonerUtils.isValidHeight("299"));  
    }

    @Test
    void testValidHeight_withUnits() {
        assertFalse(PrisonerUtils.isValidHeight("180cm"));
    }

    @Test
    void testValidHeight_nullInput() {
        assertFalse(PrisonerUtils.isValidHeight(null));
    }

    @Test
    void testValidHeight_emptyString() {
        assertFalse(PrisonerUtils.isValidHeight(""));
    }

    @Test
    void testValidHeight_withLetters() {
        assertFalse(PrisonerUtils.isValidHeight("abc"));
    }


    @Test
    void testSearchByFeatureRegex_findTattoo() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(list, "tattoo.*");
        assertEquals(2, found.size());
        assertEquals("Ivan Petrenko", found.get(0).getFullName());
        assertEquals("Taras Sydorenko", found.get(1).getFullName());
    }

    @Test
    void testSearchByFeatureRegex_findBurnMark() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(list, ".*burn.*");
        assertEquals(1, found.size());
        assertEquals("Olena Koval", found.get(0).getFullName());
    }

    @Test
    void testSearchByFeatureRegex_noneFound() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(list, "missing_feature");
        assertEquals(0, found.size());
    }

    @Test
    void testSearchByFeatureRegex_caseInsensitive() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(list, "SCAR.*");
        assertEquals(1, found.size());
        assertEquals("Ivan Petrenko", found.get(0).getFullName());
    }

    @Test
    void testSearchByFeatureRegex_partialMatch() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(list, ".*scar.*");
        assertEquals(1, found.size());
    }

    @Test
    void testSearchByFeatureRegex_prisonerMatchedOnceEvenWithMultipleFeatures() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(list, ".*on.*");
        boolean ivanFound = false;
        int ivanCount = 0;
        for (Prisoner p : found) {
            if (p.getFullName().equals("Ivan Petrenko")) { ivanCount++; ivanFound = true; }
        }
        assertTrue(ivanFound);
        assertEquals(1, ivanCount); 
    }

    @Test
    void testSearchByFeatureRegex_emptyList() {
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(new MyLinkedList<>(), "tattoo");
        assertEquals(0, found.size());
    }

    @Test
    void testSearchByFeatureRegex_prisonerWithNoFeatures() {
        MyLinkedList<Prisoner> singleList = new MyLinkedList<>();
        singleList.add(new Prisoner("No Features", LocalDate.of(2000, 1, 1), 170, "blue",
                Collections.emptyList(), LocalDate.now(), null));
        MyLinkedList<Prisoner> found = PrisonerUtils.searchByFeatureRegex(singleList, ".*");
        assertEquals(0, found.size());
    }
}
