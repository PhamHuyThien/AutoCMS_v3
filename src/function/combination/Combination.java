/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function.combination;

import function.combination.exception.InputException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class Combination {

    private final int k;
    private final int n;

    private boolean allResult;

    private ArrayList<ArrayList<Integer>> alInt;
    private boolean build;
    
    public Combination(int k, int n) {
        this.k = k;
        this.n = n;
        initCombination();
    }

    public Combination(int k, int n, boolean allResult) {
        this.k = k;
        this.n = n;
        this.allResult = allResult;
        initCombination();
    }

    public void build() throws InputException {
        if(build){
            return;
        }
        build = !build;
        if(k<1){
            throw new InputException("K >= 1!");
        }
        if(n<1){
            throw new InputException("N >= 1!");
        }
        if(k>n){
            throw new InputException("K <= N!");
        }
        for (int i = k; i <= (allResult ? n : k); i++) {
            int arrTmp[] = createTmpArrCombination(i+1);
            combination(i, n, 1, arrTmp);
        }
    }
    
    public ArrayList<ArrayList<Integer>> getResult() throws InputException{
        build();
        return alInt;
    }
    
    private void combination(int k, int n, int i, int[] arrTmp) {
        for (int j = arrTmp[i - 1] + 1; j <= n - k + i; j++) {
            arrTmp[i] = j;
            if (i == k) {
                ArrayList<Integer> alIntTmp = new ArrayList<>();
                for (int m = 1; m <= k; m++) {
                    alIntTmp.add(arrTmp[m] - 1);
                }
                this.alInt.add(alIntTmp);
            } else {
                combination(k, n, i + 1, arrTmp);
            }
        }
    }

    private void initCombination() {
        alInt = new ArrayList<>();
    }

    private static int[] createTmpArrCombination(int count) {
        int resInt[] = new int[count];
        for (int i = 0; i < count; i++) {
            resInt[i] = 0;
        }
        return resInt;
    }

    private static int countCombination(int k, int n) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    //hỗ trợ combination
    private static int factorial(int n) {
        int tmp = 1;
        for (int i = n; i > 1; i--) {
            tmp *= i;
        }
        return tmp;
    }
}
