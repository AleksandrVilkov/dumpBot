package com.dumpBot;

import com.dumpBot.storage.entity.TempDataEntity;
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
        TempDataEntity tempDataEntity = new TempDataEntity();
        Object a = tempDataRepository.save(tempDataEntity);
        System.out.println(a);
    }

}
