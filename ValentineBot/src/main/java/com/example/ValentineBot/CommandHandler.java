package com.example.ValentineBot;


import com.example.ValentineBot.Entity.Valentine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class CommandHandler {


    public SendMessage start(String chatId, String name){
        SendMessage message = new SendMessage();
        message.setText("Здравствуйте " + name + "❤\uFE0F" + "\n\n" + "Сегодня 1\uFE0F⃣4\uFE0F⃣ февраля  - день влюблённых," +
                " а это значит что самое время поздравить близких вам людей." + "\n\n" +
                "Я - Love_It_hub_bot, могу помочь вам в этом\uD83E\uDD70" +"\n\n" +
                "Здесь вы можете создать и отправить свою валентинку\uD83D\uDC8C. Анонимная валентинка будет или нет, решать вам" +"\n\n" +
                "Также, вы можете посмотреть валентинки которые отправили вам\uD83C\uDF81" + "\n" + "\n"+
                "Выберите меню что бы посмотреть список ваших возможностей▶\uFE0F");
        message.setChatId(chatId);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Меню");
        button.setCallbackData("/menu");
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(rows);

        message.setReplyMarkup(markupKeyboard);
        return message;

    }
    public SendMessage menu(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Это меню ❤\uFE0FLove_it_hub_bot❤\uFE0F\n" +
                "\n" +
                "Список комманд:\n" +
                "\n" +
                "/create - Создать валентинку\uD83D\uDC8C\n" +
                "\n" +
                "/valentine - Посмотреть мои валентинки\uD83D\uDCE9\n" +
                "\n" +
                "/help - Обратиться в поддержку\uD83C\uDD98");
        return sendMessage;
    }

    public SendMessage create1(String chatId, String name){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Вы в меню создания валентинки\n" +
                "\n" +
                "Нажмите \"да\" если уверенны что хотите создать валентинку\n" +
                "\n" +
                "Если нет, или передумаете - \n" +
                "/exit вернёт вас обратно в меню и отменит создание валентинки");
        sendMessage.setChatId(chatId);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Да");
        button.setCallbackData("StartCreate");
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(rows);

        sendMessage.setReplyMarkup(markupKeyboard);
        return sendMessage;
    }

    //Мини гайдик по созданию
    public SendMessage create2(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("\n" +
                "\n" +
                "Что бы создать валентинку вам нужно ввести некоторые данные\uD83D\uDC8C:\n" +
                "\n" +
                "Адресат\uD83E\uDDCD\u200D♂\uFE0F - тот, кому вы отправляете валентинку. Телеграм никнейм пользователя. Рекомендую зайти в профиль и скопировать ник\n" +
                "\n" +
                "Заголовок\uD83D\uDC3F - заголовок валентинки, например, туда можно вписать приветсвие адресата\n" +
                "\n" +
                "Описание\uD83D\uDCDD - Поле для основного текста валентинки\n" +
                "\n" +
                "Статус♦\uFE0F - анонимной или подписанной будет ваша валентинка");
        return sendMessage;
    }

    public SendMessage getSender(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Введите для кого будет валентинка\uD83D\uDC8C - (Вводите телеграм имя, например @user )");
        return sendMessage;
    }
    public SendMessage usernameWasGot(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Адресат успешно был выбран! Нажмите далее что бы продолжить");
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Далее▶\uFE0F");
        button.setCallbackData("Header");
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(rows);

        sendMessage.setReplyMarkup(markupKeyboard);
        return sendMessage;
    }
    public SendMessage getHeader(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Введите заголовок для вашей валентинки - начните сообщение с /h " +
                "\n"+
                "(Рекомендуется заголовок меньше 255 символов)");
        sendMessage.setChatId(chatId);
        return sendMessage;
    }
    public SendMessage createDescription(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("А теперь, переходим к основной части открытки - здесь вы можете писать что угодно, например ваше поздравление, ну или же признание♥\uFE0F" +
                "\n\n"+ "Начните с /d");
        return sendMessage;
    }
    public SendMessage getPrivacy(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("А теперь нужно определиться, хочешь ли ты подписать валентинку своим именем?");
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Да✅");
        button.setCallbackData("Public");
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Нет❌");
        button1.setCallbackData("Anonymous");
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);
        buttons.add(button1);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(rows);

        sendMessage.setReplyMarkup(markupKeyboard);
        return sendMessage;
    }
    public SendMessage valentineBuilder(String chatId, Valentine valentine){

        SendMessage sendMessage = new SendMessage();
        Status status = valentine.getStatus();
        String owner = "Анонимус\uD83D\uDC7A";
        if(status.equals(Status.PUBLIC)){
            owner = valentine.getOwner();
        }
        sendMessage.setChatId(chatId);
        sendMessage.setText("Валентинка для " + valentine.getSender() +
                "\uD83D\uDC8C\n" +
                valentine.getHeader() + "\n" +
                "--------------->♥\uFE0F\n" +
                "\n" +
                valentine.getDescription() +"\n" +
                "\n" +
                "--------------->♥\uFE0F\n" +
                "\n" +
                "Валентинка от " + owner);

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Отправить✅");
        button.setCallbackData("Send" + valentine.getId());
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Удалить❌");
        button1.setCallbackData("Remove" + valentine.getId());
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);
        buttons.add(button1);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(rows);
        sendMessage.setReplyMarkup(markupKeyboard);

        return sendMessage;
    }

    public SendMessage sendNotification(String chatId){
        SendMessage sendMessage =  new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("\uD83D\uDC96Вам пришла валентинка!\uD83D\uDCE9");
        return sendMessage;
    }
    public SendMessage sendValentine(String chatId, Valentine valentine){
        SendMessage sendMessage = new SendMessage();
        Status status = valentine.getStatus();
        String owner = "Анонимус\uD83D\uDC7A";
        if(status.equals(Status.PUBLIC)){
            owner = "@" + valentine.getOwner();
        }
        sendMessage.setChatId(chatId);
        sendMessage.setText("Валентинка для " + valentine.getSender() +
                "\uD83D\uDC8C\n" +
                valentine.getHeader() + "\n" +
                "--------------->♥\uFE0F\n" +
                "\n" +
                valentine.getDescription() +"\n" +
                "\n" +
                "--------------->♥\uFE0F\n" +
                "\n" +
                "Валентинка от " + owner);

        return sendMessage;
    }
    public SendMessage wasRemoved(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Ваша валентинка была удалена!" + "\n" + "Напишите /create что бы создать новую валентинку\uD83D\uDC8C");
        return sendMessage;
    }
    public SendMessage wasSended(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Ваша валентинка была доставлена!\uD83D\uDCE5 " + "\n" + "Напишите /create что бы создать новую валентинку\uD83D\uDC8C");
        return sendMessage;
    }
    public SendMessage exit(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Вы отменили создание валентинки❌");
        return sendMessage;
    }
    public SendMessage help(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Вы находитесь в меню поддержки\uD83D\uDCDE" +
                "\n\n" +
                "Если у вас что то поломалось или вы нашли баг, сообщите об этом @VictorNVK"+
                "\n\n" +
                "Главный разработчик постарается ответить и помочь вам как можно быстрее❤\uFE0F");

        return sendMessage;
    }

}
