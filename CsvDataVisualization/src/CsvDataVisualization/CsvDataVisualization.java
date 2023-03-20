package CsvDataVisualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CsvDataVisualization {
    public static void main(String args[]) {
        CsvDataVisualization csvDataVisualization = new CsvDataVisualization();
        try {
            csvDataVisualization.visualization();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void visualization() throws Exception {
        ArrayList<String[]> lines = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter csv file path : ");
        String filePath = sc.next().trim();
        File file = new File(filePath);
        String val = "";
        BufferedReader reader;

        int checkLength = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            while (true) {
                val = reader.readLine();

                if (val == null) {
                    break;
                } else if (!val.trim().isEmpty()) {
                    String array[] = val.split(",");
                    if (checkLength == 0) {
                        checkLength = array.length;
                    } else if (array.length != checkLength) {
                        throw new Exception("Invalid values found");
                    }
                    lines.add(array);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (lines.size() == 0) {
            System.out.println("Empty file");
        } else {
            System.out.println("The CSV file contains header ? [yes]/No");
            String containsHeader = sc.next().toLowerCase().trim();
            boolean containsCheck = (containsHeader.isEmpty() || containsHeader.equals("yes"));

            String headNames[];
            if (containsCheck) {
                headNames = lines.get(0);
            } else {
                // System.out.println(lines.get(0).length);
                headNames = new String[lines.get(0).length];
                for (int i = 0; i < lines.get(0).length; i++) {
                    headNames[i] = "Column-" + (i + 1);

                }
            }
            // /home/ranjini-zstk321/eclipse-workspace/stationary/Customer.csv

            System.out.println("Column names : " + Arrays.toString(headNames));
            System.out.println("Enter column name (e.g : [all]/Column-1,Column-2,Column-3,....) ");
            String columnsToPrint = sc.next();
            System.out.println("columnsToPrint " + columnsToPrint);
            int printColumnName[];
            if (columnsToPrint.trim().toLowerCase().equals("all") || columnsToPrint.trim().isEmpty()) {
                printColumnName = new int[headNames.length];
                for (int i = 0; i < headNames.length; i++) {
                    printColumnName[i] = i;
                }

            } else {
                String arr[] = columnsToPrint.split(",");
                printColumnName = new int[arr.length];

                for (int i = 0; i < arr.length; i++) {
                    int k = -1;
                    for (int j = 0; j < headNames.length; j++) {
                        if (arr[i].trim().equals(headNames[j].trim())) {
                            k = j;
                        }
                    }
                    if (k == -1) {
                        throw new Exception("Invalid column name" + arr[i]);
                    } else {
                        System.out.println(k);
                        printColumnName[i] = k;
                    }
                }

            }

            int rowCount = (containsCheck) ? lines.size() - 1 : lines.size();
            System.out.println("Total Row : " + rowCount);
            System.out.println("Enter rows range to print : ([all] / 0-n)");
            String row = sc.next().trim();
            int startRow;
            int endRow;
            if (row.toLowerCase().equals("all") || row.isEmpty()) {
                startRow = (containsCheck) ? 1 : 0;
                endRow = lines.size();
            } else {
                String arr[] = row.split("-");

                if (arr.length == 2) {
                    startRow = Integer.parseInt(arr[0]);
                    endRow = Integer.parseInt(arr[1]);
                } else if (arr.length == 1) {
                    startRow = Integer.parseInt(arr[0]);
                    endRow = Integer.parseInt(arr[0])+1;
                } else {
                    throw new Exception("Invalid input");
                }

                if (startRow < 0 || startRow > lines.size() || endRow < startRow || endRow > lines.size()) {
                    throw new Exception("Invalid start row/end row");
                } else if (containsCheck) {
                    startRow = startRow + 1;
                }

            }

            String[] arrayToPrint = new String[printColumnName.length];

            for (int i = startRow; i < endRow; i++) {

                String[] line = lines.get(i);
                for (int j : printColumnName) {
                    arrayToPrint[j] = line[j];
                }
                System.out.println(Arrays.toString(arrayToPrint));
            }
        }
    }
}
