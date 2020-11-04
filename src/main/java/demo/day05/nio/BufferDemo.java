package demo.day05.nio;

import java.nio.IntBuffer;

public class BufferDemo {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(20);
        System.out.println("-----after allocate-----");
        System.out.println("position="+intBuffer.position());
        System.out.println("limit="+intBuffer.limit());
        System.out.println("capacity="+intBuffer.capacity());


        for (int i=0;i<5;i++) {
            intBuffer.put(i);
        }
        System.out.println("==========put============");
        System.out.println("position="+intBuffer.position());
        System.out.println("limit="+intBuffer.limit());
        System.out.println("capacity="+intBuffer.capacity());

        intBuffer.flip();
        System.out.println("==========flip============");
        System.out.println("position="+intBuffer.position());
        System.out.println("limit="+intBuffer.limit());
        System.out.println("capacity="+intBuffer.capacity());

        System.out.println("==========get============");
        for (int i=0;i<5;i++) {
            if (i==2) {
                intBuffer.mark();
            }
            System.out.println("num====="+intBuffer.get());
            System.out.println("position="+intBuffer.position());
            System.out.println("limit="+intBuffer.limit());
        }
        intBuffer.reset();
        System.out.println("==========reset============");
        System.out.println("position="+intBuffer.position());
        System.out.println("limit="+intBuffer.limit());
        System.out.println("capacity="+intBuffer.capacity());

        intBuffer.rewind();
        System.out.println("==========rewind============");
        System.out.println("position="+intBuffer.position());
        System.out.println("limit="+intBuffer.limit());
        System.out.println("capacity="+intBuffer.capacity());
    }
}
