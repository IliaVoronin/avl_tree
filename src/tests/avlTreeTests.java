package tests;

import main.avlTree;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class avlTreeTests {

    @Test
    public void add() {
        List newList1 = Arrays.asList(15, 10, 25, 5, 13, 24, 30, 4, 6, 12, 13, 3);
        avlTree<Integer> newAVL1 = new avlTree<>();
        newAVL1.addAll(newList1);
        assertTrue(newAVL1.containsAll(newList1));
        assertTrue(newAVL1.checkAVL());

        List newList2 = Arrays.asList(15, 12, 19, 13, 11, 18, 21, 10, 9);
        avlTree<Integer> newAVL2 = new avlTree<>();
        newAVL2.addAll(newList2);
        assertTrue(newAVL2.checkAVL());
        assertTrue(newAVL2.containsAll(newList2));
        assertEquals(9, newAVL2.size());

        List newList3 = Arrays.asList(14, 11, 18, 10, 12, 17, 20, 16, 19, 22, 26);
        avlTree<Integer> newAVL3 = new avlTree<>();
        newAVL3.addAll(newList3);
        assertTrue(newAVL3.containsAll(newList3));
        assertEquals(11, newAVL3.size());
        assertTrue(newAVL3.checkAVL());

        List newList4 = Arrays.asList(16, 12, 17, 18, 19);
        avlTree<Integer> newAVL4 = new avlTree<>();
        newAVL4.addAll(newList4);
        assertTrue(newAVL4.checkAVL());
        assertTrue(newAVL4.containsAll(newList4));

        List newList5 = Arrays.asList(121, 80, 141, 72, 91, 135, 151, 76, 101, 131, 111);
        avlTree<Integer> newAVL5 = new avlTree<>();
        newAVL5.addAll(newList5);
        assertTrue(newAVL5.containsAll(newList5));
        assertTrue(newAVL5.checkAVL());
        assertEquals(11, newAVL5.size());

        List newList6 = Arrays.asList(65, 55, 85, 80, 57, 50, 90, 49, 56, 83, 87, 95, 81);
        avlTree<Integer> newAVL6 = new avlTree<>();
        newAVL6.addAll(newList6);
        assertTrue(newAVL6.checkAVL());
        assertTrue(newAVL6.containsAll(newList6));
    }

    @Test
    public void remove() {
        List newList1 = Arrays.asList(100, 60, 120, 50, 70, 115, 130, 65, 80, 110, 90);
        avlTree<Integer> newAVL1 = new avlTree<>();
        newAVL1.addAll(newList1);
        newAVL1.remove(50);
        assertFalse(newAVL1.containsAll(newList1));
        assertFalse(newAVL1.contains(50));
        assertTrue(newAVL1.checkAVL());
        assertEquals(10, newAVL1.size());

        List newList2 = Arrays.asList(14, 11, 18, 10, 12, 17, 20, 16, 19, 22);
        avlTree<Integer> newAVL2 = new avlTree<>();
        newAVL2.addAll(newList2);
        newAVL2.remove(10);
        assertFalse(newAVL2.containsAll(newList2));
        assertFalse(newAVL2.contains(10));
        assertTrue(newAVL2.checkAVL());
        assertEquals(9, newAVL2.size());

        avlTree newTree3 = new avlTree();
        List newList3 = Arrays.asList(78, 45, 12, 89, 67, 36, 58, 13, 48);
        newTree3.addAll(newList3);

        newTree3.remove(45);
        assertFalse(newTree3.contains(45));
        assertTrue(newTree3.checkAVL());
        assertEquals(newTree3.size(), 8);

        newTree3.remove(78);
        assertFalse(newTree3.contains(78));
        assertTrue(newTree3.checkAVL());
        assertEquals(newTree3.size(), 7);

        newTree3.remove(13);
        assertFalse(newTree3.contains(13));
        assertTrue(newTree3.checkAVL());
        assertEquals(newTree3.size(), 6);

        newTree3.remove(89);
        assertFalse(newTree3.contains(89));
        assertTrue(newTree3.checkAVL());
        assertEquals(newTree3.size(), 5);

        newTree3.remove(58);
        assertFalse(newTree3.contains(58));
        assertTrue(newTree3.checkAVL());
        assertEquals(newTree3.size(), 4);


    }

    @Test
    public void first() {
        avlTree<Integer> avlTree = new avlTree<>();
        List newList1 = Arrays.asList(55, 91, 80, 12);
        avlTree.addAll(newList1);
        assertEquals(12, avlTree.first().intValue());
    }

    @Test
    public void last() {
        avlTree<Integer> avlTree = new avlTree<>();
        List newList1 = Arrays.asList(55, 91, 80, 12);
        avlTree.addAll(newList1);
        assertEquals(91, avlTree.last().intValue());
    }

    @Test
    public void iterator() {
        avlTree<Integer> newTree1 = new avlTree<>();
        List<Integer> newList1 = Arrays.asList(41, 32, 26, 22, 36, 33, 61, 57, 58, 67);
        newTree1.addAll(newList1);
        Collections.sort(newList1);
        Iterator<Integer> it = newTree1.iterator();
        List<Integer> newList2 = new ArrayList<>();
        while (it.hasNext()) {
            newList2.add(it.next());
        }
        assertEquals(newList1, newList2);
    }

    @Test
    public void isEmpty() {
        avlTree<Integer> newTree1 = new avlTree<>();
        assertTrue(newTree1.isEmpty());
    }


    @Test
    public void removeAll() {
        avlTree tree = new avlTree();
        List list1 = Arrays.asList(1, 2, 3, 4, 5);
        tree.addAll(list1);
        List list2 = Arrays.asList(2, 3, 4);
        tree.removeAll(list2);
        List list3 = Arrays.asList(1, 5);
        assertTrue(tree.containsAll(list3));
        assertEquals(tree.size(), 2);
    }
}

