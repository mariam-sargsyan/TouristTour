import org.junit.Assert;
import org.junit.Test;

public class AccountTest {
    private double epsilon = 1e-6;

    @Test
    public void depositNumberShouldNotBeNegative() {
        Account account = new Account(50000);
        double balanceBeforeDepositing = account.getBalance();
        double negativeDeposit = -1.0;
        boolean isdeposirinSuccessful = account.deposit(negativeDeposit);
        double balanceAfterDepositing = account.getBalance();
        Assert.assertFalse("Depositing is not possible if the amount deposited is negative"
                , isdeposirinSuccessful);
        Assert.assertEquals("Balance should not change if deposit is negative", balanceBeforeDepositing, balanceAfterDepositing, epsilon);

    }


    @Test
    public void withdrawAmountShouldNotBeNegative() {
        Account account = new Account(50.0);
        double balanceBeforeWithdrawal = account.getBalance();
        double negativeWithdrowal = -1.0;
        boolean isWithdrawalSuccessful = account.withdraw(negativeWithdrowal);
        double balanceAfterWithdraval = account.getBalance();
        Assert.assertFalse("Withdrowal is not possible if the amunt is negative", isWithdrawalSuccessful);
        Assert.assertEquals("Balance should not change is withdrawal is negative",balanceBeforeWithdrawal,balanceAfterWithdraval,epsilon);
    }

    @Test
    public void overdraftLimitIsNotExceeded() {
        double overdraftLimit = 50.0;
        Account account = new Account(overdraftLimit);
        double amountToWithdrawMoreLimit = overdraftLimit + 1.0;
        boolean isSuccessful = account.withdraw(amountToWithdrawMoreLimit);
        Assert.assertFalse("Overdraft Limit cannot be exceeded", isSuccessful);

    }

    @Test
    public void correctAmountShouldBeDeposited() {

        Account account = new Account(50.0);
        double balanceBeforeDeposit = account.getBalance();
        double amountDeposited = 1.0;
        boolean isDepositSuccessful = account.deposit(amountDeposited);
        double balanceAfterDeposit = account.getBalance();
        Assert.assertTrue("The correct amount is deposited", balanceAfterDeposit == balanceBeforeDeposit + amountDeposited);
    }


    @Test
    public void correctAmountShouldBeWithdrawn() {
        Account account = new Account(50.0);
        double amountWithdrawn = 1.0;
        double balanceBeforeWithdrawal = account.getBalance();
        boolean isWithdrawalSuccessful = account.withdraw(amountWithdrawn);
        double balanceAfterWithdrawal = account.getBalance();


        Assert.assertTrue("the correct amount was withdrawn", balanceAfterWithdrawal == balanceBeforeWithdrawal - amountWithdrawn);


    }
@Test
    public void depositAndWithdrawalShouldReturnCorrectResults (){
        Account account = new Account(50.0);
        double amountToDeposit = 2.0;
        double amountToWithdraw = 1.0;
        double balanceBeforeDepositing = account.getBalance();
        boolean isDepositSuccessful = account.deposit(amountToDeposit);
        double balanceAfterDepositing = account.getBalance();

        double balanceBeforeWithdrawal = account.getBalance();
        boolean isWithdrawalSuccessful = account.withdraw(amountToWithdraw);
        double balanceAfterWithdrawal = account.getBalance();


        Assert.assertTrue("The correct amount went to the balance", amountToDeposit==balanceAfterDepositing-balanceBeforeDepositing);
        Assert.assertTrue("The correct amount is withdrawn from the balance",amountToWithdraw ==balanceBeforeWithdrawal-balanceAfterWithdrawal );
}
}
