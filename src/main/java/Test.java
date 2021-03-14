import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String str = null;

        String path = "C:\\Users\\zhang_ht\\Desktop\\数据采集\\logs";		//要遍历的路径
        File file = new File(path);		//获取其file对象

        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isFile())		//若是文件，直接打印
                inputStream = new FileInputStream(f.getPath());
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                System.out.println("当前文件:"+f.getName());
                while((str = bufferedReader.readLine()) != null)
                {

                    if (str.contains("QhttpLen:99")) System.out.println("队列溢出文件："+f.getName());
                }
        }

        //func(file);
    }

    private static void func(File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory())	//若是目录，则递归打印该目录下的文件
                func(f);
            if(f.isFile())		//若是文件，直接打印
                System.out.println(f);
        }
    }

}
