import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.util.Scanner;

public class Test7 {

    public static void main(String[] args) throws IOException {
        Variable_Definit variableDefinit = new Variable_Definit();

        Scanner sc = new Scanner(System.in);
        //System.out.print("输入读取的文件路径(绝对路径)：");
        String inputPath = "C:\\Users\\zhang_ht\\Desktop\\_gga_gnss-L610-405-黑色L5天线-2020_12_18.log";

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));
        //System.out.print("输入要写入的文件名：");
        String fileName = "test.xls";

        File file = new File(parentPath,fileName);

        variableDefinit.setWriter(new FileWriter(file,false));
        variableDefinit.setBw(new BufferedWriter(variableDefinit.getWriter()));

        System.out.print("输入要截取的开始时间戳：");
        String startTime = sc.next();
        System.out.print("输入截取的结束时间戳：");
        String endTime = sc.next();

        boolean flag = false;
        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            try {
                variableDefinit.str = variableDefinit.str.substring(variableDefinit.str.indexOf("GNGGA"),variableDefinit.str.lastIndexOf("*")+2);
            }catch (StringIndexOutOfBoundsException e){
                continue;
            }

            String[] aa = variableDefinit.str.split(",");
            if (aa[1].equals(startTime)) flag = true;

            if (!flag) continue;

            double weiDu;
            double jingDu;

            try {
                weiDu = Double.parseDouble(aa[2]);
                jingDu = Double.parseDouble(aa[4]);
            }catch (NumberFormatException e){
                continue;
            }

            variableDefinit.str = aa[1] + "," +GnssUtil.convertGgaLocation(weiDu,-1) + "," + GnssUtil.convertGgaLocation(jingDu,-1) + "," + aa[6] + "," + aa[7] + "," +
                                    MapUtils.GetDistance(GnssUtil.convertGgaLocation(weiDu,-1),GnssUtil.convertGgaLocation(jingDu,-1),23.120785183,113.347358464);
            variableDefinit.getBw().write(variableDefinit.str);
            variableDefinit.getBw().newLine();

            if (aa[1].equals(endTime)) break;
        }

        variableDefinit.getBw().newLine();
        variableDefinit.getBw().newLine();
        variableDefinit.getBw().newLine();
        variableDefinit.getBw().newLine();
        variableDefinit.getBw().newLine();
        variableDefinit.getBw().newLine();

        variableDefinit.closeStream();
    }
}
