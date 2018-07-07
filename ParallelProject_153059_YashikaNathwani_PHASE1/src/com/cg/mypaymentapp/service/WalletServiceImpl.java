
package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	public WalletRepo repo;
	Customer customer;

	public WalletServiceImpl(Map<String, Customer> data) {
		repo = new WalletRepoImpl(data);
	}

	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() {
		repo = new WalletRepoImpl();
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {

		customer = new Customer(name, mobileNo, new Wallet(amount));
		try
		{
		if(validate(customer.getMobileNo()))
				{
		boolean f;
		f = repo.save(customer);
		return customer;
		
				}

		else
		{
			throw new InvalidInputException("Invalid details");
			
		}
		}
		catch(InvalidInputException e)
		{
			e.getMessage();
			return null;
		}
		

	}

	public Customer showBalance(String mobileNo) {
		customer = repo.findOne(mobileNo);
		if (customer != null)
			return customer;
		else
			return null;
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		Customer c1 = repo.findOne(sourceMobileNo);
		try {
		if (c1 != null) {
			Customer c2 = repo.findOne(targetMobileNo);
			if (c2 != null) {
				c2 = repo.depositAmount(targetMobileNo, amount);
				if (c2 != null) {
					c1 = repo.withdrawAmount(sourceMobileNo, amount);
				} else {
					throw new InsufficientBalanceException("Balance Insufficient");
				}

			} else
				throw new InvalidInputException("Targer account holder not found");

		} else
			throw new InvalidInputException("Source account holder not found");
		}
		
		catch(InsufficientBalanceException e)
		{
			e.getMessage();
			//return null;
		}
		catch(InvalidInputException e)
		{
			e.getMessage();
			//return null;
		}
		return c1;
		
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		customer = repo.depositAmount(mobileNo, amount);
		return customer;

	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		customer = repo.withdrawAmount(mobileNo, amount);
		if (customer != null)
			return customer;
		else
			return null;

	}

	public boolean validate(String phoneno) {

		Pattern p = Pattern.compile("[7-9]?[0-9]{10}");
		Matcher m = p.matcher(phoneno);

		if (m.matches())
			return true;
		else
			return false;
	}

	/*public void acceptDetails(Customer customer) {
		Scanner scan = new Scanner(System.in);
		String str = customer.getMobileNo();
		while (true)
			// Validate phone number
			if (validate(str)) {
				break;
			} else {
				System.err.println("Phone number invalid");
				System.out.println("Enter valid phone number");
				customer.setMobileNo(scan.next());
			}
	}*/
}