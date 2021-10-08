package fr.ca.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class BlockChain {

    private List<Block> blockList = new ArrayList<>();

    public BlockChain(){
        this.getBlockList().add(createGenesisBlock());
    }

    private static String stringToSha1(String string)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private Block createGenesisBlock(){
        Date date = new Date();
        Block genesisBlock = new Block(stringToSha1("GENESIS_BLOCK"), new ArrayList<>(), String.valueOf(date.getTime()));
        String currentSha1 = generateCurrentSha1(genesisBlock.getPrevHash(), genesisBlock.getTransaction(), genesisBlock.getDate());
        genesisBlock.setCurrentHash(currentSha1);

        return genesisBlock;
    }

    public Block generateBlock(){
        Block prevBlock = this.getBlockList().get(this.getBlockList().size()-1);
        Date date = new Date();
        List<String> transactions = new ArrayList<>();
        transactions.add("trans1");
        transactions.add("trans2");
        transactions.add("trans3");
        transactions.add("trans4");
        transactions.add("trans5");
        Block block = new Block(prevBlock.getCurrentHash(), transactions, String.valueOf(date.getTime()));
        block.setCurrentHash(generateCurrentSha1(block.getPrevHash(), block.getTransaction(), block.getDate()));
        return block;
    }

    public boolean isValidNewBlock(Block newBlock){
        boolean boolToReturn = false;
       if(newBlock.getPrevHash().equals(getBlockList().get(getBlockList().size()-1).getCurrentHash())){
           boolToReturn = true;
       }
       return boolToReturn;
    }

    public boolean isValidChain(Block genesisBlock) {
        boolean boolToReturn = false;

        if (isValidGenesisBlock(genesisBlock)) {
            for (int i = 0; i<getBlockList().size();i++){
                    Block block = getBlockList().get(i);
                    if(i < 1){
                        boolToReturn = isValidGenesisBlock(genesisBlock);
                    }else if(block.getPrevHash().equals(getBlockList().get(i-1).getCurrentHash())){
                        System.out.println("latest block prev hash - "+block.getPrevHash()+" n-1 block current hash - "+getBlockList().get(i-1).getCurrentHash());
                        boolToReturn = true;
                    }else {
                        boolToReturn = false;
                        break;
                    }
            }

        }
        return boolToReturn;
    }


    public boolean isValidGenesisBlock(Block genesisBlock) {
        boolean isValid = false;

        if (genesisBlock.getCurrentHash().equals(this.getBlockList().get(0).getCurrentHash())) {
            isValid = true;
            System.out.println("genesis block in list - current hash - "+getBlockList().get(0).getCurrentHash()+" saved genesis block - current hash - "+genesisBlock.getCurrentHash());
        }
        return isValid;
    }

    private String generateCurrentSha1(String prevHash, List<String> transactions, String date){

        String transaction = transactionToString(transactions);
        String concatAttributes= prevHash+transaction+date;
        String currentHash = stringToSha1(concatAttributes);

        return currentHash;
    }

    private String transactionToString(List<String> transactions){
        String stringToReturn ="";
        for (String transaction: transactions) {
            stringToReturn = stringToReturn + transaction;
        }
        return stringToReturn;
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }
}
