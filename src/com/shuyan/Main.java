package com.shuyan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    /**
     * please run this program with flag -Xmx6g，since this program will need 4 GB memory to run
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        // this will need around 4 GB memory
        int[] times = new int[1000000001];
        String filePath = "10.in";
        AtomicInteger numGuards = new AtomicInteger(0);

        // update times arr when read the file
        readFileAndUpdateTimesArr(filePath, times, numGuards);

        int[] numTimesOnlyCoveredByTheGuard = new int[numGuards.get()];
        // update numTimesOnlyCoveredByTheGuard arr when read the file
        readFileAndUpdateNumTimesOnlyCoveredByTheGuardArr(filePath, times, numTimesOnlyCoveredByTheGuard);

        int minTimeCoveredByOneGuard = Arrays.stream(numTimesOnlyCoveredByTheGuard).min().orElse(0);
        int totalTimeCovered = 0;
        for (int i = 0; i < times.length; i++) {
            if (times[i] > 0) {
                totalTimeCovered++;
            }
        }

        System.out.println(totalTimeCovered - minTimeCoveredByOneGuard);
    }

    private static void readFileAndUpdateTimesArr(String filePath, int[] times, AtomicInteger numGuards) throws IOException {
        Files.lines(new File(filePath).toPath())
                .forEach(line -> {
                    String[] split = line.split(" ");
                    if (split.length == 1) {
                        numGuards.set(Integer.parseInt(split[0]));
                    } else if (split.length == 2) {
                        int start = Integer.parseInt(split[0]);
                        int end = Integer.parseInt(split[1]);
                        for (int i = start; i <= end; i++) {
                            times[i]++;
                        }
                    }
                });
    }

    private static void readFileAndUpdateNumTimesOnlyCoveredByTheGuardArr(
            String filePath, int[] times, int[] numTimesOnlyCoveredByTheGuard) throws IOException {
        AtomicInteger guardPosition = new AtomicInteger(0);
        Files.lines(new File(filePath).toPath())
                .forEach(line -> {
                    String[] split = line.split(" ");
                    if (split.length == 2) {
                        int start = Integer.parseInt(split[0]);
                        int end = Integer.parseInt(split[1]);
                        for (int i = start; i <= end; i++) {
                            if (times[i] == 1) {
                                numTimesOnlyCoveredByTheGuard[guardPosition.get()]++;
                            }
                        }
                        guardPosition.getAndIncrement();
                    }
                });
    }
}
