//package com.example.booking.Config;
//
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOServer;
//import org.springframework.context.annotation.Bean;
//
//@org.springframework.context.annotation.Configuration
//public class WebSocketConfigure {
//
//    @Bean
//    public SocketIOServer socketIOServer() {
//        Configuration config = new Configuration();
//        config.setHostname("localhost");
//        config.setPort(9092);
//        SocketIOServer server = new SocketIOServer(config);
//
//        server.addEventListener("seatSelected", String.class, (client, data, ackRequest) -> {
//            server.getBroadcastOperations().sendEvent("seatSelected", data);
//        });
//
//        server.start();
//        return server;
//    }
//}
