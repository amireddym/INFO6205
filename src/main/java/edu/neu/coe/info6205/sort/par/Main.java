package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {

        int threadsCount = 2;
        int arraySize = 50000;
        int cutOff = 5000;
        // Changing the threads and sizes for computing avg times for sorting
        processArgs(args);
        for(int i=1; i<6; i++) {

            for (int tc=1; tc<6; tc++) {

                System.out.println("Size of the Array ::: " + arraySize);
                ForkJoinPool pool = new ForkJoinPool(threadsCount);
                System.out.println("Current pool of threads ::: " + threadsCount);
                Random random = new Random();
                int[] array = new int[arraySize];
                ArrayList<Long> timeList = new ArrayList<>();

                for (int j = 1; j < arraySize/cutOff+1; j++) {

                    ParSort.cutoff = cutOff * j;
                    long timeTaken;
                    long startTime = System.currentTimeMillis();
                    for (int t = 0; t < 10; t++) {
                        for (int k = 0; k < array.length; k++) {
                            array[k] = random.nextInt(10000000);
                        }
                        ParSort.sort(array, 0, array.length, pool);
                    }
                    long endTime = System.currentTimeMillis();
                    timeTaken = endTime - startTime;
                    timeList.add(timeTaken);
                    System.out.println("Cutoff used ::: " + ParSort.cutoff + " , and time for 10 samples ::: " + timeTaken);
                }

                try{

                    FileOutputStream fileOutputStream = new FileOutputStream("./src/"+"arraySize-"+arraySize+"-thread"+threadsCount+".csv");
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    for(int index=0; index<timeList.size(); index++) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(cutOff*(index+1));
                        stringBuilder.append(",");
                        stringBuilder.append((double) timeList.get(index)/10);
                        stringBuilder.append("\n");
                        bufferedWriter.write(stringBuilder.toString());
                        bufferedWriter.flush();
                    }
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                threadsCount *= 2;
            }
            threadsCount = 2;
            arraySize*=2;

        }

    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
