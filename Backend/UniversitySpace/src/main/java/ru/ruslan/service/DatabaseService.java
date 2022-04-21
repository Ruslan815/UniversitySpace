package ru.ruslan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.Message;
import ru.ruslan.entity.MessageView;
import ru.ruslan.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public synchronized List<MessageView> readMessages(User someUser, Integer chatId) {
        String queryText1 = "SELECT * FROM MESSAGES WHERE CHAT_ID = " + chatId + " AND MESSAGE_ID in ";
        String queryText2 = "(SELECT UNREAD_MESSAGES_MESSAGE_ID FROM MESSAGES_USERS_WHO_DID_NOT_READ where USERS_WHO_DID_NOT_READ_ID = " + someUser.getId() + ");";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(queryText1 + queryText2);
        List<MessageView> responseList = new ArrayList<>();
        List<Integer> messageIdForDelete = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Message queryMessage = new Message();

            queryMessage.setMessageId((Integer) row.get("MESSAGE_ID"));
            messageIdForDelete.add((Integer) row.get("MESSAGE_ID"));

            queryMessage.setUserId((Integer) row.get("USER_ID"));
            queryMessage.setText((String) row.get("TEXT"));
            queryMessage.setSendTime((String) row.get("SEND_TIME"));

            /// System.out.println(queryMessage.getMessageId() + " | " + queryMessage.getUserId() + " | " + queryMessage.getText() + " | " + queryMessage.getSendTime());
            responseList.add(new MessageView(queryMessage));
        }

        if (!messageIdForDelete.isEmpty()) {
            queryText1 =
                    "delete FROM MESSAGES_USERS_WHO_DID_NOT_READ where USERS_WHO_DID_NOT_READ_ID = " + someUser.getId()
                            + " AND UNREAD_MESSAGES_MESSAGE_ID in " + messageIdForDelete.toString()
                            .replace("[", "(").replace("]", ")");
            jdbcTemplate.execute(queryText1);
        }

        return responseList;
    }
}