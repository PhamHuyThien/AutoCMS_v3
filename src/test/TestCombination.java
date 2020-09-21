/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import function.combination.Combination;
import function.combination.Permutation;
import function.combination.exception.InputException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class TestCombination {
    public static void main(String[] args) throws InputException {
        Permutation permutation = new Permutation(1, 4);
        permutation.build();
//        Combination combination = new Combination(3, 4);
//        combination.build();
    
        for(ArrayList<Integer> alIntt: permutation.getResult()){
            for(int i: alIntt){
                System.out.print(i+" ");
            }
            System.out.println("");
        }
    }
}
