package it.unibo.bank.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.bank.api.AccountHolder;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {
    public static final double TRANSACTION_FEE = 0.1;
    public static final double MANAGEMENT_FEE = 5.0;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private StrictBankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), 100);
        final double expectedFee = MANAGEMENT_FEE + bankAccount.getTransactionsCount() * TRANSACTION_FEE;
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(100 - expectedFee, bankAccount.getBalance(), 0.0001);
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        bankAccount.deposit(mRossi.getUserID(), 100);
        assertThrows(IllegalArgumentException.class,
            () -> bankAccount.withdraw(mRossi.getUserID(), -10));
        assertEquals(100, bankAccount.getBalance(), 0.0001);
    }

    @Test
    public void testWithdrawingTooMuch() {
        bankAccount.deposit(mRossi.getUserID(), 100);
        assertThrows(IllegalArgumentException.class,
            () -> bankAccount.withdraw(mRossi.getUserID(), 200));
        assertEquals(100, bankAccount.getBalance(), 0.0001);
    }
}
