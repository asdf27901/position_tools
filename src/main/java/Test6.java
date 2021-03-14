import java.io.*;
import java.util.Scanner;

public class Test6 {

    public static void main(String[] args) throws IOException {

//        Variable_Definit variableDefinit = new Variable_Definit();
//        System.out.print("输入读取的文件路径(绝对路径)：");
//        Scanner sc = new Scanner(System.in);
//        String inputPath = sc.next();
//
//        variableDefinit.setInputStream(new FileInputStream(inputPath));
//
//        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));
//
//        int count = 0;
//        int preSec = 0;
//        int sec = 0;
//        int time = 0;
//        int preTime = 0;
//        int i = 0;
//        int j = 0;
//        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
//        {
//            if (variableDefinit.str.contains("nmea:$GNGGA,")){
//                count++;
//                String[] aa = variableDefinit.str.split(",");
//                try{
//                    sec = Integer.parseInt(aa[1].substring(5,6));
//                }catch (StringIndexOutOfBoundsException e){
//                    continue;
//                }
//                if (count != 1) {
//                    if (sec == 0 && preSec == 9){
//                        preSec = 0;
//                        continue;
//                    }
//                    if (sec - preSec != 1 && sec != preSec) System.out.println("GGA第"+(count-1)+"行丢失");
//                    else if (sec == preSec) System.out.println("GGA第"+count+"行和第"+(count-1)+"行相同");
//                }
//                preSec = sec;
//
//                time = Integer.parseInt(variableDefinit.str.substring(variableDefinit.str.indexOf("[")+1,variableDefinit.str.indexOf("]")));
//
//                if (count != 1 && time - preTime > 200) System.out.println("第"+(++i)+"行日志时间超过1s");
//                else if (count != 1 && time - preTime > 400) System.out.println("第"+(++j)+"行日志时间超过2s");
//
//                preTime = time;
//            }
//        }
//        System.out.println("超过1s总共"+i+"次");
//        System.out.println("超过2s总共"+j+"次");
//        variableDefinit.getBufferedReader().close();
//        variableDefinit.getInputStream().close();
//
        System.out.println(GnssUtil.convertGgaLocation(2307.19679423,-1));
        System.out.println(GnssUtil.convertGgaLocation(11320.71056088,-1));
    }
}
