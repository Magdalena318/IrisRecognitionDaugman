/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IrisRecognitionDaugman;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


import javax.imageio.ImageIO;

/**
 *
 * @author root
 */


public class image {
    //image data 
    int w;
    int h;
    int [][][] colors;
    
    //data for gaussian filter
    int[][] gauss_mask = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
    double gauss_divisor = 16;
    double gauss_offset = 0;
    
    image() {
        w = 862;
        h = 477;
        colors = new int[3][h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                        colors[0][i][j] = 255;
                        colors[1][i][j] = 255;
                        colors[2][i][j] = 255;
            }
        }       
    }
    
    image(int width, int height) {
        w = width;
        h = height;
        colors = new int[3][h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                        colors[0][i][j] = 255;
                        colors[1][i][j] = 255;
                        colors[2][i][j] = 255;
            }
        }       
    }
    
    //constructor initiating image data
    image(File img) throws IOException{
        BufferedImage image = ImageIO.read(img);

        w = image.getWidth();
        h = image.getHeight();
        colors = new int[3][h][w];

        int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w); 

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){

                        Color c = new Color(dataBuffInt[i*w+j]);

                        colors[0][i][j] = c.getRed();
                        colors[1][i][j] = c.getGreen();
                        colors[2][i][j] = c.getBlue();
            }
        }        
    }
    
    //converts image data into image
    public BufferedImage getBufferedImage(){
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color newColor = new Color(colors[0][i][j], colors[1][i][j], colors[2][i][j]);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }
    
    //resizes image to fit jLabel displaying it
    public BufferedImage Resize(int width, int height){
        int new_w;
        int new_h;
        if(w <= width && h <= height){
            return this.getBufferedImage();
        } else {
            double w_coeff = (double)width/w;
            double h_coeff = (double)height/h;
            if(w_coeff < h_coeff){
                new_w = (int)(w * w_coeff);
                new_h = (int)(h * w_coeff);
            } else {
                new_h = (int)(h * h_coeff);
                new_w = (int)(w * h_coeff);
            }
        }
        BufferedImage bi = new BufferedImage(new_w, new_h, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(this.getBufferedImage(), 0, 0, new_w, new_h, null);
        g2d.dispose();
        return bi;
    }   
    
    //gives pixel a color
    public void putPixel(int y, int x, int R, int G, int B){
        this.colors[0][y][x] = R;
        this.colors[1][y][x] = G;
        this.colors[2][y][x] = B;
    }
    
    //help function to keep values in borders [0, 255]
    int Truncate(double value){
		if(value > 255){
			value = 255;		
		} else if(value < 0){
			value = 0;
		}
		return (int)(value);
    }
    
    //convertes image to grayscale
    public void grayscale(){
        int value;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                value = (int) ((colors[0][i][j] + colors[1][i][j] + colors[2][i][j]) / 3);
                colors[0][i][j] = value;
                colors[1][i][j] = value;
                colors[2][i][j] = value;
            }
        }
    }
    
    //thresholding image, not changing the image data 
    public int[][] thresholding(double threshold){
        int[][] thresholded = new int[h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                    if(colors[0][i][j] + colors[1][i][j] + colors[2][i][j] > threshold){
                            thresholded[i][j] = 255;
                    } else {
                            thresholded[i][j] = 0;
                    }
            }
        } 
        return thresholded;
    }
    
    //normalizes image's histogram
    public void histogram_normalization(){
        int[][] hist =  new int[3][256];
        for(int i = 0; i < h; i++){
                for(int j = 0; j < w; j++){
                        hist[0][colors[0][i][j]]++;
                        hist[1][colors[1][i][j]]++;
                        hist[2][colors[2][i][j]]++;
                }
        } 

        int[][] LUT = new int[3][256];
        for (int i = 0; i < 3; i++){	
                int sum = 0;
                int full_sum = 0;
                for(int j = 0; j < 256; j++){
                        full_sum +=hist[i][j];
                }
            for(int j = 0; j < 256; j++){
                sum += hist[i][j];
                LUT[i][j] = 255 * sum / full_sum;
            }
        }


        for(int i = 0; i < h; i++){
                for(int j = 0; j < w; j++){
                        colors[0][i][j] = LUT[0][colors[0][i][j]];
                        colors[1][i][j] = LUT[1][colors[1][i][j]];
                        colors[2][i][j] = LUT[2][colors[2][i][j]];
                }
        } 
    }
    
    //apply gaussian filter
    public void gaussian_filter(){
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                int sum[] = new int[3];
                for(int s = 0; s < 3; s++){//initiate 
                        sum[s] = 0;
                        for (int k = -1; k < 2; k++){
                            for (int t = -1; t < 2; t++){
                                    int k1, t1;
                                    if(i + k < 0){
                                            k1 = 0;
                                    } else if(i + k >= h){
                                            k1 = h-1;
                                    } else{
                                            k1 = i + k;
                                    }
                                    if(j + t < 0){
                                            t1 = 0;
                                    } else if(j + t >= w){
                                            t1 = w-1;
                                    } else{
                                            t1 = j + t;
                                    }
                                    sum[s] += gauss_mask[k + 1][t + 1] * colors[s][k1][t1];
                            }
                        }
                        colors[s][i][j] = Truncate((Double.valueOf(sum[s])/gauss_divisor) + gauss_offset);
                }
            }
        } 
    }
    
    //creates horizontal projection for thresholded image
    public int[] horizontal_projection(int[][] newColors){
        int[] projection = new int[h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w ; j++) {
                if (newColors[i][j] == 0) {
                    projection[i] = projection[i] + 1;
                }
            }
        }
        return projection;
    }
    
    //creates vertical projection for thresholded image
    public int[] vertical_projection(int[][] newColors){
        int[] projection = new int[w];
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                if (newColors[i][j] == 0) {
                    projection[j] = projection[j] + 1;
                }
            }
        }
        return projection;
    }
    
    //finds maximum of a histogram
    public static int hist_max(int[] hist) {
        int max = 0;
        for (int i = 0; i < hist.length; i++) {
            if (hist[i] > max) {
                max = hist[i];
            }
        }
        return max;
    }
    
    //finds the longest segment in projection of values bigger the med
    public int[] find_length(int[] proj, int med){
        int i = 0;
        boolean in_segment = false;
        int start = 0;
        int end = 0;
        int tmp_len = 0;
        int[] data = new int[3];
        while(i < proj.length){
            
            if(!in_segment){
                if(proj[i] > med){
                    start = i;
                    //System.out.println("start: "+ start);
                    in_segment = true;
                }
            } else {
                if(proj[i] < med){
                    end = i - 1;
                    //System.out.println("end: "+ end);
                    in_segment = false;
                    if(end - start > tmp_len){
                        tmp_len = end - start;
                        data[0] = start;
                        data[1] = end;
                        data[2] = tmp_len;
                    }
                } else if (i == proj.length - 1){
                    end = i;
                    //System.out.println("end: "+ end);
                    in_segment = false;
                    if(end - start > tmp_len){
                        tmp_len = end - start;
                        data[0] = start;
                        data[1] = end;
                        data[2] = tmp_len;
                }
            }
            }
            i++;
        }
        return data;
    }
    
    //approximately calculates pupil's coordinates using histograms
    public Circle approximate_pupil_detection(){
        int[][] th = thresholding(50);
                
        //Finding projections
        int[] v_proj = vertical_projection(th);
        int[] h_proj = horizontal_projection(th);
        
        //Finding their maximums
        int v_max = hist_max(v_proj);
        int h_max = hist_max(h_proj);
        
        //System.out.println("max_v: "+ v_max);
        //System.out.println("max_h: "+ h_max);
        
        //int v_med = find_median(v_proj, v_max);
        //int h_med = find_median(h_proj, h_max);
        int v_med = v_max/5;
        int h_med = h_max/5;
        
        //System.out.println("med_v: "+ v_med);
        //System.out.println("med_h: "+ h_med);
        
        //Find longest section above median
        int[] v_length = find_length(v_proj, v_med);
        int[] h_length = find_length(h_proj, h_med);
        
        //System.out.println("start_v: "+ v_length[0]);
        //System.out.println("start_h: "+ h_length[0]);
        //System.out.println("end_v: "+ v_length[1]);
        //System.out.println("end_h: "+ h_length[1]);
        
        int x = (int)(v_length[0] + v_length[1])/2;
        int y = (int)(h_length[0] + h_length[1])/2;
        int r = Math.min(v_length[1] - v_length[0], h_length[1] - h_length[0]);
        //System.out.println("r: "+ r + "coord: " + x + ", " + y);
        

        Circle pupil = new Circle(x, y, r);
        return pupil;
    }
    
    //init structures of circles models (tables) for different  radiuses
    public ArrayList<ArrayList<Point>> circle_models(int start_r, int end_r){
        ArrayList<ArrayList<Point>> circle_models = new ArrayList<>();
        for(int r = start_r; r < end_r; r++){
            
            ArrayList<Point> circle = new ArrayList<>();
            
            for(int step = 0; step < 10000; step++){
                double angle = ((double)step * 2 * Math.PI)/10000;

                double sin = Math.sin(angle);
                double cos = Math.cos(angle);

                int d_y = (int)(r * sin);
                int d_x = (int)(r * cos);
                
                Point p = new Point(d_x, d_y);
                
                if(circle.contains(p) == false) circle.add(p);
            }
            
            circle_models.add(circle);
        }
        return circle_models;
    }
    
    //calculate sum for given radius
    double calculate_circle_sum(int x0, int y0, int r, ArrayList<Point> circle_model){
        double sum = 0;
        
        //calculate sum of pixels for corresponding radius
        int add = 0;
        for(int s = 0; s < circle_model.size(); s++){
            int y = y0 + circle_model.get(s).y;
            int x = x0 + circle_model.get(s).x;
            add += colors[0][y][x];                        
        }

        sum = (double)(add) / (2 * Math.PI * r);
        return sum;
    }
    
    public Circle segmentation_iris(Circle center, ArrayList<ArrayList<Point>> circle_models){
        //check for indexing
        int start_y = 1, start_x = 1;
        int end_y = h - 1, end_x = w - 1;
        if(center.y - center.r > 0){
            start_y = center.y - center.r;
        }
        if(center.x - center.r > 0){
            start_x = center.x - center.r;
        }
        if(center.y + center.r < h - 1){
            end_y = center.y + center.r;
        }
        if(center.x + center.r < w - 1){
            end_x = center.x + center.r;
        }
        

        //for each point
        Circle max_circle = new Circle();
        double max_diff = 0;
        
        int r_start = 30;
        
        for(int i = start_y; i < end_y; i++){
            for(int j = start_x; j < end_x; j++){                
                int distance_from_border = Math.min(Math.min(i, j), Math.min(w - j, h - i));
                
                double prev = 0; 
                //for every radius
                for(int r = r_start; r < distance_from_border; r++){                    
                    //calculate sum for given radius
                    double sum = calculate_circle_sum(j, i, r, circle_models.get(r - 1));
                    
                    
                    //find maximum absolute change between neighbour circles
                    double diff = 0;
                    if(r > r_start) diff = Math.abs(sum - prev);

                    if(diff > max_diff){
                        max_diff = diff;
                        max_circle = new Circle(j, i, r);
                    }
                    prev = sum;
                }                
            }
        }
        
        
        //drawing iris
        if(max_circle.r > 0){
            for(int i = 0; i < circle_models.get(max_circle.r - 1).size(); i++){
                putPixel(max_circle.y + circle_models.get(max_circle.r - 1).get(i).y, max_circle.x + circle_models.get(max_circle.r - 1).get(i).x, 255, 0, 0);
            }        
        }
        

        return max_circle;
    }
    
    public Circle segmentation_pupil(Circle sclera, ArrayList<ArrayList<Point>> circle_models){
        //instance which will contain found pupil
        Circle max_circle = new Circle();
        double max_diff = 0;
        int r_start = 7;
        
        double prev = 0; 
        //for every radius
        for(int r = r_start; r < Math.round(0.8 * sclera.r); r++){                    
            //calculate sum for given radius
            double sum = calculate_circle_sum(sclera.x, sclera.y, r, circle_models.get(r - 1));


            //find maximum absolute change between neighbour circles
            double diff = 0;
            if(r > r_start) diff = Math.abs(sum - prev);

            if(diff > max_diff){
                max_diff = diff;
                max_circle = new Circle(sclera.x, sclera.y, r);
            }
            prev = sum;
        }     
        
        //drawing pupil
         if(max_circle.r > 0){
            for(int i = 0; i < circle_models.get(max_circle.r - 1).size(); i++){
                putPixel(max_circle.y + circle_models.get(max_circle.r - 1).get(i).y, max_circle.x + circle_models.get(max_circle.r - 1).get(i).x, 255, 0, 0);
            }        
        }
        
        
        return max_circle;
    }
    
    image normalization(Circle pupil, Circle sclera, int steps_num_r, int steps_num_th){
        //new imag for holding normalization
        image norm = new image(steps_num_th, steps_num_r);
        
        //variables for better looking of code
        double step_th = 2 * Math.PI / (double) steps_num_th;
        double step_r = 1 / (double) steps_num_r;
        double end_th = step_th * steps_num_th;
 
        
        //calculating sin and cos for each angle
        for(double th = 0; th < end_th - step_th/2; th += step_th){             
            double sin = Math.sin(th);
            double cos = Math.cos(th);

            //searching for corresponding polar coords 
            for(double r = 0; r < 1; r += step_r){
                double scaled_r = (double) pupil.r + ((double)(sclera.r - pupil.r) * r);
                
                int x_r_th = pupil.x + (int) Math.round(cos * scaled_r);
                int y_r_th = pupil.y + (int) Math.round(sin * scaled_r);               
                
                int coord_r = (int) Math.round(r / step_r);
                int coord_th = (int) Math.round(th / step_th);

                //assigning there values to normalized iris
                norm.putPixel(coord_r, coord_th, colors[0][y_r_th][x_r_th], colors[0][y_r_th][x_r_th], colors[0][y_r_th][x_r_th]);
            }
        }
        
        return norm;
    }
    
    public image cut(image im, int c_up, int c_d){
        image iris = new image(im.w, im.h - c_up - c_d);
        for(int i = c_d; i < im.h - c_up; i++){
            for(int j = 0; j < im.w; j++){
                iris.putPixel(i - c_d, j, im.colors[0][i][j], im.colors[0][i][j], im.colors[0][i][j]);
            }
        }
        return iris;
    }
    
    public void median_filter(int window_width, int window_height){
        int[][] tmp = new int[h][w];
        
        //copying of array
        for(int k = 0; k < h; k++){
            System.arraycopy(colors[0][k], 0, tmp[k], 0, w);       
        }
        
        int edge_x = Math.round(window_width / 2);
        int edge_y = Math.round(window_height / 2);
        
        //foreach pixel
        for(int i = edge_y; i < h - edge_y; i++){
            for(int j = edge_x; j < w - edge_x; j++){
                
                //allocate window
                //int window = new int[window_height][window_width];
                ArrayList<Integer> window = new ArrayList<>();
                for(int f_y = 0; f_y < window_height; f_y++){
                    for(int f_x = 0; f_x < window_width; f_x++){
                        window.add(colors[0][i + f_y - edge_y][j + f_x - edge_x]);
                    }
                }
                
                //sort window in ascending order
                Collections.sort(window);
                
                //choose median
                tmp[i][j] = window.get(Math.round(window.size() / 2));
            }        
        }
        
        //copying values back
        for(int i = 0; i <  h; i++){
            for(int j = 0; j < w; j++){
                this.putPixel(i, j, tmp[i][j], tmp[i][j], tmp[i][j]);
            }
        }
    }
    
    public int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for(int i=numbers.length - 1; i>=0; i--)
            if(numbers[i]=='1')
                result += Math.pow(2, (numbers.length-i - 1));
        return result;
    }
    
    public void LBP(){
        int[][] tmp = new int[h][w];
        
        //copying of array
        for(int k = 0; k < h; k++){
            System.arraycopy(colors[0][k], 0, tmp[k], 0, w);       
        }
        
        for(int i = 1; i < h - 1; i++){
            for(int j = 1; j < w - 1; j++){   
                //init neighbours
                int[] neighbours = new int[8];
                neighbours[0] = colors[0][i - 1][j - 1];
                neighbours[1] = colors[0][i - 1][j];
                neighbours[2] = colors[0][i - 1][j + 1];
                neighbours[3] = colors[0][i][j + 1];
                neighbours[4] = colors[0][i + 1][j + 1];
                neighbours[5] = colors[0][i + 1][j];
                neighbours[6] = colors[0][i + 1][j - 1];
                neighbours[7] = colors[0][i][j - 1];
                
                //for holding values after thresholding
                String binary = new String();
                
                //thresholding of heighbourhood
                for(int k = 0; k < 8; k++){
                    if(colors[0][i][j] > neighbours[k])
                        binary += "0";
                    else binary += "1";
                }
                
                tmp[i][j] = binaryToInteger(binary);
            }
            
        }
        
        //copying values back
        for(int i = 0; i <  h; i++){
            for(int j = 0; j < w; j++){
                this.putPixel(i, j, tmp[i][j], tmp[i][j], tmp[i][j]);
            }
        }
    }
    
    public ArrayList<Integer> chunked_encoding(){
        ArrayList<Integer> features = new ArrayList<>();
        
        //calculating mean
        double mean = 0;
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                mean += colors[0][i][j];
            }
        }
        mean /= w * h;
        
        //calculating standard deviation
        double sd = 0;
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                sd += Math.pow(colors[0][i][j] - mean, 2);
            }
        }
        sd /= w * h;
        sd = Math.sqrt(sd);
        
        double m_prev = 0;
        double s_prev = 0;
        
        //blocks 8*8
        for (int a = 0; a < h; a += 8){
            for (int b = 0; b < w; b += 8){
                //calculating local meanlocal mean
                double m = 0;
                for (int i = a; i < (a + 8); i++){
                    for (int j = b; j < (b + 8); j++){
                         m += colors[0][i][j];
                    }                   
                }
                m /= 64;
                
                //calculating local standard deviation
                double s = 0;
                for (int i = a; i < (a + 8); i++){
                    for (int j = b; j < (b + 8); j++){
                        s += Math.pow(colors[0][i][j] - m, 2);
                    }                   
                }
                s /= 64;
                s = Math.sqrt(s);
                
                if (m > mean) features.add(1);
                else features.add(0);
                if (s > sd) features.add(1);
                else features.add(0);
                if (m_prev > mean) features.add(1);
                else features.add(0);
                if (s_prev > sd) features.add(1);
                else features.add(0);

                m_prev = m;
                s_prev = s;
            }
        }
        
        return features;
    }
    

    
    public image iris_detection(){
        //preprocessing
        this.grayscale();
        this.histogram_normalization();
        this.gaussian_filter();
       // this.median_filter(5, 5);
        
        //approximate finding of pupil using histograms
       // Circle center = approximate_pupil_detection();
        Circle center = new Circle(w/2, h/2, h/4);

        //init structures of circles models (tables)
        ArrayList<ArrayList<Point>> circle_models = circle_models(1, Math.round(Math.min(h, w)/2));
        
        //segmentation of outer border of iris
        Circle sclera = segmentation_iris(center, circle_models);
        
        //segmentation of inner border of iris
        Circle pupil = segmentation_pupil(sclera, circle_models);
             
        //normalization
        image iris = normalization(pupil, sclera, 48, 360);

        //cutting 
        iris = cut(iris, 16, 0);
        
        //median filter
        iris.median_filter(2, 2);
        
        //LBP
        iris.LBP();
             
        return iris;       
    }
    
}
