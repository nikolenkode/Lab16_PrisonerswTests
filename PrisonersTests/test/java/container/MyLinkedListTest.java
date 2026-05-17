package container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Iterator;

public class MyLinkedListTest {

    private MyLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }


    @Test
    void testAddAndSize() {
        assertTrue(list.isEmpty());
        list.add("First");
        list.add("Second");
        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void testAddSingleElement() {
        list.add("Only");
        assertEquals(1, list.size());
        assertEquals("Only", list.get(0));
    }


    @Test
    void testGet() {
        list.add("A");
        list.add("B");
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    void testGetThrowsOnNegativeIndex() {
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void testGetThrowsOnOutOfBounds() {
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void testGetThrowsOnEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }


    @Test
    void testRemoveByElementMiddle() {
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(list.remove("B"));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    void testRemoveByElementHead() {
        list.add("A");
        list.add("B");
        assertTrue(list.remove("A"));
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    void testRemoveByElementNotFound() {
        list.add("A");
        assertFalse(list.remove("Z"));
        assertEquals(1, list.size());
    }

    @Test
    void testRemoveByElementOnEmptyList() {
        assertFalse(list.remove("X"));
    }


    @Test
    void testRemoveByIndex() {
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(list.remove(1));
        assertEquals(2, list.size());
        assertEquals("C", list.get(1));
    }

    @Test
    void testRemoveByIndexFirst() {
        list.add("A");
        list.add("B");
        assertTrue(list.remove(0));
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    void testRemoveByIndexOutOfBounds() {
        list.add("A");
        assertFalse(list.remove(10));
        assertFalse(list.remove(-1));
    }

    // --- clear ---

    @Test
    void testClear() {
        list.add("A");
        list.add("B");
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void testClearEmptyList() {
        list.clear(); 
        assertTrue(list.isEmpty());
    }


    @Test
    void testContainsTrue() {
        list.add("Apple");
        assertTrue(list.contains("Apple"));
    }

    @Test
    void testContainsFalse() {
        list.add("Apple");
        assertFalse(list.contains("Banana"));
    }

    @Test
    void testContainsOnEmptyList() {
        assertFalse(list.contains("X"));
    }

    @Test
    void testToArray() {
        list.add("X");
        list.add("Y");
        Object[] array = list.toArray();
        assertEquals(2, array.length);
        assertEquals("X", array[0]);
        assertEquals("Y", array[1]);
    }

    @Test
    void testToArrayEmpty() {
        Object[] array = list.toArray();
        assertEquals(0, array.length);
    }


    @Test
    void testIterator() {
        list.add("One");
        list.add("Two");
        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("One", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("Two", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorOnEmptyList() {
        Iterator<String> iterator = list.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testForEachLoop() {
        list.add("A");
        list.add("B");
        list.add("C");
        int count = 0;
        for (String s : list) {
            assertNotNull(s);
            count++;
        }
        assertEquals(3, count);
    }


    @Test
    void testFilterMatchesSome() {
        list.add("Apple");
        list.add("Banana");
        list.add("Apricot");
        MyLinkedList<String> filtered = list.filter(s -> s.startsWith("A"));
        assertEquals(2, filtered.size());
        assertEquals("Apple", filtered.get(0));
        assertEquals("Apricot", filtered.get(1));
    }

    @Test
    void testFilterMatchesNone() {
        list.add("Banana");
        list.add("Cherry");
        MyLinkedList<String> filtered = list.filter(s -> s.startsWith("Z"));
        assertEquals(0, filtered.size());
        assertTrue(filtered.isEmpty());
    }

    @Test
    void testFilterMatchesAll() {
        list.add("Apple");
        list.add("Avocado");
        MyLinkedList<String> filtered = list.filter(s -> s.startsWith("A"));
        assertEquals(2, filtered.size());
    }

    @Test
    void testFilterOnEmptyList() {
        MyLinkedList<String> filtered = list.filter(s -> true);
        assertEquals(0, filtered.size());
    }


    @Test
    void testSaveAndLoadSerialized() throws Exception {
        list.add("Alpha");
        list.add("Beta");

        File tmp = File.createTempFile("mylist", ".dat");
        tmp.deleteOnExit();
        list.saveToFileSerialized(tmp.getAbsolutePath());

        MyLinkedList<String> loaded = MyLinkedList.loadFromFileSerialized(tmp.getAbsolutePath());
        assertEquals(2, loaded.size());
        assertEquals("Alpha", loaded.get(0));
        assertEquals("Beta", loaded.get(1));
    }
}
