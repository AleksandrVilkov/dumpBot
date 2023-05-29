package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.ButtonCallBack;
import com.dumpBot.model.User;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.msgProcessor.MsgProcess;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.IUserStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


//TODO отрефакторить, вынести в ресурсы текст
@Component
public class MsgRejectedProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    IUserStorage userStorage;
    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    ILogger logger;

    @Override
    public void processResultPreviousStep() {

    }

    @Override
    public void preparationCurrentProcess() {

    }

    @Override
    public List<SendMessage> execute(Update update) {
        User user = userStorage.getUser(String.valueOf(update.getMessage().getFrom().getId()));
        ButtonCallBack buttonCallBack;
        try {
            buttonCallBack = Util.readButtonCallBack(user.getLastCallback());
        } catch (JsonProcessingException e) {
            logger.writeStackTrace(e);
            //TODO возвращать сообщение об ошибке
            return null;
        }

        UserAccommodation accommodation = accommodationStorage.getById(buttonCallBack.getAccommodationId());
        String rejectedText = update.getMessage().getText();
        String authorLogin = accommodation.getClientLogin();
        List<SendMessage> result = new ArrayList<>();
        //Сообщение автору.
        result.add(createMsgForAuthor(authorLogin, user.getUserName(), rejectedText));
        //Сообщение админу, кто отменяет
        result.add(createMsgForAdmin(user.getLogin()));
        //Обновляем запрос
        accommodation.setRejected(true);
        accommodation.setTopical(false);
        accommodationStorage.saveAccommodation(accommodation);
        //Обновляем действия админа
        user.setLastCallback("");
        user.setClientAction(Action.ADMINISTRATION.name());
        userStorage.saveUser(user);
        //уведоляем админов остальный
        List<User> admins = userStorage.findAdmins();
        result.addAll(createMsgForAdmins(admins,user,accommodation,rejectedText));

        return result;
    }

    private SendMessage createMsgForAuthor(String authorId, String adminName, String cause) {
        String text = "Привет! Администратор @" + adminName + " отменил твой запрос по следующей причине: \n" + cause;
        text += "\nПожалуста, подай запрос на размещение заново, исправив ошибки. Если есть вопросы - напиши админу.";
        return new SendMessage(authorId, text);
    }

    private SendMessage createMsgForAdmin(String adminName) {
        String text = "Запрос помечен как отклоненный. Автор получил уведомление. Остальные администраторы в курсе что ты отменил этот запрос";
        return new SendMessage(adminName, text);
    }
    private List<SendMessage> createMsgForAdmins(List<User> admins, User mainAdmin,UserAccommodation ua, String cause) {
        String text = "Служебное сообщение: \n Администратор @" + mainAdmin.getUserName()  +
                " отменил запрос #" +ua.getId() + " со следующим комментарием: \n"
                + cause;
        List<SendMessage> results = new ArrayList<>();
        for (User user: admins) {
            SendMessage sendMessage = new SendMessage(user.getLogin(), text);
            results.add(sendMessage);
        }


        return results;
    }
}
