package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void checkBalance(){
          Wallet newWallet= new Wallet();
          assertEquals(0.0,newWallet.getBalance());
    }
    @Test
    void checkInitialBalance(){
        Wallet newWallet= new Wallet(100);
        assertEquals(100,newWallet.getBalance());
    }
    @Test
    void checkInitialNegativeBalance(){
        Wallet newWallet= new Wallet(-100);
        assertEquals(-100,newWallet.getBalance());
    }
    @Test
    void checkInsertBalanceNegative(){
        Wallet newWallet= new Wallet(100);
        assertThrows(InvalidAmountException.class, () ->{
           newWallet.addFunds(-100);
        });
        assertEquals(100,newWallet.getBalance());
    }
    @Test
    void checkBalanceDeductValue(){
        Wallet newWallet= new Wallet();
        assertThrows(InsufficientFundsException.class, () ->{
            newWallet.deductFunds(500);
        });
        assertEquals(0.0,newWallet.getBalance());
    }
    @Test
    void checkBalanceInsertValue(){
        Wallet newWallet= new Wallet(500);
        newWallet.addFunds(500);
        assertEquals(1000,newWallet.getBalance());
    }
    @Test
    void checkNegativeBalanceInsertValue(){
        Wallet newWallet= new Wallet(500);
        assertThrows(InvalidAmountException.class,() ->{
            newWallet.addFunds(-500);
        });

        assertEquals(500,newWallet.getBalance());
    }
    @Test
    void checkZeroBalanceInsertValue(){
        Wallet newWallet= new Wallet(500);
        assertThrows(InvalidAmountException.class,() ->{
            newWallet.addFunds(0.0);
        });
        assertEquals(500,newWallet.getBalance());
    }
    @Test
    void HundredBalanceInsertValue(){
        Wallet newWallet= new Wallet(500);
        newWallet.addFunds(100);
        assertEquals(600,newWallet.getBalance());
    }

    @Test
    void checkBalanceDeduct(){
        Wallet newWallet= new Wallet(1000);
        newWallet.deductFunds(500);
        assertEquals(500,newWallet.getBalance());
    }
    @Test
    void checkBalanceDeductAll(){
        Wallet newWallet= new Wallet(1000);
        newWallet.deductFunds(1000);
        assertEquals(0.0,newWallet.getBalance());
    }
    @Test
    void checkBalanceDeduction(){
        Wallet newWallet= new Wallet(300);
        assertThrows(InsufficientFundsException.class, () ->{
            newWallet.deductFunds(500);
        });
        assertEquals(300,newWallet.getBalance());
    }
    @Test
    void checkBalanceDeductNegativeValue(){
        Wallet newWallet= new Wallet(100);
        assertThrows(InvalidAmountException.class, () ->{
            newWallet.deductFunds(-50);
        });
        assertEquals(100,newWallet.getBalance());
    }
    @Test
    void checkFundTransfer(){
        Wallet mahadiWallet= new Wallet(100);
        Wallet siamWallet=new Wallet(50);

        mahadiWallet.transferFunds(siamWallet,20);
        assertEquals(80,mahadiWallet.getBalance());
        assertEquals(70,siamWallet.getBalance());

    }
    @Test
    void checkFundTransferAll(){
        Wallet mahadiWallet= new Wallet(100);
        Wallet siamWallet=new Wallet(50);

        mahadiWallet.transferFunds(siamWallet,100);
        assertEquals(0.0,mahadiWallet.getBalance());
        assertEquals(150,siamWallet.getBalance());
    }
    @Test
    void checkFundTransferZero(){
        Wallet mahadiWallet= new Wallet(100);
        Wallet siamWallet=new Wallet(50);

        assertThrows(InvalidAmountException.class, () ->{
            mahadiWallet.transferFunds(siamWallet,0.0);
        });
        assertEquals(100,mahadiWallet.getBalance());
        assertEquals(50,siamWallet.getBalance());

    }
    @Test
    void checkFundTransferNegative(){
        Wallet mahadiWallet= new Wallet(100);
        Wallet siamWallet=new Wallet(50);

        assertThrows(InvalidAmountException.class, () ->{
            mahadiWallet.transferFunds(siamWallet,-50);
        });
        assertEquals(100,mahadiWallet.getBalance());
        assertEquals(50,siamWallet.getBalance());

    }
    @Test
    void checkFundTransferGreaterThanBalance(){
        Wallet mahadiWallet= new Wallet(100);
        Wallet siamWallet=new Wallet(50);

        assertThrows(InsufficientFundsException.class, () ->{
            mahadiWallet.transferFunds(siamWallet,500);
        });
        assertEquals(100,mahadiWallet.getBalance());
        assertEquals(50,siamWallet.getBalance());

    }
    @Test
    void checkFundTransferGreaterThanBalanceSmall(){
        Wallet mahadiWallet= new Wallet(100);
        Wallet siamWallet=new Wallet(50);

        assertThrows(InsufficientFundsException.class, () ->{
            mahadiWallet.transferFunds(siamWallet,101);
        });
        assertEquals(100,mahadiWallet.getBalance());
        assertEquals(50,siamWallet.getBalance());

    }






}