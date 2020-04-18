package com.iimc.apds03.group1.data.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class TransactionsGenerator {

	private static int good_transactions_iterations = 100000;
	private static int bad_transactions_iterations = 10000;

	private static final String AC_TYPE_LARGE = "LARGE";
	private static final String AC_TYPE_MEDIUM = "MEDIUM";
	private static final String AC_TYPE_SMALL = "SMALL";
	private static int good_transaction_flag = 1;
	private static int bad_transaction_flag = 0;

	private static int min_s_account_number = 1000;
	private static int max_s_account_number = 2000;
	private static int min_m_account_number = 2500;
	private static int max_m_account_number = 3000;
	private static int min_l_account_number = 4500;
	private static int max_l_account_number = 4550;

	private static HashMap<Integer, LocalDate> accountsDateMap = new HashMap<>();
	private static HashMap<Integer, String> lastTransactionPlace = new HashMap<>();
	private static HashMap<Integer, Integer> lastTransactionAmount = new HashMap<>();
	private static HashMap<Integer, String> lastTransactionPurpose = new HashMap<>();

	private static Set<Integer> s_Accounts = new HashSet<>();
	private static Set<Integer> m_Accounts = new HashSet<>();
	private static Set<Integer> l_Accounts = new HashSet<>();

	private static int min_s_amount = 100;
	private static int max_s_amount = 5000;
	private static int min_m_amount = 15001;
	private static int max_m_amount = 35000;
	private static int min_l_amount = 100000;
	private static int max_l_amount = 450000;

	private static int s_credit_limit = 30000;
	private static int m_credit_limit = 80000;
	private static int l_credit_limit = 500000;

	private static int min_good_hh = 8;
	private static int max_good_hh = 22;
	private static int min_bad_hh = 0;
	private static int max_bad_hh = 5;
	private static int year = 2020;
	private static List<String> s_purposeList = Arrays.asList("domestic", "repairs", "others");
	private static List<String> m_purposeList = Arrays.asList("education", "furniture", "repairs");
	private static List<String> l_purposeList = Arrays.asList("business", "car", "tv");

	private static List<String> others_purposeList = Arrays.asList("withdrawal");

	private static List<String> statesList = Arrays.asList("HR", "KA", "MP", "DL", "JK");

	private static List<String> mediums = Arrays.asList("online", "hand_held_terminal");
	private static List<String> other_mediums = Arrays.asList("ATM");

	public static void main(String[] args) throws IOException {

		FileWriter fw = new FileWriter("/home/manojkumarvohra/Documents/apds/capstone/generated_data");

		fw.write("transactionId" + "," + "accountNumber" + "," + "dateOfTransaction" + "," + "hh" + "," + "dayOfWeek"
				+ "," + "state" + "," + "purpose" + "," + "medium" + "," + "amount" + "," + "credit_limit" + ","
				+ "legal" + "\n");
		for (int i = 0; i < good_transactions_iterations; i++) {
			// small bucket
			generateGoodTransaction(fw, min_s_account_number, max_s_account_number, min_s_amount, max_s_amount,
					s_credit_limit, s_purposeList, good_transaction_flag);
			// medium bucket
			generateGoodTransaction(fw, min_m_account_number, max_m_account_number, min_m_amount, max_m_amount,
					m_credit_limit, m_purposeList, good_transaction_flag);
			// large bucket
			generateGoodTransaction(fw, min_l_account_number, max_l_account_number, min_l_amount, max_l_amount,
					l_credit_limit, l_purposeList, good_transaction_flag);
		}

		for (int i = 0; i < bad_transactions_iterations; i++) {
			generateTimeAnomalyTransaction(fw, bad_transaction_flag);
			generatePlaceAnomalyTransaction(fw, bad_transaction_flag);
			generateTerminalAnomalyTransaction(fw, bad_transaction_flag);
			
			generateRangeAnomolyTransaction(fw, bad_transaction_flag, s_Accounts, s_credit_limit, s_purposeList);
			generateRangeAnomolyTransaction(fw, bad_transaction_flag, m_Accounts, m_credit_limit, m_purposeList);
		}
		
		for (int i = 0; i < 100; i++) {
			generateRangeAnomolyTransaction(fw, bad_transaction_flag, l_Accounts, l_credit_limit, l_purposeList);
		}
		
		//add noise
		for (int i = 0; i < 100; i++) {
			generateRangeAnomolyTransaction(fw, good_transaction_flag, s_Accounts, s_credit_limit, s_purposeList);
			generateRangeAnomolyTransaction(fw, good_transaction_flag, m_Accounts, m_credit_limit, m_purposeList);
			generateRangeAnomolyTransaction(fw, good_transaction_flag, l_Accounts, l_credit_limit, l_purposeList);
		}
		
		fw.close();
	}

	private static void generateGoodTransaction(FileWriter fw, int min_account_number, int max_account_number,
			int min_amount, int max_amount, int credit_limit, List<String> purposeList, int transaction_flag)
			throws IOException {
		LocalDate dateOfTransaction = createRandomDate(year);
		String transactionId = UUID.randomUUID().toString();
		int accountNumber = createRandomIntBetween(min_account_number, max_account_number);

		LocalDate lastTransactionDateForAccount = accountsDateMap.get(accountNumber);
		if (lastTransactionDateForAccount != null) {
			dateOfTransaction = lastTransactionDateForAccount.plusDays(1);
		}
		accountsDateMap.put(accountNumber, dateOfTransaction);

		String state = getRandomItemFromList(statesList);
		String lastStateForAccount = lastTransactionPlace.get(accountNumber);
		if (lastStateForAccount != null) {
			state = lastStateForAccount;
		} else {
			lastTransactionPlace.put(accountNumber, state);
		}
		int hh = createRandomIntBetween(min_good_hh, max_good_hh);
		int amount = createRandomIntBetween(min_amount, max_amount);
		lastTransactionAmount.put(accountNumber, amount);

		String purpose = getRandomItemFromList(purposeList);
		lastTransactionPurpose.put(accountNumber, purpose);

		String medium = getRandomItemFromList(mediums);
		DayOfWeek dayOfWeek = dateOfTransaction.getDayOfWeek();

		String accountType = getAccountType(accountNumber);
		switch (accountType) {
		case AC_TYPE_SMALL:
			s_Accounts.add(accountNumber);
			break;
		case AC_TYPE_MEDIUM:
			m_Accounts.add(accountNumber);
			break;
		case AC_TYPE_LARGE:
			l_Accounts.add(accountNumber);
			break;
		}

		fw.write(transactionId + "," + accountNumber + "," + dateOfTransaction + "," + hh + "," + dayOfWeek + ","
				+ state + "," + purpose + "," + medium + "," + amount + "," + credit_limit + "," + transaction_flag
				+ "\n");
	}

	private static void generateTimeAnomalyTransaction(FileWriter fw, int transaction_flag) throws IOException {
		LocalDate dateOfTransaction = createRandomDate(year);
		String transactionId = UUID.randomUUID().toString();
		int randomAccountNumber = getRandomItemFromList(accountsDateMap.keySet());

		LocalDate lastTransactionDateForAccount = accountsDateMap.get(randomAccountNumber);
		dateOfTransaction = lastTransactionDateForAccount.plusDays(1);
		accountsDateMap.put(randomAccountNumber, dateOfTransaction);

		String state = lastTransactionPlace.get(randomAccountNumber);
		int hh = createRandomIntBetween(min_bad_hh, max_bad_hh);
		int amount = lastTransactionAmount.get(randomAccountNumber) + createRandomIntBetween(114, 256);
		String purpose = lastTransactionPurpose.get(randomAccountNumber);
		String medium = getRandomItemFromList(mediums);
		DayOfWeek dayOfWeek = dateOfTransaction.getDayOfWeek();
		int credit_limit = getCreditLimitFor(randomAccountNumber);

		fw.write(transactionId + "," + randomAccountNumber + "," + dateOfTransaction + "," + hh + "," + dayOfWeek + ","
				+ state + "," + purpose + "," + medium + "," + amount + "," + credit_limit + "," + transaction_flag
				+ "\n");
	}

	private static void generateTerminalAnomalyTransaction(FileWriter fw, int transaction_flag) throws IOException {
		LocalDate dateOfTransaction = createRandomDate(year);
		String transactionId = UUID.randomUUID().toString();
		int randomAccountNumber = getRandomItemFromList(accountsDateMap.keySet());

		LocalDate lastTransactionDateForAccount = accountsDateMap.get(randomAccountNumber);
		dateOfTransaction = lastTransactionDateForAccount.plusDays(1);
		accountsDateMap.put(randomAccountNumber, dateOfTransaction);

		String state = lastTransactionPlace.get(randomAccountNumber);
		int hh = createRandomIntBetween(min_good_hh, max_good_hh);
		int amount = getRandomItemFromList(Arrays.asList(500, 1000, 1500, 5000, 10000));
		String purpose = getRandomItemFromList(others_purposeList);
		String medium = getRandomItemFromList(other_mediums);
		DayOfWeek dayOfWeek = dateOfTransaction.getDayOfWeek();
		int credit_limit = getCreditLimitFor(randomAccountNumber);
		fw.write(transactionId + "," + randomAccountNumber + "," + dateOfTransaction + "," + hh + "," + dayOfWeek + ","
				+ state + "," + purpose + "," + medium + "," + amount + "," + credit_limit + "," + transaction_flag
				+ "\n");
	}

	private static void generateRangeAnomolyTransaction(FileWriter fw, int transaction_flag, Set<Integer> accounts, int credit_limit, List<String> purpose) throws IOException {
		LocalDate dateOfTransaction = createRandomDate(year);
		String transactionId = UUID.randomUUID().toString();

		int accountNumber = getRandomItemFromList(accounts);
		LocalDate lastTransactionDateForAccount = accountsDateMap.get(accountNumber);
		if (lastTransactionDateForAccount != null) {
			dateOfTransaction = lastTransactionDateForAccount.plusDays(1);
		}
		accountsDateMap.put(accountNumber, dateOfTransaction);

		String state = lastTransactionPlace.get(accountNumber);

		int hh = createRandomIntBetween(min_good_hh, max_good_hh);
		// spend in near about credit limit
		int amount = (int) ((Float.valueOf(getRandomItemFromList(Arrays.asList("0.75", "0.80", "0.85", "0.90", "0.95")))
				* credit_limit) + createRandomIntBetween(112, 133));
		String transaction_purpose = getRandomItemFromList(purpose);

		String medium = getRandomItemFromList(mediums);
		DayOfWeek dayOfWeek = dateOfTransaction.getDayOfWeek();

		fw.write(transactionId + "," + accountNumber + "," + dateOfTransaction + "," + hh + "," + dayOfWeek + ","
				+ state + "," + transaction_purpose + "," + medium + "," + amount + "," + credit_limit + "," + transaction_flag
				+ "\n");
	}

	private static void generatePlaceAnomalyTransaction(FileWriter fw, int transaction_flag) throws IOException {
		LocalDate dateOfTransaction = createRandomDate(year);
		String transactionId = UUID.randomUUID().toString();
		int randomAccountNumber = getRandomItemFromList(m_Accounts);

		LocalDate lastTransactionDateForAccount = accountsDateMap.get(randomAccountNumber);
		dateOfTransaction = lastTransactionDateForAccount.plusDays(1);
		accountsDateMap.put(randomAccountNumber, dateOfTransaction);

		String state = null;
		int lastIndex = statesList.indexOf(lastTransactionPlace.get(randomAccountNumber));
		if (lastIndex == (statesList.size() - 1)) {
			state = statesList.get(lastIndex - 1);
		} else {
			state = statesList.get(lastIndex + 1);
		}

		int hh = createRandomIntBetween(min_good_hh, max_good_hh);
		int amount = lastTransactionAmount.get(randomAccountNumber) + createRandomIntBetween(191, 556);
		String purpose = lastTransactionPurpose.get(randomAccountNumber);
		String medium = getRandomItemFromList(mediums);
		int credit_limit = getCreditLimitFor(randomAccountNumber);
		DayOfWeek dayOfWeek = dateOfTransaction.getDayOfWeek();
		fw.write(transactionId + "," + randomAccountNumber + "," + dateOfTransaction + "," + hh + "," + dayOfWeek + ","
				+ state + "," + purpose + "," + medium + "," + amount + "," + credit_limit + "," + transaction_flag
				+ "\n");
	}

	private static int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	private static <T> T getRandomItemFromList(Collection<T> items) {
		List<T> itemsCopy = new ArrayList<T>(items);
		return itemsCopy.get(new Random().nextInt(items.size()));
	}

	private static String getAccountType(int accountNumber) {
		if (min_s_account_number <= accountNumber && accountNumber <= max_s_account_number) {
			return AC_TYPE_SMALL;
		} else if (min_m_account_number <= accountNumber && accountNumber <= max_m_account_number) {
			return AC_TYPE_MEDIUM;
		} else if (min_l_account_number <= accountNumber && accountNumber <= max_l_account_number) {
			return AC_TYPE_LARGE;
		} else {
			throw new RuntimeException("Unable to find account slab for generated account number");
		}
	}

	private static int getCreditLimitFor(int accountNumber) {
		String accountType = getAccountType(accountNumber);
		switch (accountType) {
		case AC_TYPE_SMALL:
			return s_credit_limit;
		case AC_TYPE_MEDIUM:
			return m_credit_limit;
		case AC_TYPE_LARGE:
			return l_credit_limit;
		}
		throw new RuntimeException("Can't find credit limit for account");
	}

	private static LocalDate createRandomDate(int year) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 3);
		return LocalDate.of(year, month, day);
	}
}