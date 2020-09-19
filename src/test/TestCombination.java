/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import function.combination.Combination;
import function.combination.exception.InputException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class TestCombination {
    public static void main(String[] args) throws InputException {
        Combination combination = new Combination(5, 10, true);
        combination.build();
        ArrayList<ArrayList<Integer>> alInt = combination.getResult();
        for(ArrayList<Integer> alIntt: alInt){
            for(int i: alIntt){
                System.out.print(i+" ");
            }
            System.out.println("");
        }
    }
}
