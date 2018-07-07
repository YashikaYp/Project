package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.InsetsUIResource;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo {

	private Map<String, Customer> data = new HashMap<>();

	public Map<String, Customer> getData() {
		return data;
	}

	public void setData(Map<String, Customer> data) {
		this.data = data;
	}

	Customer cust = new Customer();

	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}

	public WalletRepoImpl() {
		// super();
	}

	public boolean save(Customer customer) {

		// System.out.println("save method repo layer");
		String mob = customer.getMobileNo();

		if (data.get(mob) == null) {
			// System.out.println("in save method of repo impl (data.get(mob))");
			data.put(mob, customer);
			return true;
		} else
			return false;
	}

	public Customer findOne(String mobileNo) {
		cust = data.get(mobileNo);
		try {
			if (cust != null)
				return cust;
			else
				throw new InvalidInputException("invalid mob no");
		} catch (InvalidInputException e) {
			e.getMessage();
			return null;
		}

	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		cust = data.get(mobileNo);
		Wallet wallet = new Wallet();
		// String mobile=cust1.getMobileNo();
		try {
			if (cust != null) {
				BigDecimal amt = cust.getWallet().getBalance();
				amt = amt.add(amount);

				wallet.setBalance(amt);
				cust.setWallet(wallet);
				return cust;
			}

			else
				throw new InvalidInputException("invalid mob no");
		} catch (InvalidInputException e) {
			e.getMessage();
			return null;
		}

	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		cust = data.get(mobileNo);
		BigDecimal prevAmt = null;
		Wallet wallet = new Wallet();
		try {
			if (cust != null)
				prevAmt = cust.getWallet().getBalance();
			int result = prevAmt.compareTo(amount);
			if (result == 1 || result == 0)

			{
				prevAmt = prevAmt.subtract(amount);
				wallet.setBalance(prevAmt);
				cust.setWallet(wallet);
				return cust;
			} else
				throw new InsufficientBalanceException(
						"You don't have the required balance to withdraw,Please enter a minimum balance to continue");
		} catch (InsufficientBalanceException e) {
			e.getMessage();
			return null;
		}

	}

}
