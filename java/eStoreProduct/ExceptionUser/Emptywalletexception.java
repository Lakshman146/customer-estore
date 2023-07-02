package eStoreProduct.ExceptionUser;

public class Emptywalletexception extends Exception {
	double amount;
	String msg;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Emptywalletexception(double amount, String msg) {
		super();
		this.amount = amount;
		this.msg = msg;
	}

}
