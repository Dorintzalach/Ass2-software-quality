package tests;

import org.junit.Before;
import org.junit.Test;
import system.Space;

import static org.junit.Assert.*;

public class SpaceTest {

    Space mySpace;

    @Before
    public void init() throws Exception {
        mySpace = new Space(20);
    }

    @Test
    public void afterTest() throws Exception {
        //checking that the file exists
        assertEquals(20, mySpace.getAlloc().length);
//        assertTrue(20==mySpace.getAlloc().length);
        //checking that we fot the right amount of space
        assertEquals(20, mySpace.countFreeSpace());
//        assertTrue(20==mySpace.countFreeSpace());

    }

//    @Test
//    public void alloc() {
//        int size = 5;
//        LeafStub leafStub = null;
//        try {
//            leafStub = new LeafStub("file1",size);
//        } catch (OutOfSpaceException e) {
//            e.printStackTrace();
//        }
//        try {
//            space.Alloc(5,leafStub);
//        } catch (OutOfSpaceException e) {
//            e.printStackTrace();
//        }
//        //checking that in order of more leaf created, the storage change as expected
//        assertEquals(15, space.countFreeSpace());
//        assertEquals(5,space.getAlloc().length);
//    }
//
//    @Test
//    public void dealloc(){
//        //creating a new leaf that does not exist in the space to check if dealloc any way
//        LeafStub leafStub = null;
//        try {
//            leafStub = new LeafStub("file2" , 5);
//        } catch (OutOfSpaceException e) {
//            e.printStackTrace();
//        }
//        space.Dealloc(leafStub);
//        assertEquals(15, space.countFreeSpace());
//        assertEquals(5,space.getAlloc().length);
//    }
//
//    @Test
//    public void countFreeSpace() {
//    }
//
//    @Test
//    public void getAlloc() {
//    }
}