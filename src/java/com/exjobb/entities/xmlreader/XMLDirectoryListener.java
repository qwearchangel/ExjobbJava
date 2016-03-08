/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.xmlreader;

import com.exjobb.entities.ejbs.FileSystemMonitor;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Filip
 */
@Startup
@Singleton
public class XMLDirectoryListener {

     @EJB
     private FileSystemMonitor fsm;

    @PostConstruct
    public void startup() {
        String path = "C:\\temp\\";
        fsm.poll(path);
    }

    @PreDestroy
    public void shutdown() {
    }
}
