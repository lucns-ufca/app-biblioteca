package com.equipe17.biblioteca.users;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class UsersManager {

    public static final int PASSWORD_MINIMUM_LENGTH = 4;
    private static UsersManager instance;

    public static UsersManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UsersManager.class) {
                instance = new UsersManager(context);
            }
        }
        return instance;
    }

    private final File fileUsers, fileUser;
    private Map<String, User> users;

    private UsersManager(Context context) {
        fileUsers = new File(context.getDataDir(), "files/users/AppUsers.json");
        fileUser = new File(context.getDataDir(), "files/users/AppUser.json");
        readUsers();
    }

    public String getNameFromCpf(String cpf) {
        if (!users.containsKey(cpf)) return null;
        User user = users.get(cpf);
        if (user == null) return null;
        return user.name;
    }

    public boolean hasLoggedUser() {
        return fileUser.exists() && fileUser.isFile();
    }

    public User getLoggedUser() {
        if (!hasLoggedUser()) return null;
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(readFile(fileUser));
            if (jsonObject.has("is_pd")) {
                user = new User(jsonObject.getString("name"), jsonObject.getString("cpf"), jsonObject.getString("password"), jsonObject.getString("deficiency"), jsonObject.getBoolean("is_pd"));
            } else {
                user = new User(jsonObject.getString("name"), jsonObject.getString("cpf"), jsonObject.getString("password"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void setLoggedUser(User user) {
        if (user == null) {
            fileUser.delete();
            return;
        }
        user = users.get(user.cpf);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", user.name);
            jsonObject.put("cpf", user.cpf);
            jsonObject.put("password", user.password);
            if (user.isPd) {
                jsonObject.put("is_pd", user.isPd);
                jsonObject.put("deficiency", user.deficiency);
            }
            writeFile(fileUser, jsonObject.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserProcessStatus addUser(User user) {
        if (user.cpf == null || user.cpf.isEmpty() || user.password == null || user.password.length() < PASSWORD_MINIMUM_LENGTH) return UserProcessStatus.INVALID;
        if (users.containsKey(user.cpf)) return UserProcessStatus.ALREADY_EXISTS;
        if (user.isPd && (user.deficiency == null || user.deficiency.isEmpty())) return UserProcessStatus.INVALID;
        users.put(user.cpf, user);
        writeUsers();
        return UserProcessStatus.OK;
    }

    public UserProcessStatus verifyPassword(User user) {
        if (user.cpf == null || user.cpf.isEmpty() || user.password == null || user.password.length() < PASSWORD_MINIMUM_LENGTH) return UserProcessStatus.INVALID;
        User u = users.get(user.cpf);
        if (u == null) return UserProcessStatus.NOT_REGISTERED;
        if (u.password.equals(user.password)) return UserProcessStatus.OK;
        return UserProcessStatus.WRONG_PASSWORD;
    }

    private void writeUsers() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (User user : users.values()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", user.name);
                jsonObject.put("cpf", user.cpf);
                jsonObject.put("password", user.password);
                if (user.isPd) {
                    jsonObject.put("is_pd", user.isPd);
                    jsonObject.put("deficiency", user.deficiency);
                }
                jsonArray.put(jsonObject);
            }
            if (jsonArray.length() == 0) return;
            writeFile(fileUsers, jsonArray.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void readUsers() {
        users = new HashMap<>();
        if (fileUsers.isDirectory() || !fileUsers.exists()) return;
        String everything = readFile(fileUsers);
        if (everything == null) return;

        try {
            JSONArray jsonArray = new JSONArray(everything);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user;
                if (jsonObject.has("is_pd")) {
                    user = new User(jsonObject.getString("name"), jsonObject.getString("cpf"), jsonObject.getString("password"), jsonObject.getString("deficiency"), jsonObject.getBoolean("is_pd"));
                } else {
                    user = new User(jsonObject.getString("name"), jsonObject.getString("cpf"), jsonObject.getString("password"));
                }
                users.put(user.cpf, user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readFile(File file) {
        if (file.isDirectory() || !file.exists()) return null;
        String everything = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            everything = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return everything;
    }

    private void writeFile(File file, String content) {
        try {
            boolean exists = false;
            if (!file.exists() || file.isDirectory()) {
                File folder = file.getParentFile();
                if (!folder.exists() || folder.isFile()) folder.mkdirs();
                exists = file.createNewFile();
            }
            if (!exists) throw new IOException("Fail create a new file at: " + file.getPath());
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
