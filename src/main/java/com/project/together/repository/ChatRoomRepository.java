package com.project.together.repository;

import com.project.together.entity.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRooms(){
        List<ChatRoom> result = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(result);

        return result;
    }

    public ChatRoom findRoomById(String id){
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(){
        ChatRoom room = ChatRoom.create();
        chatRoomMap.put(room.getRoomId(), room);

        return room;
    }
}
