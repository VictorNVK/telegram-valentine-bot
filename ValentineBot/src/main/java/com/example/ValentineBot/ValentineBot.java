package com.example.ValentineBot;

import com.example.ValentineBot.Entity.User;
import com.example.ValentineBot.Entity.Valentine;
import com.example.ValentineBot.Repository.UserRepository;
import com.example.ValentineBot.Repository.ValentineRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Component
public class ValentineBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    private final ValentineRepository valentineRepository;

    private final CommandHandler commandHandler = new CommandHandler();

    public ValentineBot(UserRepository userRepository, ValentineRepository valentineRepository) {
        this.valentineRepository = valentineRepository;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        handleUpdates(update);
    }

    public void handleUpdates(Update update) throws Exception {

        if (update.hasMessage() && update.getMessage().hasText()){

            User user = userRepository.findByChatId(update.getMessage().getFrom().getId().toString());
            if(user == null){
                User newUser = new User();
                newUser.setChatId(update.getMessage().getFrom().getId().toString());
                newUser.setTgName(update.getMessage().getFrom().getUserName());
                userRepository.save(newUser);
            }
            user = userRepository.findByChatId(update.getMessage().getFrom().getId().toString());
                commandHandler(update, user);

        }
        else if (update.hasCallbackQuery()) {
            User user = userRepository.findByChatId(update.getCallbackQuery().getFrom().getId().toString());
            callbackHandler(update, user);
        }
        else {
            System.out.println("void");
        }
    }

    //Обработка всех комманд в отдельном методе
    private void commandHandler(Update update, User user) throws Exception{
        String chatId = update.getMessage().getChat().getId().toString();
        String username = update.getMessage().getFrom().getUserName();
        String command = update.getMessage().getText();
        if (command.startsWith("/start")) {
            execute(commandHandler.start(chatId, username));
        }
        //Начало создания валентинки
        if(command.startsWith("/create") && !user.getCreateStatus()){
            user.setCreateStatus(true);
            userRepository.save(user);
            execute(commandHandler.create1(chatId, username));
        }
        if(command.startsWith("/valentine")){
            List<Valentine> valentines = valentineRepository.findAllBySender(username);
            for(int i = 0; i < valentines.size(); i++){
                execute(commandHandler.sendValentine(chatId, valentines.get(i)));
            }
        }
        if(command.startsWith("/help")){
            execute(commandHandler.help(chatId));
        }

        if(command.startsWith("/menu")){
            execute(commandHandler.menu(chatId));
        }
        if(command.startsWith("/h")&&user.getCreateStatus()){
            commandHandler.getHeader(chatId);
            String header = command.substring(2);
            if(header.length() > 255) {
            header = header.substring(0, Math.min(header.length(), 255));
            }
            Valentine valentine = valentineRepository.findValentineByStatusAndOwner(null, username);
            valentine.setHeader(header);
            valentineRepository.save(valentine);
            execute(commandHandler.createDescription(chatId));
        }
        if(command.startsWith("/d") && user.getCreateStatus()){
            String description = command.substring(2);
            Valentine valentine = valentineRepository.findValentineByStatusAndOwner(null, username);
            valentine.setDescription(description);
            valentineRepository.save(valentine);
            execute(commandHandler.getPrivacy(chatId));
        }
        if(command.startsWith("/exit") && user.getCreateStatus()){
            execute(commandHandler.exit(chatId));
            user.setCreateStatus(false);
            userRepository.save(user);
            execute(commandHandler.menu(chatId));
            Valentine valentine = valentineRepository.findValentineByStatusAndOwner(null, username);
            valentine.setStatus(Status.NON_CREATED);
            valentineRepository.save(valentine);
        }
        //Получение имени
        if(command.startsWith("@") && user.getCreateStatus()){
            String sender = command.substring(1);
            if(userRepository.existsByTgName(sender)){
                execute(commandHandler.usernameWasGot(chatId));
                Valentine valentine = new Valentine();
                valentine.setSender(sender);
                valentine.setOwner(userRepository.findByChatId(chatId).getTgName());
                valentineRepository.save(valentine);
                commandHandler.createDescription(chatId);

            }
            else{//Если юзер не найдуен в базе данных
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Похоже пользователя с таким ником нет, или он ещё не пользовался ботом :)");
                execute(sendMessage);
            }
        }

    }
    private void callbackHandler(Update update, User user) throws TelegramApiException {
        String callbackData = update.getCallbackQuery().getData();
        if(callbackData.startsWith("/menu")){
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.menu(chatId));
        }
        if(callbackData.startsWith("StartCreate")){
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.create2(chatId));
            execute(commandHandler.getSender(chatId));
        }
        if(callbackData.startsWith("Header") && user.getCreateStatus()) {
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.getHeader(chatId));
        }
        if(callbackData.startsWith("Public") && user.getCreateStatus()){
            String name = update.getCallbackQuery().getFrom().getUserName();
            Valentine valentine = valentineRepository.findValentineByStatusAndOwner(null, name);
            valentine.setStatus(Status.PUBLIC);
            valentineRepository.save(valentine);
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.valentineBuilder(chatId, valentine));
        }
        if(callbackData.startsWith("Anonymous") && user.getCreateStatus()){
            String name = update.getCallbackQuery().getFrom().getUserName();
            Valentine valentine = valentineRepository.findValentineByStatusAndOwner(null, name);
            valentine.setStatus(Status.ANONYMOUS);
            valentineRepository.save(valentine);
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.valentineBuilder(chatId, valentine));
        }
        if(callbackData.startsWith("Send") && user.getCreateStatus()){
            Integer id = Integer.valueOf(callbackData.substring(4));
            Valentine valentine = valentineRepository.findValentineById(id);
            User sender = userRepository.findByTgName(valentine.getSender());
            execute(commandHandler.sendNotification(sender.getChatId()));
            execute(commandHandler.sendValentine(sender.getChatId(), valentine));
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.wasSended(chatId));
            user.setCreateStatus(false);
            userRepository.save(user);
        }

        if(callbackData.startsWith("Remove") && user.getCreateStatus()){
            Integer id = Integer.valueOf(callbackData.substring(6));
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            execute(commandHandler.wasRemoved(chatId));
            user.setCreateStatus(false);
            userRepository.save(user);
            //Недоделано
        }
    }

    @Override
    public String getBotUsername() {
        return "bot_name";
    }

    @Override
    public String getBotToken() {
        return "token";
    }

}
