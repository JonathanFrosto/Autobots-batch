package com.springbatch.autobotsjob.writer;

import com.springbatch.autobotsjob.dominio.Autobot;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutobotWriter implements ItemWriter<Autobot> {

    @Override
    public void write(List<? extends Autobot> list) throws Exception {
        list.forEach(System.out::println);
    }
}
