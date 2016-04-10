package com.exjobb.xmlreader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Filip
 */
@WebListener
public class XMLDirectoryListener implements ServletContextListener {

    private final DirectoryMonitor dm;

    public XMLDirectoryListener() {
        this.dm = new DirectoryMonitor();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializeing directory listener ...");
        dm.startThread();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down ...");
        dm.stopThread();
    }
}
