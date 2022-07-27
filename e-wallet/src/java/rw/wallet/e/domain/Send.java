package rw.wallet.e.domain;

/**
 *
 * @author hirwa
 */
public class Send {
    private long recieverAccount;
    private Double sentAmount;

    public Send() {
    }

    public Send(long recieverAccount, Double sentAmount) {
        this.recieverAccount = recieverAccount;
        this.sentAmount = sentAmount;
    }

    public long getRecieverAccount() {
        return recieverAccount;
    }

    public void setRecieverAccount(long recieverAccount) {
        this.recieverAccount = recieverAccount;
    }

    public Double getSentAmount() {
        return sentAmount;
    }

    public void setSentAmount(Double sentAmount) {
        this.sentAmount = sentAmount;
    }
}
