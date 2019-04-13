package com.example.kjh.nettyclientdemo.otto;


import com.example.kjh.nettyclientdemo.data.ChatLogs;

/**
 * Created by JYN on 2017-11-17.
 */

public class Events {

    /** 소켓 콜백 이벤트 보내기 */
    public static class SocketCallBack {
        private ChatLogs chatLogs;

        public SocketCallBack(ChatLogs chatLogs) {
            this.chatLogs = chatLogs;
        }

        public ChatLogs getChatLogs() {
            return chatLogs;
        }
    }
}
