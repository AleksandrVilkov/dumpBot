package com.dumpBot;

import com.dumpBot.storage.entity.TempData;
import com.dumpBot.storage.repository.TempDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DumpBotApplicationTests {
    @Autowired(required = true)
    TempDataRepository tempDataRepository;

    @Test
    @Transactional
    void testTempData() {
        TempData tempData = new TempData();
        Object a = tempDataRepository.save(tempData);
        System.out.println(a);
    }

}
