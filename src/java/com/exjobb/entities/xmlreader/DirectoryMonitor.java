/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.xmlreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filip
 */
public class DirectoryMonitor implements Runnable {

    private final XMLImporter xi;
    private volatile Thread thread;

    public DirectoryMonitor() {
        this.xi = new XMLImporter();
    }

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
        
        Properties prop = new Properties();
        try {
            prop.load(DirectoryMonitor.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(DirectoryMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String fileSystemPath = prop.getProperty("systempath");

        checkExsistingFiles(fileSystemPath);

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Thread runningThread = Thread.currentThread();
            Path path = Paths.get(fileSystemPath);
            WatchKey watchKey = path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Watch service is watching: " + path.toString());
            while (runningThread == thread) {
                WatchKey key = null;
                try {
                    key = watcher.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue; // If events are lost or discarded
                        }
                        WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;

                        //Process files....
                        Path dir = (Path) key.watchable();
                        Path fullPath = dir.resolve(event.context().toString());
                        System.out.println("WATCHINNG: " + fullPath);
                        if (getExtension(fullPath.toString()).equals("xml")) {
                            xi.xmlAction(fullPath.toString());
                            Files.delete(fullPath);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                } finally {
                    if (key != null) {
                        boolean valid = key.reset();
                        if (!valid) {
                            break; // If the key is no longer valid, the directory is inaccessible so exit the loop.
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DirectoryMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkExsistingFiles(String path) {
        File dir = new File(path);
        String[] fileList = dir.list();
        Arrays.sort(fileList);
        if (fileList.length > 0) {
            for (String file : fileList) {
                // Process each file.
                System.out.println("FileList: " + path + file);
                File xml = new File(path + file);
                if (getExtension(xml.getAbsolutePath()).equals("xml")) {
                    System.out.println("Existing file is xml");
                    xi.xmlAction(xml.getAbsolutePath());
                    xml.delete();
                }
            }
        }
    }

    private String getExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
