package com.example.bug;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DummyServiceImpl implements DummyService {

    @Override
    @Cacheable(cacheNames = "dummy", key = "T(com.example.bug.DummyUtils).getDummyInt()")
    public String dummy() {
        return "test";
    }
}
