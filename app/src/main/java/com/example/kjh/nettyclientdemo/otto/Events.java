package com.example.kjh.nettyclientdemo.otto;


import com.example.kjh.nettyclientdemo.data.ChatLogs;

/**
 * Created by JYN on 2017-11-17.
 */

public class Events {


    /** 이벤트
     *    Netty_handler --> Chat_F
     *    : Data_for_netty 객체와 함께 채팅방 리사이클러뷰에 대한 변경점 이벤트 메시지를 전달
     * */
    public static class Event1 {
        private ChatLogs chatLogs;

        public Event1(ChatLogs chatLogs) {
            this.chatLogs = chatLogs;
        }

        public ChatLogs getChatLogs() {
            return chatLogs;
        }
    }
}
