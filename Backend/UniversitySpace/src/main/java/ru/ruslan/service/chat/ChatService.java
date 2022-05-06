package ru.ruslan.service.chat;

import org.springframework.stereotype.Service;
import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.ChatView;
import ru.ruslan.entity.user.User;
import ru.ruslan.repository.chat.ChatRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatView create(Chat someChat) throws Exception {
        if (someChat.getName() == null || someChat.getName().equals("")) {
            throw new Exception();
        }
        return new ChatView(chatRepository.save(someChat));
    }

    public List<ChatView> getAll() {
        List<Chat> tempList = chatRepository.findAll();
        List<ChatView> responseList = new ArrayList<>();
        for (Chat tempChat : tempList) {
            responseList.add(new ChatView(tempChat));
        }
        return responseList;
    }

    public Chat getByChatId(Integer chatId) {
        return chatRepository.findById(chatId).orElseThrow();
    }

    public boolean isChatNameExists(String name) {
        return chatRepository.findByName(name) != null;
    }

    @Transactional
    public boolean enterChat(User user, Integer chatId) {
        Chat chat = chatRepository.getByChatId(chatId);
        boolean isUserNotInChat = chat.getChatMembers().add(user);
        if (isUserNotInChat) {
            chatRepository.save(chat);
        }
        return isUserNotInChat;
    }

    @Transactional
    public boolean leaveChat(User user, Integer chatId) {
        Chat chat = chatRepository.getByChatId(chatId);
        boolean isUserAlreadyInChat = chat.getChatMembers().remove(user);
        if (isUserAlreadyInChat) {
            chatRepository.save(chat);
        }
        return isUserAlreadyInChat;
    }

    public boolean isUserInPrivateChat(User user, Integer chatId) {
        if (this.isPrivateChatExist(chatId)) {
            Chat chat = chatRepository.getByChatId(chatId);
            return chat.getChatMembers().contains(user);
        }
        return false;
    }

    public boolean isPrivateChatExist(Integer chatId) {
        if (chatId == null) return false;
        return chatRepository.existsById(chatId);
    }
}
