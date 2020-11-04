package homework.day01.work_1;

import java.math.BigDecimal;

/*
Week01 作业题目（周四）： 1.（选做）自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。


::::::::::::::::::使用说明::::::::::::::::::
:::: 当前目录执行, 生成class文件:
javac -g Hello.java

:::: 当前目录执行, 查看反编译的代码; 根据操作系统决定是否转义（Windows不需要反斜杠转义?）：
javap -v Hello.class
javap -v Hello\$ISayHello.class
javap -v Hello\$AbstractSayHello.class
javap -v Hello\$AnyHuman.class

*/
// 演示程序: 为了排版方便, 使用的行内注释;
public class Hello {

    public static void main(String[] args) {
        // 这里拼接的字符串会被编译器合并优化掉
        String manPrefix = "human" + "-" + "name" + "-";
        int age = 2020 - 1999;
        long createTime = System.currentTimeMillis();
        //
        int total = 10;
        AnyHuman[] humanArray = new AnyHuman[total];
        for (int i = 0; i < total; i++) {
            String name = manPrefix + i;
            BigDecimal money = BigDecimal.valueOf(i * total);
            // 构建对象
            AnyHuman human = new AnyHuman();
            human.setName(name);
            human.setAge(age);
            human.setCreateTime(createTime);
            human.setOffset((short) i);
            human.setOpcode(Integer.valueOf(i).byteValue());
            human.setAlive(i % 2 == 0);
            human.setMoney(human.isAlive() ? money : BigDecimal.ZERO);
            // 加入数组
            humanArray[i] = human;
        }
        for (AnyHuman anyHuman : humanArray) {
            if (anyHuman.isAlive()) {
                anyHuman.sayHello();
            } else {
                ISayHello obj = anyHuman;
                obj.sayHello();
            }
        }

    }

    public interface ISayHello {
        // interface的方法和属性默认带 public 标志
        String sayName();

        // JDK8接口的默认方法实现
        default void sayHello() {
            System.out.println("Hello," + sayName() + "");
        }
    }

    public static class AbstractSayHello implements ISayHello {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String sayName() {
            return age + "'s" + this.name;
        }
    }

    public static class AnyHuman extends AbstractSayHello implements ISayHello {
        private double ratio = 0.5D;
        private float increase = 0.5F;
        private long createTime;
        private short offset;
        private byte opcode;
        private boolean alive;
        private BigDecimal money;

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public float getIncrease() {
            return increase;
        }

        public void setIncrease(float increase) {
            this.increase = increase;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public boolean isAlive() {
            return alive;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public short getOffset() {
            return offset;
        }

        public void setOffset(short offset) {
            this.offset = offset;
        }

        public byte getOpcode() {
            return opcode;
        }

        public void setOpcode(byte opcode) {
            this.opcode = opcode;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        @Override
        public String sayName() {
            return this.toString() + super.sayName();
        }

        @Override
        public String toString() {
            return "AnyHuman{" +
                    "name='" + super.getName() + '\'' +
                    ", age=" + super.getAge() +
                    ", ratio=" + ratio +
                    ", increase=" + increase +
                    ", createTime=" + createTime +
                    ", offset=" + offset +
                    ", opcode=" + opcode +
                    ", alive=" + alive +
                    ", money=" + money +
                    '}';
        }
    }
}
