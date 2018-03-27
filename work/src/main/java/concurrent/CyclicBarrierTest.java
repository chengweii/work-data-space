package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(3, new PreviewProcess());

    public static void main(String[] args) {
        testAwait();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        testAwaitRepeat();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        testReset();
    }

    public static void testAwait() {
        System.out.println("开始测试await");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1准备就绪");
                    c.await();
                    System.out.println("线程1处理任务结束");
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2准备就绪");
                    c.await();
                    System.out.println("线程2处理任务结束");
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程3准备就绪");
                    c.await();
                    System.out.println("线程3处理任务结束");
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public static void testAwaitRepeat() {
        System.out.println("开始测试await repeat");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1准备就绪");
                    c.await();
                    System.out.println("线程1处理任务结束");
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2准备就绪");
                    c.await();
                    System.out.println("线程2处理任务结束");
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程3准备就绪");
                    c.await();
                    System.out.println("线程3处理任务结束");
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public static void testReset() {
        System.out.println("开始测试reset");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1准备就绪");
                    c.await();
                    System.out.println("线程1处理任务结束");
                } catch (BrokenBarrierException e) {
                    System.out.println("中途接到取消通知，线程1取消任务");
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await(5, TimeUnit.SECONDS);
                    System.out.println("线程2处理任务结束");
                } catch (Exception e) {
                    System.out.println("线程2：还不来！取消任务");
                    c.reset();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程3：等等我，马上到");
                    Thread.sleep(8000);
                    c.await(3, TimeUnit.SECONDS);
                    System.out.println("线程3处理任务结束");
                } catch (Exception e) {
                    System.out.println("线程3等了很久也没等到大家伙，呜呜");
                }
            }
        }).start();
    }

    static class PreviewProcess implements Runnable {
        @Override
        public void run() {
            System.out.println("我喊开始了");
        }
    }
}
