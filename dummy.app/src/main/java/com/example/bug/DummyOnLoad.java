package com.example.bug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Service
public class DummyOnLoad {

    private final DummyService dummyService;

    @Autowired
    public DummyOnLoad(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @EventListener
    public void onStart(ApplicationReadyEvent event) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool1 = new ForkJoinPool(1, new MyForkJoinWorkerThreadFactory(), null, false);
        forkJoinPool1.submit(() -> {
            String dummy = dummyService.dummy();
            System.out.println("ForkJoinPool1 : " + dummy);
        }).get();

        ForkJoinPool forkJoinPool2 = new ForkJoinPool(1);
        forkJoinPool2.submit(() -> {
            String dummy = dummyService.dummy();
            System.out.println("ForkJoinPool2 : " + dummy);
        }).get();
    }
}
