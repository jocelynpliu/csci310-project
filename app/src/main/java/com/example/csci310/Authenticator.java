package com.example.csci310;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Authenticator {
    private Random rand;

    public String getSalt() {
        // IMPLEMENT
        return ""; // so it compiles
    }

    public void encryptPassword(String password, String salt) {
        // IMPLEMENT
    }

    public boolean verifyPassword(String password, String salt) {
        // IMPLEMENT
        return false; // so it compiles
    }

    public byte[] hash(String password, String salt) {
        // IMPLEMENT

        // !!! just so it compiles, REPLACE !!!
        byte[] a = "FIX ME".getBytes(StandardCharsets.UTF_8);
        return a;
    }
}
