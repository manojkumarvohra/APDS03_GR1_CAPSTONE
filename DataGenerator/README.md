Transactions Data Generator

This module generates around 3.5 lakh dummy transactions (number is configurable) with below highlighted features.
The utility generates a set of transactions with embedded anomalies like out of range spends, cash withdrawals through credit cards, odd hour transactions, illegal or defaulting merchants involved in transaction.

* Features

1. transactionId

2. AccountId
	
3. date of transaction

4. hour of the day for transaction

5. day of week for transaction

	- Monday, Tuesday etc

6. state of the country in which transaction is done

	- "HR", "KA", "MP", "DL", "JK"

7. purpose of transaction

	- "domestic", "repairs", "others", "education", "furniture", "repairs", "tv", "business", "car"

8. medium of transaction

	- "online", "hand_held_terminal", "ATM"

9. amount of transaction

10. credit_limit

11. class label: legal

		- 0 (fishy)
		- 1 (ok)

