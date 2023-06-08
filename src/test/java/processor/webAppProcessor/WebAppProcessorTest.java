package processor.webAppProcessor;


import com.dumpBot.Main;
import com.dumpBot.config.Config;
import com.dumpBot.processor.webAppProcessor.WebAppProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;


@SpringBootTest(
        classes = Main.class)
public class WebAppProcessorTest {
    @Autowired
    WebAppProcessor webAppProcessor;
    @Test
    void startWebAppProcessorTest() {
        //given
        Update update = new Update();
        Message message = new Message();
        WebAppData webAppData = new WebAppData();
        User from = new User();
        from.setId(436641871L);
        webAppData.setData("{\"cars\":[{\"id\":37,\"createDate\":null,\"concern\":{\"name\":\"PSA\"},\"model\":{\"name\":\"301\"},\"engine\":{\"name\":\"EB2M\"},\"boltPattern\":{\"name\":null},\"brand\":{\"name\":\"Peugeot\"}},{\"id\":38,\"createDate\":null,\"concern\":{\"name\":\"PSA\"},\"model\":{\"name\":\"301\"},\"engine\":{\"name\":\"DV6DTED\"},\"boltPattern\":{\"name\":null},\"brand\":{\"name\":\"Peugeot\"}},{\"id\":39,\"createDate\":null,\"concern\":{\"name\":\"PSA\"},\"model\":{\"name\":\"301\"},\"engine\":{\"name\":\"EC5\"},\"boltPattern\":{\"name\":null},\"brand\":{\"name\":\"Peugeot\"}}],\"price\":\"4\",\"description\":\"У\",\"action\":\"SALE\"}");
        webAppData.setButtonText("Продать");
        message.setWebAppData(webAppData);
        message.setFrom(from);
        update.setMessage(message);
        //when
        webAppProcessor.startWebAppProcessor(update);
        //then

    }
}
