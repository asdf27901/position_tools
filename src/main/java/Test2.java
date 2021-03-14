import java.io.*;
import java.util.*;

public class Test2 {

    public static void main(String[] args) throws IOException {
        Set<String> set = new LinkedHashSet<>();
        //List<String> set = new ArrayList();
        Variable_Definit variableDefinit = new Variable_Definit();

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));
        System.out.print("输入要写入的文件名：");
        String fileName = sc.next();

        File file = new File(parentPath,fileName);

        variableDefinit.setWriter(new FileWriter(file,false));
        variableDefinit.setBw(new BufferedWriter(variableDefinit.getWriter()));

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            int startIndex = variableDefinit.str.indexOf("$GNGGA");
            int firstIndex = variableDefinit.str.indexOf("_");
            int endIndex = variableDefinit.str.lastIndexOf("$GNGGA");
            int rmcIndex = variableDefinit.str.indexOf("rmc");

            if (rmcIndex != -1 && startIndex == endIndex) set.add(variableDefinit.str.substring(startIndex,rmcIndex-2));

            if (endIndex - firstIndex == 1){
                set.add(variableDefinit.str.substring(startIndex,firstIndex));
                set.add(variableDefinit.str.substring(endIndex,rmcIndex-2));
            }

            if (firstIndex != -1 && (endIndex - firstIndex > 1)){
                set.add(variableDefinit.str.substring(startIndex,firstIndex));
                set.add(variableDefinit.str.substring(firstIndex+1,endIndex-1));
                set.add(variableDefinit.str.substring(endIndex,rmcIndex-2));
            }
        }

        for (String s : set) {
            variableDefinit.getBw().write(s);
            variableDefinit.getBw().newLine();
        }

        variableDefinit.closeStream();

    }


}
