package fr.ca;

import fr.ca.classes.Block;
import fr.ca.classes.BlockChain;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	System.out.println("INIT PROGRAMM");
    String ANSI_RED = "\u001B[31m";

    //create Block
        BlockChain blockChain = new BlockChain();
        Block genesisBlock = blockChain.getBlockList().get(0);

        int i=0;
        while(blockChain.getBlockList().size() <= 5) {
            if (blockChain.isValidChain(genesisBlock)) {
                System.out.println("CHAIN IS VALID");
                launchNewBlockGeneration(blockChain, i);

            }else{
                // invalid chain - genesis block corrupted
                if(i==0){
                    System.out.print("ERROR INVALID CHAIN - genesisBlock corrupted - resetting bloc chain");
                    blockChain = new BlockChain();
                    genesisBlock = blockChain.getBlockList().get(0);
                    i=0;
                    //other block corrupted
                }else{
                    System.out.print("ERROR INVALID CHAIN - one block is corrupted - deleting concerned blocks");
                    for (int j=blockChain.getBlockList().size(); j>i; j--){
                        blockChain.getBlockList().remove(j);
                        i=j;
                    }
                    launchNewBlockGeneration(blockChain, i);
                }
            }
            i++;
        }
    }

    public static void launchNewBlockGeneration(BlockChain blockChain, int i) throws InterruptedException {
        Block newBlock = blockChain.generateBlock();
        Thread.sleep(1000);
        if (blockChain.isValidNewBlock(newBlock)) {
            System.out.println("NEW BLOCK IS VALID - BLOCK-"+i);
            if(newBlock.getTransaction().size() == 5){
                blockChain.getBlockList().add(newBlock);
                System.out.println("NEW BLOCK "+i+" ADDED");
            }
        } else {
            System.out.println("ERREUR - BLOCKCHAIN ERRONEE");
        }

    }
}
