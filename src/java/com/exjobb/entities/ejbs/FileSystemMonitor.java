/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.ejbs;

import com.exjobb.entities.xmlreader.XMLImporter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.spi.FileTypeDetector;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 *
 * @author Filip
 */
@Stateless
public class FileSystemMonitor {

    @Asynchronous
    public void poll(String fileSystemPath) {

        checkExsistingFiles(fileSystemPath);

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();

            Path path = Paths.get(fileSystemPath);
            WatchKey watchKey = path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Watch service is watching: " + path.toString());
            for (;;) {
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
                            System.out.println("watcher: file is xml");
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
            Logger.getLogger(FileSystemMonitor.class.getName()).log(Level.SEVERE, null, ex);
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
                if (getExtension(path + file).equals("xml")) {
                    System.out.println("Existing file is xml");
                    XMLImporter.Import(path + file);
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
