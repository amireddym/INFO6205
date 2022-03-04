package edu.neu.coe.info6205.union_find;

import edu.neu.coe.info6205.graphs.BFS_and_prims.StdRandom;

import java.util.Scanner;

public class UFClient {

    public static int count(int n){
        UF_HWQUPC ufObj = new UF_HWQUPC(n);
        int m = 0;
        while(ufObj.components()>1){
            int a= StdRandom.uniform(n),b=StdRandom.uniform(n);//Generating random numbers (a,b) with uniform distribution
            if(!ufObj.isConnected(a,b)){//checking if both the components are connected or not
                ufObj.union(a,b);//perform union if they are not connected
            }
            m++;
        }
        return m;
    }

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Number of Sites(n): ");
        int sites=scan.nextInt();
        int trailsNo=25;

        //Using Doubling Method
        for(int i=sites;i<3000000;i+=i){
            double sum=0;
            for (int j=0;j<trailsNo;j++){
                sum+=count(i);
            }
            System.out.println("Number of objects (n): " + i + ", Number of pairs (m) :" + sum/trailsNo);//computing the average
        }
    }
}
