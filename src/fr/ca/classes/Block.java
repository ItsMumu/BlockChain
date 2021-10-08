package fr.ca.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class Block {

    public String getPrevHash() {
        return prevHash;
    }

    private String prevHash;
    private List<String> transaction = new ArrayList<>();
    private String date;
    private String currentHash;

    public Block(String prevHash, List<String> transaction, String date){
        this.prevHash = prevHash;
        this.transaction = transaction;
        this.date = date;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public List<String> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<String> transaction) {
        this.transaction = transaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }




}
