package com.dumpBot.statistics;

import com.dumpBot.statistics.entity.Message;
import com.dumpBot.statistics.entity.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    public static void getStatistic() {
        String filePath = "/home/vilkov/Загрузки/Telegram Desktop/ChatExport_2023-05-15/result.json";
        String content;
        try {
            content = readFile(filePath, StandardCharsets.UTF_8);
            Root root = convertJson(content);
            Map<String, Integer> countMsg = new HashMap<>();
            Map<String, String> userNames = new HashMap<>();

            for (Message msg: root.getMessages()) {
                if (!countMsg.containsKey(msg.getUserId())) {
                    userNames.put(msg.getUserId(), msg.getFrom());
                    countMsg.put(msg.getUserId(), 1);
                } else {
                    int count = countMsg.get(msg.getUserId()) + 1;
                    countMsg.put(msg.getUserId(), count);
                }
            }
            System.out.println("За выгруженный период получилась следующая статистика:");
            int i = 1;
            for(Map.Entry entry: countMsg.entrySet()) {
                System.out.println(i + ". Пользователь : " + entry.getKey() + " " + userNames.get(entry.getKey()) + " отправил " + entry.getValue() + " сообщений;");
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Root convertJson(String json) {
        Root root = new Root();
        JSONObject jsonObject = new JSONObject(json);
        root.setType(jsonObject.getString("type"));
        root.setName(jsonObject.getString("name"));
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject msg = (JSONObject) jsonArray.get(i);

            if (msg.has("from_id")) {
                Message message = new Message();
                message.setUserId(msg.getString("from_id").replaceAll("user", ""));
                message.setText(msg.optString("text"));
                message.setFrom(msg.getString("from"));
                root.getMessages().add(message);
            }
        }
        return root;
    }
    public static String readFile(String path, Charset encoding) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), encoding);
        return String.join(System.lineSeparator(), lines);
    }
}
