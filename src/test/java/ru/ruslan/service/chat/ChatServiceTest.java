package ru.ruslan.service.chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.ChatView;
import ru.ruslan.entity.user.User;
import ru.ruslan.repository.chat.ChatRepository;

import java.util.HashSet;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @MockBean
    private ChatRepository chatRepository;

    private final String userName = "username";
    private final Long chatId = 1L;
    private final String chatName = "chatName";

    @Test
    public void createChatSuccessful() {
        Chat passedChat = new Chat(chatId, chatName);
        ChatView expectedResponse = new ChatView(passedChat);
        Mockito.when(chatRepository.save(passedChat)).thenReturn(passedChat);

        ChatView actualResponse = null;
        try {
            actualResponse = chatService.create(passedChat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedResponse, actualResponse);
    }

    @Test(expected = Exception.class)
    public void createChatFailedEmptyChatName() throws Exception {
        Chat passedChat = new Chat(chatId, null);

        ChatView actualResponse = chatService.create(passedChat);
    }

    @Test
    public void enterChatSuccessful() {
        Chat chat = new Chat();
        HashSet<User> chatMembers = new HashSet<>();
        chat.setChatMembers(chatMembers);
        User user = new User();
        user.setUsername(userName);
        Mockito.when(chatRepository.getByChatId(chatId)).thenReturn(chat);

        boolean actualResponse = chatService.enterChat(user, chatId);

        assertTrue(actualResponse);
    }

    @Test
    public void enterChatFailedUserAlreadyInChat() {
        Chat chat = new Chat();
        HashSet<User> chatMembers = new HashSet<>();
        User user = new User();
        user.setUsername(userName);
        chatMembers.add(user);
        chat.setChatMembers(chatMembers);
        Mockito.when(chatRepository.getByChatId(chatId)).thenReturn(chat);

        boolean actualResponse = chatService.enterChat(user, chatId);

        assertFalse(actualResponse);
    }

    @Test
    public void leaveChatSuccessful() {
        Chat chat = new Chat();
        HashSet<User> chatMembers = new HashSet<>();
        User user = new User();
        user.setUsername(userName);
        chatMembers.add(user);
        chat.setChatMembers(chatMembers);
        Mockito.when(chatRepository.getByChatId(chatId)).thenReturn(chat);

        boolean actualResponse = chatService.leaveChat(user, chatId);

        assertTrue(actualResponse);
    }

    @Test
    public void leaveChatFailedUserNotInChat() {
        Chat chat = new Chat();
        HashSet<User> chatMembers = new HashSet<>();
        chat.setChatMembers(chatMembers);
        User user = new User();
        user.setUsername(userName);
        Mockito.when(chatRepository.getByChatId(chatId)).thenReturn(chat);

        boolean actualResponse = chatService.leaveChat(user, chatId);

        assertFalse(actualResponse);
    }
}