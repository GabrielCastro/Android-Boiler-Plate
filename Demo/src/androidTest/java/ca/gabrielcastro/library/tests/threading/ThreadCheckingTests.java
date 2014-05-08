/*
 * Copyright 2013 - 2014 Gabriel Castro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.gabrielcastro.library.tests.threading;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;

import ca.gabrielcastro.library.Helpers;
import ca.gabrielcastro.library.threading.WrongThreadException;

public class ThreadCheckingTests extends AndroidTestCase {

    // Next three methods hate the UI thread

    /**
     * Expect an exception since we don't want main from main
     */
    @UiThreadTest // TODO: WTF why isn't this working
    public void testCheckNotMainFromMain() throws InterruptedException {
        Helpers.runOnUiAndWait(new Runnable() {
            @Override
            public void run() {

                boolean thorwn = false;
                try {
                    WrongThreadException.ensureNotMain();
                } catch (WrongThreadException e) {
                    thorwn = true;
                }
                assertTrue("not main thread check", thorwn);

            }
        });
    }


    /**
     * Shouldn't get an exaction from checking the thread
     * if we call from new thread without a Looper
     */
    public void testCheckNotMainFromNonMainWithoutLooper() throws Exception {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                WrongThreadException.ensureNotMain();
            }
        });
        t.start();
        t.join();

    }


    /**
     * Shouldn't get an exception from checking the thread
     * if we call from a new thread with a Looper
     */
    public void testCheckNotMainFromNonMainWithLooper() throws Exception {
        HandlerThread t = new HandlerThread("test-thread");
        t.start();

        // to ensure the test run before we quit the Looper
        final CountDownLatch latch = new CountDownLatch(1);

        Handler handler = new Handler(t.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                WrongThreadException.ensureNotMain();
                latch.countDown();
            }
        });

        // to ensure the test run before we quit the Looper
        latch.await();
        t.quit();
        t.join();
    }

    // We want to be on main here down

    /**
     * Tests that checking main from main shouldn't throw
     */
    @UiThreadTest // TODO: WTF why isn't this working
    public void testCheckMainFromMain() throws Exception {
        Helpers.runOnUiAndWait(new Runnable() {
            @Override
            public void run() {
                WrongThreadException.ensureMain();
            }
        });
    }


    /**
     * Tests that checking main from a thread without a looper should throw
     */
    public void testCheckMainFromNonMainWithoutLooper() throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean thrown = false;
                try {
                    WrongThreadException.ensureMain();
                } catch (WrongThreadException e) {
                    thrown = true;
                }
                assertTrue("main from non main no looper", thrown);
            }
        });
        t.start();
        t.join();
    }


    /**
     * Tests that checking main from a thread with a looper should throw
     */
    public void testCheckMainFromNonMainWithLooper() throws InterruptedException {
        HandlerThread t = new HandlerThread("test thread");
        t.start();
        Handler handler = new Handler(t.getLooper());
        final CountDownLatch latch = new CountDownLatch(1);
        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean thrown = false;
                try {
                    WrongThreadException.ensureMain();
                } catch (WrongThreadException e) {
                    thrown = true;
                }
                assertTrue("main from non main with looper", thrown);
                latch.countDown();
            }
        });
        latch.await();
        t.quit();
        t.join();
    }

}