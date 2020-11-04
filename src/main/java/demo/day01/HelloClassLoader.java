package demo.day01;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Base64;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            new HelloClassLoader().findClass("jvm.Hello").newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = null;
        try {
            FileInputStream fis = new FileInputStream("/Users/maxiao/Workspace/Learn/java_training/target/classes/jvm/Hello.class");
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            data = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 自定义类加载器本质上就是，通过某种方式获得字节码或者class文件定义，传递给JVM
        return defineClass(name, data, 0, data.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
