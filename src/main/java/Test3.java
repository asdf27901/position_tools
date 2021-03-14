import java.io.*;
import java.util.Scanner;

public class Test3 {

    public static void main(String[] args) throws IOException {
        Variable_Definit variableDefinit = new Variable_Definit();

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        int count = 0;
        int age;
        int min = 100;
        int max = 0;
        int sumAge = 0;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
           if (variableDefinit.str.contains("nmea:$GNGGA,")){

              variableDefinit.str = variableDefinit.str.substring(variableDefinit.str.indexOf("nmea:$GNGGA"));
              String[] aa = variableDefinit.str.split(",");

               if (aa[13].equals("")) continue;

               count++;
               age = Integer.parseInt(aa[13].split("\\.")[0]);
               sumAge += age;

               if (age > max) max = age;
               if (age <= min) min = age;

           }
        }

        System.out.println("平均差分龄期："+String.format("%.2f",(double)sumAge/count));
        System.out.println("最大差分龄期："+max);
        System.out.println("最小差分龄期："+min);

        variableDefinit.getInputStream().close();
        variableDefinit.getBufferedReader().close();

    }
}
