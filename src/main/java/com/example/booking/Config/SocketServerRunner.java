//package com.example.booking.Config;
//
//import com.corundumstudio.socketio.SocketIOServer;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class SocketServerRunner implements CommandLineRunner {
//
//    private final SocketIOServer server;
//
//    public SocketServerRunner(SocketIOServer server) {
//        this.server = server;
//    }
//
//    @Override
//    public void run(String... args) {
//        server.addConnectListener(client -> {
//            System.out.println("Client connected: " + client.getSessionId());
//        });
//
//        server.addEventListener("joinRoom", String.class, (client, room, ackRequest) -> {
//            client.joinRoom(room);
//            System.out.println("Client joined room: " + room);
//        });
//
//        server.addEventListener("seatUpdate", Map.class, (client, data, ackRequest) -> {
//            String room = "flight-" + data.get("flightId");
//            List<Map<String, Object>> seats = (List<Map<String, Object>>) data.get("seats");
//
//            // Phát sự kiện tới tất cả client khác
//            server.getRoomOperations(room).sendEvent("seatUpdate", seats);
//        });
//
//        server.start();
//    }
//}
