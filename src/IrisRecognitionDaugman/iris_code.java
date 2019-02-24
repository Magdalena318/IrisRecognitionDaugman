/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IrisRecognitionDaugman;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class iris_code {
    int id;
    int session;
    int person;
    int num;
    ArrayList<Integer> code;
//    ArrayList<Integer> code_2;
//    ArrayList<Integer> code_3;
//    ArrayList<Integer> code_4;
//    ArrayList<Integer> code_5;
    
    iris_code(int id, int session, int person, int num, ArrayList<Integer> code){
        this.id = id;
        this.session = session;
        this.person = person;
        this.num = num;
        this.code = code;
    }
}
