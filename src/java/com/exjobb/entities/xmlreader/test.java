/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.xmlreader;

import com.exjobb.entities.ejbs.EJBCollection;
import com.exjobb.entities.models.Category;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Filip
 */
@WebListener
public class test implements ServletContextListener {

    private final NewFileRunner runner;

    public test() throws IOException {
        this.runner = new NewFileRunner();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializeing ... ");
        runner.startThread();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Shutting down ... ");
        runner.stopThread();
    }

    class NewFileRunner implements Runnable {

        private EJBCollection ejb;

        {
            try {
                InitialContext ctx = new InitialContext();
                ejb = (EJBCollection) ctx.lookup("java:global/Exjobb_-_Java/EJBCollection");
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }

        private volatile Thread thread;
        private final WatchService watchService;

        public NewFileRunner() throws IOException {
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get("c:/temp/").register(watchService, ENTRY_CREATE);
        }

        /**
         * Start a worker thread to listen for directory changes.
         */
        public void startThread() {
            thread = new Thread(this);
            thread.start();
        }

        /**
         * Flag worker thread to stop gracefully.
         */
        public void stopThread() {
            if (thread != null) {
                Thread runningThread = thread;
                thread = null;
                runningThread.interrupt();
            }
        }

        @Override
        public void run() {
            System.out.println("WATCHING");
            Thread runningThread = Thread.currentThread();
            while (runningThread == thread) {
                WatchKey watchKey = null;
                try {
                    watchKey = watchService.take();
                    if (watchKey != null) {
                        for (WatchEvent<?> watchEvent : watchKey.pollEvents()) { // Is looking for events
                            System.out.println(watchEvent.kind() + " " + ((Path) watchEvent.context()));

                            ejb.category().add(new Category(1, "foo"));
                            System.out.println("added");

                        }
                        watchKey.reset();
                    }
                } catch (InterruptedException e) {
                    e = null;
                }
            }
        }
    }
}
