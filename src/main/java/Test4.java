import java.io.*;
import java.util.Scanner;

public class Test4 {

    public static void main(String[] args) throws IOException {
        Variable_Definit variableDefinit = new Variable_Definit();

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        int count = 0;
        int start = 0;
        int end = 0;
        int time = 0;
        int preTime = 0;
        int passedTime = 0;
        int max = 0;
        int min = 10000;
        int sumTime = 0;
        Boolean flag = false;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            if (variableDefinit.str.contains("cors url")){

                try{
                    time = Integer.parseInt(variableDefinit.str.substring(variableDefinit.str.indexOf("[")+1,variableDefinit.str.indexOf("]")));
                    if (flag == true){
                        flag = false;
                        preTime = time;
                        continue;
                    }
                }catch (NumberFormatException e){
                    flag = true;
                    continue;
                }

                count++;
                if (count != 1 && time - preTime > 0){
                    passedTime = time - preTime;
                    sumTime += passedTime;
                    //end = time;
                }else if (count != 1 && time - preTime < 0){
                    count = 1;
                    sumTime = 0;
                    max = 0;
                    min = 10000;
                    passedTime = 0;
                }//else start = time;

               preTime = time;

                if (passedTime > max) max = passedTime;
                if (count != 1 && passedTime <= min) min = passedTime;

                //if (end - start > 360000) break;
            }
        }

        System.out.println("平均CORS频率："+String.format("%.2f",(double)sumTime/count)+"------>"+String.format("%.2f",(double)(sumTime*5/count)/1000.0)+"秒");
        System.out.println("最大CORS频率："+max+"------>"+(max*5)/1000.0+"秒");
        System.out.println("最小CORS频率："+min+"------>"+(min*5)/1000.0+"秒");

        variableDefinit.getInputStream().close();
        variableDefinit.getBufferedReader().close();

    }
}
