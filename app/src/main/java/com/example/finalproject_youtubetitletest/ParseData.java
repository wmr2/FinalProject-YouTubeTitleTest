package com.example.finalproject_youtubetitletest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ParseData {
    public static List<String> getNames(InputStream input) {
        List<String> results = new ArrayList<String>();
        List<String> titlesOut = new ArrayList<>();
        //BufferedReader reader;
        try {
            /*URL path = ClassLoader.getSystemResource("Data.txt");
            System.out.println(path);*/
            //List<String> lines = new ArrayList<>();
            //String path = context.getFilesDir().getPath();
            int read = 0;
            StringBuilder fullRead = new StringBuilder();
            while (read != -1) {
                byte[] temp = new byte[100000];
                try {
                    read = input.read(temp);
                    //System.out.println(read);
                    //System.out.println(new String(temp));
                    fullRead = fullRead.append(new String(temp));
                } catch (Exception e) {
                    System.out.println("can't read");
                    e.printStackTrace();
                    break;
                }
            }
            String haveRead = fullRead.toString();
            String thing = "{\"etag\"";
            String[] lines = haveRead.split(",\"pageInfo\":");
            //reader = new BufferedReader(new FileReader(path + "/Data.txt"));
            //reader = new BufferedReader(new FileReader(path + "/Data.txt"));
            /*
            String line = reader.readLine();
            System.out.println("hi lul");
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            //System.out.println(lines.get(3));
            */

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                //System.out.println(lines[i]);
                try {
                    if (line.split("\"\"")[1].split("\"items\"")[0].equals(",")) {
                        //System.out.println(line);
                        String[] titles = line.split("\",\"title\":\"");
                        //System.out.println("hi");
                        //System.out.println(titles[1]);
                        for (int j = 1; j < titles.length; j++) {
                            String title = titles[j];
                            title = title.split("\"")[0];
                            //System.out.println(title);
                            titlesOut.add(title);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("probably normal");
                }
            }
            return titlesOut;
        } catch (Exception e) {
            System.out.println("bad file path");
            System.out.println(System.getProperty("user.dir"));
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }
        //return System.getProperty("user.dir");
    }
    /*
    public static void main(String[] args) {
        System.out.println(getNames());
    }*/
}
