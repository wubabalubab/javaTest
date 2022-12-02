package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {


    private static final String type1 = "project";
    private static final String type2 = "subModule";
    private static final String type3 = "subDep";
    private static final String type4 = "otherDep";

    private static final String flag1 = "+---";
    private static final String flag2 = "|";

    private static final String flag7 = "com.cmccit:";
    private static final String flag3 = "project";
    private static final String flag4 = "\\---";
    private static final String flag5 = "->";

    private static final String flag6 = "(*)";
    private static final String path = "C:\\Users\\ss\\Desktop\\orgion.txt";
    private static int index;
    private static BaseNode rootNode;

    private static final String targetFile="C:\\Users\\ss\\Desktop\\target.txt";

    private static List<String> orgionList = new ArrayList<>();
    private static List<String> targetList;

    public static void main(String[] args) {

        targetList = new ArrayList<>();
        getTargetList(targetList);

        String temp = "|||androidx.lifecycle:lifecycle-runtime:2.3.1 -> 2.4.0 (*)";
//        String temp="|||androidx.lifecycle:lifecycle-runtime:2.3.1 (*)";
//        System.out.println(parseLine(temp));

        rootNode = new BaseNode();
        rootNode.setName("app");
        rootNode.setType("project");
        rootNode.setId(0);
        rootNode.setParentNode(null);
        index = 0;


        parseFile();

        excute();
        rootNode.getChildList(targetList);

    }

    private static void getTargetList(List<String> targetList) {
        File fileTarget=new File(targetFile);
        try (InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(fileTarget))){
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String tempTarget=bufferedReader.readLine();
            while (tempTarget != null) {
                targetList.add(tempTarget);
                tempTarget=bufferedReader.readLine();
            }
        }catch (Exception e){

        }
    }

    private static void excute() {
        //第一个表示层数
        HashMap<Integer, BaseNode> mapStack = new HashMap<>();
        mapStack.put(0, rootNode);
        for (int i = 0; i < orgionList.size(); i++) {

            BaseNode baseNode = parseLine(orgionList.get(i), i + 1);

            int stack = headSpaceNum(orgionList.get(i));
            mapStack.put(stack, baseNode);

            mapStack.get(stack - 5).setChildBean(baseNode);
            for (Map.Entry<Integer, BaseNode> entry : mapStack.entrySet()) {

            }
        }

//        rootNode.printForName("basemodule");
//        rootNode.getChildList(targetList);

        for (BaseNode baseNode : rootNode.getChildList()) {
            if (type1.equals(baseNode.getType())) {
                for (BaseNode node : baseNode.getChildList()) {
                    System.out.println(node.getName());
                }
            }else {
                System.out.println(baseNode.getName());
            }
        }
    }

    public static int headSpaceNum(String line) {
        int num = 0;
        for (int i = 0; i < line.length(); i++) {
            if (!Character.isLowerCase(line.charAt(i)) && !Character.isUpperCase(line.charAt(i)) && (line.charAt(i) != ":".charAt(0))) {
                num++;
            } else {
                break;
            }
        }
        return num;
    }

    public static BaseNode getBaseNode() {
        BaseNode baseNode = new BaseNode();


        return baseNode;
    }

    public static void parseFile() {
        File file = new File(path);
        String line = "";
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file))) {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null) {

                orgionList.add(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static BaseNode parseLine(String line, int id) {
        BaseNode childNode = new BaseNode();
        childNode.setId(id);
//        System.out.println(">>>" + id);
        //第一个分号位置
        int startIndex = 0;
        if (line.contains(":")) {
            int firstIndex = line.indexOf(":");
            for (int i = 0; i < line.length(); i++) {
                if (Character.isLowerCase(line.charAt(i)) || Character.isUpperCase(line.charAt(i))) {
                    startIndex = i;
                    break;
                }
            }
            if (line.contains(flag3)) {
                childNode.setType(type1);
            }
            if (line.contains(flag7)) {
                childNode.setType(type2);
            }
            if (line.replaceFirst(":", "").contains(":")) {
                childNode.setPath(line.substring(startIndex, firstIndex));
                if (line.contains(":")) {
                    //第二个：位置
                    int lastIndex = line.replaceFirst(":", "").indexOf(":");
                    if (lastIndex != -1) {
                        childNode.setName(line.substring(firstIndex + 1, lastIndex+1));
                        if (line.contains(flag5)) {
                            int start = line.indexOf(flag5);
                            for (int i = start + 2; i < line.length(); i++) {
                                if (line.charAt(i) == " ".charAt(0) || Character.isSpaceChar(line.charAt(i))) {
                                    childNode.setVersion(line.substring(start + 2, i));
                                }
                            }
                        } else {
                            for (int i = lastIndex + 1; i < line.length(); i++) {
                                if (line.charAt(i) == " ".charAt(0) || Character.isSpaceChar(line.charAt(i))) {
                                    childNode.setVersion(line.substring(lastIndex + 2, i));
                                }
                            }
                        }

                    }
                } else {
                    childNode.setName(line.substring(firstIndex + 1));
                }
            } else {
                childNode.setName(line.substring(firstIndex + 1));
            }


        }
        if (line.contains(type1)) {
            childNode.setType(type1);
        }

        childNode.setLastList(line.contains(flag4));
        return childNode;
    }


}
