package concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    static final CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) {
        test();

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }

        testRepeat();
    }

    public static void test() {
        System.out.println("开始测试");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1准备就绪");
                    latch.countDown();
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2准备就绪");
                    latch.countDown();
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("线程3准备就绪");
                    latch.countDown();
                } catch (Exception e) {
                }
            }
        }).start();

        try {
            latch.await();
            System.out.println("测试结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testRepeat() {
        System.out.println("开始重复测试");
        System.out.println(latch.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1准备就绪");
                    latch.countDown();
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2准备就绪");
                    //latch.countDown();
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("线程3准备就绪");
                    //latch.countDown();
                } catch (Exception e) {
                }
            }
        }).start();

        try {
            latch.await();
            System.out.println("测试结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
