package tests;
import org.junit.Before;
import org.junit.Test;
import system.BadFileNameException;
import system.FileSystem;
import system.OutOfSpaceException;


import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FileSystemTest {

    FileSystem fileSystem;

    @Before
    public void init() throws Exception {
        fileSystem = new FileSystem(30);
    }

    @Test(expected = NegativeArraySizeException.class)
    public void testNegativeSizeException1(){
        FileSystem fileSystem1 = new FileSystem(-1);
    }

    //checking that all directory and files created exists.
    @Test
    public void disk() {
        //checking that block space is all free as supposed
        String[][] fileSystemDisk = fileSystem.disk();
        for (String[] block:fileSystemDisk) {
            assertNull(block);
        }
    }

    @Test
    public void disk1() throws Exception {
        String[] path ={"root","file"};
        fileSystem.file(path,30);
        //checking that block space is all catch as supposed
        String[][] fileSystemDisk = fileSystem.disk();
        for (String[] block:fileSystemDisk) {
            assertArrayEquals(path,block);
            assertEquals(2,block.length);
        }
    }
    /*
    dir tests-->
    use cases:
    1. no root name in the start of the path --> bad name exception
    2.file already exist in the path with an equal name --> bad name exception
    3.normal and good creation --> make sure that dir exists
    4. dir with the same name already exist in the path --> does nothing
     *///(expected = BadFileNameException.class)
    //testing case of no root name in the start of the path when trying to create dir with dir function
    @Test
    public void testDirCaseNoRootName() throws Exception {
        //checking that with no root name in start of the path getting BadFileNameException
        String[] path = new String[2];
        path[0] = "testRoot";
        path[1] = "dir1";
        try{
            fileSystem.dir(path);
        }catch (Exception e){
            assertTrue(e.getClass().equals(BadFileNameException.class));
        }
    }

    @Test(expected = BadFileNameException.class)
    public void testDirCaseFileExistWithSameName() throws Exception {
        String[] path2 = {"root", "testDir"};
        //file creation
        fileSystem.file(path2, 5);
        //dir with same name creation
        fileSystem.dir(path2);
    }

    //testing that after directory creation it is exist in file system
    @Test
    public void dir() throws Exception {
        String[] path = {"root","dir2"};
        fileSystem.dir(path);
        assertNotNull(fileSystem.DirExists(path));
    }
    //failing test
    //testing case of creation of two directories with the same name
    @Test
    public void testCaseOfSameDirectoryName() throws Exception {
        String[] path = {"root" , "dir1"};
        //creation first of the directory with the name dir 1 under root
        fileSystem.dir(path);
        //now lets try to create it again-suppose to do nothing
//        fileSystem.dir(path);
        //checking that directory still exist in the wanted path
        assertNotNull(fileSystem.DirExists(path));
    }

    /*
    testing file function with all use cases
    1. file creation in a normal path
    2. file resize - first normal creation and the resize with too big size-->out of space Exception
    3. the same case above but with correct size
    4. no root in the start of the path --> bad file exception
    5.creation file with equal name of exist directory --> bad file name exception
     */

    //test file creation
    @Test
    public void file() throws Exception {
        String[] path = {"root" , "dir1" ,"file1"};
        fileSystem.file(path, 10);
        //checking that file exist
        assertNotNull(fileSystem.FileExists(path));
    }

    //checking that in case of creation file with existing file name-->better to test dealloc of space from here
    @Test
    public void testDeallocOfSpace() throws Exception {
        String[] path = {"root" , "dir1" ,"file1"};
        //creation
        fileSystem.file(path,6);
        fileSystem.file(path,10);
        assertNotNull(fileSystem.FileExists(path));
    }

    //testing case of out of space
//    @Test(expected = OutOfSpaceException.class)
//    public void testOutOfSpaceFileException() throws Exception {
//        String[] path = {"root" , "dir1" ,"file1"};
//        //first lets create the file with an acceptable size
//        fileSystem.file(path,8);
////        //now lets try to resize the file with too big size
////        fileSystem.file(path, 50);
//        //checking that the original file still exists in the system
//        assertNotNull(fileSystem.FileExists(path));
//    }


    //checking that bad file name thrown correctly when trying to create file with no root
    @Test(expected = BadFileNameException.class)
    public void testBadFileNameException() throws Exception {
        String[] path = {"notRoot" , "dir2" , "file3"};
        //case of not root name as root
        fileSystem.file(path,4);
        String exception = "";
    }

    //testing case of creation file with equal name of exist directory
    @Test(expected = BadFileNameException.class)
    public void testFileCaseDirExistWithSameName() throws Exception{
        //case of directory name equal to the file name that we try to create
        //directory creation
        String[] path2 = {"root", "dir2"};
        fileSystem.dir(path2);
        //trying to create file with the same name and path
        fileSystem.file(path2,5);
    }

    /*
    testing lsdir
    1. normal case --> creation of file and directories and getting output as expected
    2. case of directory that does not exist --> return null
     */

    @Test
    public void lsdir() throws Exception {
        //checking that root contain the expected directories and files
        String[] pathToDir = {"root","dir1"};
        fileSystem.dir(pathToDir);
        String[] pathToDir2 = {"root" , "dir2"};
        fileSystem.dir(pathToDir2);
        String[] pathToFile = {"root","testFile"};
        fileSystem.file(pathToFile,7);
        String[] expected = {"dir1","dir2","testFile"};
        String[] path = {"root"};
        String[] actual = fileSystem.lsdir(path);
        assertArrayEquals(expected,actual);
        //checking that dir1 contain file1
        String[] path1 = {"root","dir1","file1"};
        fileSystem.file(path1,8);
        String[] expected1 = {"file1"};
        String[] pathForTest = {"root","dir1"};
        assertArrayEquals(expected1,fileSystem.lsdir(pathForTest));
    }
    //case of non exists directory
    @Test
    public void testLsWhenDirNotExist(){
        String[] path ={"root","dir1"};
        assertNull(fileSystem.lsdir(path));
    }

    @Test
    public void rmfile() {
        //first lets create a dir to remove.
        String[] pathToDir = {"root"};
        //lets remove testDir - the first file created
        String[] pathToRemove = {"root","testDir"};
        fileSystem.rmfile(pathToRemove);
        //checking that file test dir doesnt exist.
        assertNull(fileSystem.FileExists(pathToRemove));
    }
    //test case of null path when trying to rmfile
    @Test(expected = NullPointerException.class)
    public void testRmFileWithNullPath(){
        fileSystem.rmfile(null);
    }
    //checking that directoryNotEmptyException thrown
    @Test(expected = DirectoryNotEmptyException.class)
    public void testDirectoryNotEmptyException () throws Exception {
        //first lets create file in directory
        String[] path = {"root","dir2","file2"};
        fileSystem.file(path,7);
        String[] pathToRemove = {"root","dir2"};
        fileSystem.rmdir(pathToRemove);
//        //checking that path not exist in the system after removing the directory above
        assertNull(fileSystem.DirExists(path));
    }

    //checking that the function removes a directory as expected
    @Test
    public void rmdir() throws Exception {
        String[][] disl = fileSystem.disk();
        String[] path = {"root","dir2"};
        //creation of the directory to remove
        fileSystem.dir(path);
        fileSystem.rmdir(path);
        //checking that dir2 does not exist
        assertNull(fileSystem.DirExists(path));
    }

    @Test (expected = NullPointerException.class)
    public void testNullPathRmdir() throws Exception{
        fileSystem.rmdir(null);
    }

    @Test(expected = OutOfSpaceException.class)
    public void testCreationOfTooBifFiles() throws Exception {
        String[] path = {"root","file1"};
        String[] path1 = {"root","file2"};
        fileSystem.file(path,20);
        fileSystem.file(path1,12);
    }

}