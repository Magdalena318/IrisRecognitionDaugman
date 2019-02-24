/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IrisRecognitionDaugman;

/**
 *
 * @author root
 */
public class iris {
    int w;
    int h;
    int[][] intencities;
             
    //normalization of an image im
    iris(image im, Circle pupil, Circle sclera, int steps_num_r, int steps_num_th){
        //init values for holding normalization
        w = steps_num_th;
        h = steps_num_r;
        intencities = new int[h][w];
        
        //variables for better looking of code
        double step_th = 2 * Math.PI / (double) steps_num_th;
        double step_r = 1 / (double) steps_num_r;
        double end_th = step_th * steps_num_th;
 
        
        //calculating sin and cos for each angle
        for(double th = 0; th < end_th; th += step_th){             
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
                intencities[coord_r][coord_th] = im.colors[0][y_r_th][x_r_th];
            }
        }
    }
    
    
}

