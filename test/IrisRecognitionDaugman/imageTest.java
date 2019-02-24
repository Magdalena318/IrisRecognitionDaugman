/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IrisRecognitionDaugman;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class imageTest {
    static image img;
    
    public imageTest() throws IOException{
        File im;
        im = new File("images.jpg");
        img = new image(im);
    }
    
    //@BeforeClass
    public static void setUpClass() throws IOException{

    }
    
    //@AfterClass
    public static void tearDownClass() {
    }
    
    //@Before
    public void setUp() throws IOException{
        
    }
    
    //@After
    public void tearDown() {
    }

    /**
     * Test of getBufferedImage method, of class image.
     */
   // @Test
    public void testGetBufferedImage() {
        System.out.println("getBufferedImage");
        image instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.getBufferedImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Resize method, of class image.
     */
    //@Test
    public void testResize() {
        System.out.println("Resize");
        int width = 0;
        int height = 0;
        image instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.Resize(width, height);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putPixel method, of class image.
     */
   // @Test
    public void testPutPixel() {
        System.out.println("putPixel");
        int y = 0;
        int x = 0;
        int R = 0;
        int G = 0;
        int B = 0;
        image instance = null;
        instance.putPixel(y, x, R, G, B);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Truncate method, of class image.
     */
   // @Test
    public void testTruncate() {
        System.out.println("Truncate");
        double value = 0.0;
        image instance = null;
        int expResult = 0;
        int result = instance.Truncate(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grayscale method, of class image.
     */
   // @Test
    public void testGrayscale() {
        System.out.println("grayscale");
        image instance = null;
        instance.grayscale();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of thresholding method, of class image.
     */
   // @Test
    public void testThresholding() {
        System.out.println("thresholding");
        double threshold = 0.0;
        image instance = null;
        int[][] expResult = null;
        int[][] result = instance.thresholding(threshold);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of histogram_normalization method, of class image.
     */
   // @Test
    public void testHistogram_normalization() {
        System.out.println("histogram_normalization");
        image instance = null;
        instance.histogram_normalization();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gaussian_filter method, of class image.
     */
   // @Test
    public void testGaussian_filter() {
        System.out.println("gaussian_filter");
        image instance = null;
        instance.gaussian_filter();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of horizontal_projection method, of class image.
     */
  //  @Test
    public void testHorizontal_projection() {
        System.out.println("horizontal_projection");
        int[][] newColors = null;
        image instance = null;
        int[] expResult = null;
        int[] result = instance.horizontal_projection(newColors);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vertical_projection method, of class image.
     */
   // @Test
    public void testVertical_projection() {
        System.out.println("vertical_projection");
        int[][] newColors = null;
        image instance = null;
        int[] expResult = null;
        int[] result = instance.vertical_projection(newColors);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hist_max method, of class image.
     */
   // @Test
    public void testHist_max() {
        System.out.println("hist_max");
        int[] hist = null;
        int expResult = 0;
        int result = image.hist_max(hist);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find_length method, of class image.
     */
  //  @Test
    public void testFind_length() {
        System.out.println("find_length");
        int[] proj = null;
        int med = 0;
        image instance = null;
        int[] expResult = null;
        int[] result = instance.find_length(proj, med);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of approximate_pupil_detection method, of class image.
     */
    //@Test
    public void testApproximate_pupil_detection() {
        System.out.println("approximate_pupil_detection");
        image instance = img;
        Circle expResult = new Circle(131, 80, 8);
        Circle result = instance.approximate_pupil_detection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of circle_models method, of class image.
     */
 //   @Test
    public void testCircle_models() {
        System.out.println("circle_models");
        int start_r = 0;
        int end_r = 0;
        image instance = null;
        ArrayList<ArrayList<Point>> expResult = null;
        ArrayList<ArrayList<Point>> result = instance.circle_models(start_r, end_r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculate_circle_sum method, of class image.
     */
    @Test
    public void testCalculate_circle_sum() {
        System.out.println("calculate_circle_sum");
        int x0 = 131;
        int y0 = 80;
        int r = 9;
        
        image instance = img;
        ArrayList<Point> circle_model = instance.circle_models(8,  10).get(1);
        double expResult = 0.0;
        double result = instance.calculate_circle_sum(x0, y0, r, circle_model);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iris_detection method, of class image.
     */
  //  @Test
    public void testIris_detection() {
        System.out.println("iris_detection");
        image instance = img;
        image expResult = null;
        image result = instance.iris_detection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
