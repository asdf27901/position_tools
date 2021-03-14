import java.io.*;
import java.util.Scanner;

public class Test5 {

    public static void main(String[] args) throws IOException {
        Variable_Definit variableDefinit = new Variable_Definit();

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        int maxCPU = 0;
        int minCPU = 100;
        int maxMem = 0;
        int minMem = 10000000;
        int cpuCount = 0;
        int memCount = 0;
        int percent = 0;
        int sumPer = 0;
        int mem = 0;
        int p9 = 0;
        int p8 = 0;
        int p7 = 0;
        int p6 = 0;
        int p5 = 0;

        long sumMem = 0;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            if (variableDefinit.str.contains("[log_cpu_and_mem_free]cpu")){

                try {
                    percent = Integer.parseInt(variableDefinit.str.substring(variableDefinit.str.indexOf("cpu:")+4).trim());
                }catch (NumberFormatException e){
                    continue;
                }

                cpuCount++;
                sumPer += percent;

                if (percent > maxCPU) maxCPU = percent;
                if (percent < minCPU) minCPU = percent;
                if (percent > 40 && percent <= 50) p5++;
                else if (percent > 30 && percent <= 40) p6++;
                else if (percent > 20 && percent <= 30) p7++;
                else if (percent > 10 && percent <= 20) p8++;
                else if (percent >= 0 && percent <= 10) p9++;

            }
            if (variableDefinit.str.contains("[log_cpu_and_mem_free]mem")){

                try {
                    mem = Integer.parseInt(variableDefinit.str.substring(variableDefinit.str.indexOf("mem:")+4).trim());
                }catch (NumberFormatException e){
                    continue;
                }

                memCount++;
                sumMem += mem;

                if (mem > maxMem) maxMem = mem;
                if (mem < minMem) minMem = mem;

            }
        }

        System.out.println("CPU平均负载："+String.format("%.2f",(100.0-(double)sumPer/cpuCount))+"%,CPU最大负载："+(100-minCPU)+"%,CPU最小负载："+(100-maxCPU)+"%");
        System.out.println("CPU负载小于50占比"+String.format("%.2f",((double)(cpuCount-p5-p6-p7-p8-p9)/cpuCount)*100)+"%");
        System.out.println("CPU负载大于等于50小于60占比"+String.format("%.2f",((double)p5/cpuCount)*100)+"%");
        System.out.println("CPU负载大于等于60小于70占比"+String.format("%.2f",((double)p6/cpuCount)*100)+"%");
        System.out.println("CPU负载大于等于70小于80占比"+String.format("%.2f",((double)p7/cpuCount)*100)+"%");
        System.out.println("CPU负载大于等于80小于90占比"+String.format("%.2f",((double)p8/cpuCount)*100)+"%");
        System.out.println("CPU负载大于等于90小于100占比"+String.format("%.2f",((double)p9/cpuCount)*100)+"%");
        System.out.println("平均剩余内存："+String.format("%.2f",(double)sumMem/memCount)+",剩余最大内存："+maxMem+",剩余最小内存："+minMem);

        variableDefinit.getBufferedReader().close();
        variableDefinit.getInputStream().close();
    }
}
