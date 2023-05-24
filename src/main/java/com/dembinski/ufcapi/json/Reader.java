package com.dembinski.ufcapi.json;

import com.dembinski.ufcapi.data.Fight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Reader {
    private static final Logger logger = LoggerFactory.getLogger(Reader.class);
    private final List<Fight> allFights;

    public Reader() {
        this.allFights = generateFightList();
    }

    private List<Fight> generateFightList() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Fight> list;
        try {
            list = Arrays.asList(objectMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("full_data.json"), Fight[].class));
        } catch (IOException e) {
            logger.error(String.valueOf(e));
            list = new ArrayList<>();
        }

        return list;
    }

    public List<Fight> getAllFights() {
        return this.allFights;
    }
}
