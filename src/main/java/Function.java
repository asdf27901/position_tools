import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Function {
    Variable_Definit variableDefinit = new Variable_Definit();

    public static void main(String[] args) throws IOException {
        Function function = new Function();
        while(true){
            System.out.println("1.删除只有时间或者空的GGA/RMC数据\n");
            System.out.println("2.删除时间重复的GGA/RMC数据\n");
            System.out.println("3.计算数据的丢失率\n");
            System.out.println("4.统计高程数据\n");
            System.out.println("5.找到数据丢失所在行\n");
            System.out.println("6.计算差分龄期占比\n");
            System.out.println("7.筛选出所有固定解\n");
            System.out.println("8.截取日志中任意时间段数据\n");
            System.out.println("9.退出");
            System.out.print("选择你要进行的操作：");
            Scanner sc = new Scanner(System.in);
            int choose = sc.nextInt();

            switch (choose){
                case 1:
                    function.deleteOnlyTimeData();
                    break;
                case 2:
                    function.deleteSameTime();
                    break;
                case 3:
                    function.calLossRate();
                    break;
                case 4:
                    function.countMeter();
                    break;
                case 5:
                    function.findLoseRow();
                    break;
                case 6:
                    function.countAge();
                    break;
                case 7:
                    function.catFixedSolution();
                    break;
                case 8:
                    function.selectAnyTimePerod();
                    break;
                case 9:
                    System.exit(0);
                default:
                    System.out.print("重新选择:");
            }

            System.out.print("是否继续？(y/n)");
            String judge = sc.next();
            if (judge.equals("y")){
                continue;
            }
            System.exit(0);
        }
    }

    //删除只有时间或者空的数据
    public void deleteOnlyTimeData() throws IOException {

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

        int count = 0;
        String str5 = null;
        String str6 = null;


        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            count++;
            String[] aa = variableDefinit.str.split(",");

            str5 = aa[5];
            str6 = aa[6];

            if(str5.equals("") && str6.equals("0")){
                continue;
            }else if(str5.equals("") && str6.equals("")){
                continue;
            }

            variableDefinit.getBw().write(variableDefinit.str);
            variableDefinit.getBw().newLine();
        }
        System.out.println("删除成功");
        variableDefinit.closeStream();
    }

    //删除时间重复的数据
    public void deleteSameTime() throws IOException{

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

        String time = null;
        String preTime = null;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            String[] aa = variableDefinit.str.split(",");
            time = aa[1];
            if(time.equals(preTime)){
                preTime = time;
                continue;
            }
            preTime = time;

            variableDefinit.getBw().write(variableDefinit.str);
            variableDefinit.getBw().newLine();
        }
        System.out.println("删除成功");
        variableDefinit.closeStream();
    }

    //计算数据的丢失率
    public void calLossRate() throws IOException{
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(3);
        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        int realCount = 0;
        int theoryCount = 0;
        int day = 0;
        int overHour = 0;
        int overMin = 0;
        int overSec = 0;
        boolean flag1 = false;
        boolean flag2 = false;
        String time = null;
        String hour = null;
        String min = null;
        String sec = null;
        String firstTime = null;
        String lastTime = null;

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {

            String[] aa = variableDefinit.str.split(",");

            time = aa[1];
            try{
                hour = time.substring(0,2);
                min = time.substring(2,4);
                sec = time.substring(4,6);
            }catch (StringIndexOutOfBoundsException e){
                continue;
            }

            realCount++;

            if(realCount == 1){
                if ((Integer.parseInt(hour)+8) > 24) firstTime = (Integer.parseInt(hour) + 8 - 24)+":"+min+":"+sec;
                else firstTime = Integer.parseInt(hour) + 8+":"+min+":"+sec;
            }
            if ((Integer.parseInt(hour)+8) > 24) lastTime = (Integer.parseInt(hour) + 8 - 24)+":"+min+":"+sec;
            else lastTime = Integer.parseInt(hour) + 8+":"+min+":"+sec;

            if(firstTime.equals(lastTime) && realCount != 1) day++;

        }

        if (Integer.parseInt(lastTime.split(":")[2]) < Integer.parseInt(firstTime.split(":")[2])){
            flag2 = true;
            overSec = (Integer.parseInt(lastTime.split(":")[2]) + 60) - Integer.parseInt(firstTime.split(":")[2]);
        }else overSec = Integer.parseInt(lastTime.split(":")[2]) - Integer.parseInt(firstTime.split(":")[2]);

        if (Integer.parseInt(lastTime.split(":")[1]) < Integer.parseInt(firstTime.split(":")[1])){
            flag1 = true;
            if (flag2 == true) overMin = (Integer.parseInt(lastTime.split(":")[1]) + 60 - 1) - Integer.parseInt(firstTime.split(":")[1]);
            else overMin = (Integer.parseInt(lastTime.split(":")[1]) + 60) - Integer.parseInt(firstTime.split(":")[1]);
        }else if (Integer.parseInt(lastTime.split(":")[1]) == Integer.parseInt(firstTime.split(":")[1])){
            if (flag2 == true) overMin = (Integer.parseInt(lastTime.split(":")[1]) + 60 - 1) - Integer.parseInt(firstTime.split(":")[1]);
            else overMin = Integer.parseInt(lastTime.split(":")[1]) - Integer.parseInt(firstTime.split(":")[1]);
        }else {
            if (flag2 == true) overMin = (Integer.parseInt(lastTime.split(":")[1]) - 1) - Integer.parseInt(firstTime.split(":")[1]);
            else overMin = Integer.parseInt(lastTime.split(":")[1]) - Integer.parseInt(firstTime.split(":")[1]);
        }

        if (Integer.parseInt(lastTime.split(":")[0]) < Integer.parseInt(firstTime.split(":")[0])){
            if (flag1 == true) overHour = (Integer.parseInt(lastTime.split(":")[0]) + 24 - 1) - Integer.parseInt(firstTime.split(":")[0]);
            else overHour = (Integer.parseInt(lastTime.split(":")[0]) + 24) - Integer.parseInt(firstTime.split(":")[0]);
        }
        else if (Integer.parseInt(lastTime.split(":")[0]) == Integer.parseInt(firstTime.split(":")[0])){
            if (flag1 == true) overHour = (Integer.parseInt(lastTime.split(":")[0]) + 24 - 1) - Integer.parseInt(firstTime.split(":")[0]);
            else overHour = Integer.parseInt(lastTime.split(":")[0]) - Integer.parseInt(firstTime.split(":")[0]);
        }else {
            if (flag1 == true) overHour = (Integer.parseInt(lastTime.split(":")[0]) - 1) - Integer.parseInt(firstTime.split(":")[0]);
            else overHour = Integer.parseInt(lastTime.split(":")[0]) - Integer.parseInt(firstTime.split(":")[0]);
        }

        theoryCount = day*86400+overHour*3600+overMin*60+overSec+1;

        System.out.println("经过"+day+"天"+overHour+"小时"+overMin+"分"+overSec+"秒");

        System.out.println("开始时间："+firstTime);
        System.out.println("结束时间："+lastTime);
        System.out.println("总历元数："+realCount);
        System.out.println("理论历元数："+theoryCount);
        System.out.println("丢失数："+(theoryCount-realCount));
        System.out.println("丢失率为："+Double.parseDouble(ddf1.format((double)(theoryCount-realCount)/theoryCount))*100+"%");

    }

    //统计高程数据
    public void countMeter() throws IOException{

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));
        String fileName = new File(inputPath).getName();
        fileName = fileName.substring(0,fileName.lastIndexOf("."));

        File file = new File(parentPath,fileName+"固定解.csv");

        variableDefinit.setWriter(new FileWriter(file,false));
        variableDefinit.setBw(new BufferedWriter(variableDefinit.getWriter()));

        String meter = null;
        String catE = null;
        String status = null;
        String unit = null;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            String[] aa = variableDefinit.str.split(",");
            catE = aa[5];
            status = aa[6];
            meter = aa[9];
            unit = aa[10];

            variableDefinit.str = catE+","+status+","+meter+","+unit;

            variableDefinit.getBw().write(variableDefinit.str);
            variableDefinit.getBw().newLine();

        }
        System.out.println("统计完成");
        variableDefinit.closeStream();
    }

    //找到GGA数据丢失所在行
    public void findLoseRow() throws IOException{

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        int count = 0;
        int preSec = 0;
        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            count++;
            String[] aa = variableDefinit.str.split(",");
            int sec = Integer.parseInt(aa[1].substring(5,6));
            if (count != 1) {
                if (sec == 0 && preSec == 9){
                    preSec = 0;
                    continue;
                }
                //System.out.println("当前秒："+sec+"前一秒："+preSec);
                if (sec - preSec != 1) System.out.println("第"+(count-1)+"行");
            }
            preSec = sec;

        }
        variableDefinit.getBufferedReader().close();
        variableDefinit.getInputStream().close();
    }

    //统计差分龄期占比
    public void countAge() throws IOException{

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        int count = 0;
        int age = 0;
        int ageLessThan5 = 0;
        int ageLessThan10 = 0;
        int ageLessThan15 = 0;

        String status = null;
        String preStatus = null;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            count++;
            String[] aa = variableDefinit.str.split(",");
            status = aa[6];
            if (!aa[13].equals("") && aa[13] != null) age = Integer.parseInt(aa[13].split("\\.")[0]);

            if (status.equals(preStatus)){
                if (age <= 5 && age >= 0) ageLessThan5++;
                if (age <= 10 && age >= 0) ageLessThan10++;
                if (age <= 15 && age >= 0) ageLessThan15++;
            }
            preStatus = status;
        }

        System.out.println("差分龄期小于5的占比"+(double)ageLessThan5/count);
        System.out.println("差分龄期小于10的占比"+(double)ageLessThan10/count);
        System.out.println("差分龄期小于15的占比"+(double)ageLessThan15/count);

        variableDefinit.getBufferedReader().close();
        variableDefinit.getInputStream().close();
    }

    //筛选出所有固定解
    public void catFixedSolution() throws IOException{

        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();
        String fileName = new File(inputPath).getName();
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));

        File file = new File(parentPath,fileName+"-固定解.log");

        variableDefinit.setWriter(new FileWriter(file,false));
        variableDefinit.setBw(new BufferedWriter(variableDefinit.getWriter()));

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            String[] aa = variableDefinit.str.split(",");
            if (aa[6].equals("4")){
                variableDefinit.getBw().write(variableDefinit.str);
                variableDefinit.getBw().newLine();
            }
        }
        System.out.println("筛选完毕");
        variableDefinit.closeStream();
    }

    //截取日志中任意时间段数据
    public void selectAnyTimePerod() throws IOException {
        System.out.print("输入读取的文件路径(绝对路径)：");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.next();

        variableDefinit.setInputStream(new FileInputStream(inputPath));
        String parentPath = new File(inputPath).getParent();

        System.out.print("输入开始的时间：");
        String startTime = sc.next();
        System.out.print("输入结束的时间：");
        String endTime = sc.next();

        variableDefinit.setBufferedReader(new BufferedReader(new InputStreamReader(variableDefinit.getInputStream())));
        System.out.print("输入要写入的文件名：");
        String fileName = sc.next();

        File file = new File(parentPath,fileName);

        variableDefinit.setWriter(new FileWriter(file,false));
        variableDefinit.setBw(new BufferedWriter(variableDefinit.getWriter()));

        List<String> txtLists = new ArrayList<>();

        int startCount = 0;
        int endCount = 0;
        int chooseStartTime = 0;
        int chooseEndTime = 0;
        String time = null;

        while((variableDefinit.str = variableDefinit.getBufferedReader().readLine()) != null)
        {
            String[] aa = variableDefinit.str.split(",");
            time = aa[1];
            if (startTime.equals(time)) startCount++;
            if (endTime.equals(time)) endCount++;
            txtLists.add(variableDefinit.str);
        }

        if (startCount > 1){
            System.out.print("输入的开始时间"+startTime+"在日志中出现了"+startCount+"次，请选择第几次：");
            chooseStartTime = sc.nextInt();
        }else chooseStartTime = 1;

        if (endCount > 1){
            System.out.print("输入的结束时间"+endTime+"在日志中出现了"+endCount+"次，请选择第几次：");
            chooseEndTime = sc.nextInt();
        }else chooseEndTime = 1;

        startCount = 0;
        endCount = 0;

        for (String txtList : txtLists) {

            String[] aa = txtList.split(",");
            time = aa[1];
            if (startTime.equals(time)) startCount++;
            if (endTime.equals(time)) endCount++;

            if (chooseStartTime <= startCount){
                variableDefinit.getBw().write(txtList);
                variableDefinit.getBw().newLine();
            }
            if (chooseEndTime == endCount) break;
        }
        System.out.println("截取成功");
        variableDefinit.closeStream();
    }
}
