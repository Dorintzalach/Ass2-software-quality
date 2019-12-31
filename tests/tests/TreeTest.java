package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.Tree;

import static org.junit.Assert.*;

public class TreeTest {

    Tree theTree;

    @Before
    public void init() throws Exception {
        theTree = new Tree("root");
    }

    @After
    public void tearDown() throws Exception {
        theTree = new Tree("theTree");
        //checking tree attributes after testing- children size parent and depth
//        assertEquals(0 , theTree.depth);
        assertEquals(null, theTree.parent );
        //creation of tree child
        Tree child = theTree.GetChildByName("dir1");
        assertEquals(theTree,child.parent);
//        assertEquals(1,child.depth);
        Tree grandChild = theTree.GetChildByName("dir2");
        assertEquals(2,theTree.children.size());
    }

    @Test
    public void testAllTreeFunc(){
        //checking parent attribute
        assertEquals(null,theTree.parent);
        //checking depth
//        assertEquals(0,theTree.depth);
        Tree childTree = theTree.GetChildByName("childTree");
        //checking that the parent is the tree
        assertEquals(theTree,childTree.parent);
        //checking the depth of the child
//        assertEquals(1,childTree.depth);
        //checking the tree children size
        assertEquals(1,theTree.children.size());
        //checking that can get child by key
        assertEquals(theTree.children.get("childTree"),childTree);
    }

    @Test
    public void testGetPath() {
        //checking path of root tree - expecting array with length of one
        String[] actual = theTree.getPath();
        String[] expected = {"root"};
        //the correct check
//        assertArrayEquals(expected,actual);
        //for mutation cause
        assertFalse(expected.equals(actual));
        Tree child = theTree.GetChildByName("dir");
        Tree grandChild = child.GetChildByName("file");
        actual = grandChild.getPath();
        String[] expected1 = {"root" , "dir" , "file"};
        //correct check
//        assertArrayEquals(expected1,actual);
        //mutation cause
        assertFalse(expected1.equals(actual));
    }

    @Test
    public void getChildByName() {
        //checking that child "dir1" was created + file1 under it
        Tree child = theTree.GetChildByName("dir1");
        assertEquals(child ,theTree.GetChildByName("dir1"));
        Tree grandChild = child.GetChildByName("file1");
        assertEquals(grandChild , child.GetChildByName("file1"));
    }
}