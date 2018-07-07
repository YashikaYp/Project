package com.cg.mypaymentapp.test;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;


public class TestClass {

	
	private static WalletService service;
	private static Map<String,Customer> data;
	@BeforeClass
	public static void initialize()
	{
		data=new HashMap<String,Customer>();
		service=new WalletServiceImpl();
	}
	
	@Before
	public void initData(){
		 Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
	}	
	

	@Test(expected=NullPointerException.class)  //Positive test case; it will pass
	public void testCreateAccount()
	{
		service.createAccount(null, null, null);
	}
	
	@Test(expected=NullPointerException.class)   //Negative test case; it will fail , and so invalid details are printed on console
	public void testCreateAccount1()
	{
		service.createAccount("Yashika", "757585", new BigDecimal(988.0));
		
	}
	
	@Test
	public void testcreateAccount2() // positive test,  it will pass
	{
		
		Customer cust=new Customer();
		cust=service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		
	
		assertEquals("Yashu", cust.getName());
		
	}
	@Test
	public void testcreateAccount3() // negative test,  it will fail
	{
		
		Customer cust=new Customer();
		cust=service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		
	
		assertEquals("7062156698", cust.getMobileNo());
		
	}
	
	@Test
	public void testcreateAccount4() // positive test,  it will pass
	{
		
		Customer cust=new Customer();
		cust=service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		
	
		assertEquals("9877998833", cust.getMobileNo());
		
	}
	@Test
	public void testcreateAccount5() // positive test,  it will pass
	{
		
		Customer cust=new Customer();
		cust=service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		
	
		assertEquals(new BigDecimal(6000), cust.getWallet().getBalance());
		
	}
	@Test
	public void testcreateAccount6() // negative test,  it will fail
	{
		
		Customer cust=new Customer();
		cust=service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		
	
		assertEquals(new BigDecimal(7000), cust.getWallet().getBalance());
		
	}
	@Test  //Positive test case; it will pass, invalid details are sent so on console invalid details is shown as we have caught the exception in service layer
	public void testCreateAccount7()
	{
		service.createAccount("Yashu", "7855", new BigDecimal(9888.0));
	}
	@Test
	public void testFundTransfer() // positive test; it will pass
	{
		Customer cust1=service.createAccount("Yashika", "7062156698", new BigDecimal(7000));
		Customer cust2=service.createAccount("Yashu", "7078990011", new BigDecimal(5000));
		cust1=service.fundTransfer("7062156698", "7078990011", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		BigDecimal expected=cust1.getWallet().getBalance();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFundTransfer1() // negative test; it will fail
	{
		Customer cust1=service.createAccount("Yashika", "7062156698", new BigDecimal(7000));
		Customer cust2=service.createAccount("Yashu", "7078990011", new BigDecimal(5000));
		cust1=service.fundTransfer("7062156698", "7078990011", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		BigDecimal expected=cust2.getWallet().getBalance();
		assertEquals(expected, actual);
	}
	@Test
	public void testDeposit() // positive test; it will pass
	{
		
		Customer cust1=service.depositAmount("9963242422", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		
		assertEquals(new BigDecimal(6700), actual);
	}
	@Test
	public void testDeposit1() // negative test; it will fail and as mobile number is invalid , invalid mobile number printed
	{
		
		Customer cust1=service.depositAmount("9963242428", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		
		assertEquals(new BigDecimal(6700), actual);
	}
	@Test
	public void testWithdraw() // positive test; it will pass
	{
		Customer cust1=service.withdrawAmount("9900112212", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		
		assertEquals(new BigDecimal(8300), actual);
	}
	@Test
	public void testWithdraw1() // negative test; it will fail as expected is 8000 and actual was 8300
	{
		
		Customer cust1=service.withdrawAmount("9900112212", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		
		assertEquals(new BigDecimal(8000), actual);
	}
	@Test
	public void testWithdraw2() // negative test; it will fail as mobile number is wrong and so invalid mobile number is printed
	{
		
		Customer cust1=service.withdrawAmount("9900112214", new BigDecimal(700));
		BigDecimal actual=cust1.getWallet().getBalance();
		
		assertEquals(new BigDecimal(8300), actual);
	}
	@Test
	public void testShowBalance() // positive test, it will pass
	{

		Customer cust1=service.showBalance("9900112212");
		String actual=cust1.getMobileNo();
		
		assertEquals("9900112212", actual);
	}
	@Test
	public void testShowBalance1() // negative  test, it will fail
	{

		Customer cust1=service.showBalance("9900112212");
		String actual=cust1.getMobileNo();
		
		assertEquals("9900112214", actual);
	}
	@After
	public void testAfter()
	{
		service=null;
	}
}
 

