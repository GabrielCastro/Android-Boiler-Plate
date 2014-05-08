package ca.gabrielcastro.library.tests.threading;


import android.os.HandlerThread;
import android.os.Looper;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import ca.gabrielcastro.library.threading.LooperExecutor;

/**
 * Tests for {@link ca.gabrielcastro.library.threading.LooperExecutor}
 */
public class LooperExecutorTest extends AndroidTestCase {


    /**
     * Tests that the default constructor uses the main thread
     *
     * @throws Exception
     */
    public void testDefaultsToMainThread() throws Exception {

        Executor executor = new LooperExecutor();
        final CountDownLatch latch = new CountDownLatch(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                assertEquals("running on ui thread", Looper.getMainLooper(), Looper.myLooper());
                latch.countDown();
            }
        });
        latch.await();
    }

    /**
     * Tests that the executor uses the supplied thread
     *
     * @throws Exception
     */
    public void testRunsOnCorrectLooper() throws Exception {

        final HandlerThread newThread = new HandlerThread("looper executor test thread");
        newThread.start();

        final Looper newLooper = newThread.getLooper();
        Executor executor = new LooperExecutor(newLooper);
        final CountDownLatch latch = new CountDownLatch(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                assertEquals("running on with expected looper", newLooper, Looper.myLooper());
                assertEquals("running on expected thread", newThread, Thread.currentThread());
                latch.countDown();
            }
        });
        latch.await();
        newLooper.quit();
        newThread.join();

    }

}
