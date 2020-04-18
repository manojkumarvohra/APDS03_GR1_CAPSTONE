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

*SAMPLE

| transactionId                        | accountNumber | dateOfTransaction | hh | dayOfWeek | state | purpose    | medium             | amount | credit_limit | legal |
|--------------------------------------|---------------|-------------------|----|-----------|-------|------------|--------------------|--------|--------------|-------|
| d95bdacb-9949-4b79-87b2-a98571ee8950 | 1560          | 2020-01-01        | 10 | WEDNESDAY | MP    | others     | online             | 3087   | 30000        | 1     |
| 629f9d50-817d-4218-9e08-22025afaf0bf | 2781          | 2020-01-01        | 16 | WEDNESDAY | DL    | education  | online             | 29141  | 80000        | 1     |
| 13c518e8-0c53-4446-a428-c770a750f891 | 2895          | 2020-01-01        | 17 | WEDNESDAY | KA    | furniture  | online             | 28573  | 80000        | 1     |
| 8a641ea1-2d44-4eef-bd76-b7b357479eeb | 1131          | 2020-01-01        | 19 | WEDNESDAY | JK    | repairs    | online             | 273    | 30000        | 1     |
| ee25714f-a127-47f4-bf4e-2e5f162f40fd | 1479          | 2020-01-01        | 20 | WEDNESDAY | DL    | domestic   | online             | 2578   | 30000        | 1     |
| 09e8c7d2-82ad-496a-bc85-ca2ea6e4ec68 | 4536          | 2020-01-01        | 21 | WEDNESDAY | KA    | business   | online             | 193215 | 500000       | 1     |
| 9566f0b2-afdc-4523-9c66-364abdbb1152 | 1981          | 2020-01-02        | 10 | THURSDAY  | DL    | domestic   | hand_held_terminal | 3840   | 30000        | 1     |
| 4f47cff9-d2b1-4914-9e38-8459826dfb7f | 1409          | 2020-05-17        | 11 | SUNDAY    | MP    | withdrawal | ATM                | 500    | 30000        | 0     |
| 5d2f500c-9f79-4f07-9de7-51702d9863f3 | 1871          | 2020-05-17        | 11 | SUNDAY    | HR    | repairs    | hand_held_terminal | 28629  | 30000        | 0     |
| 1e18a440-efd8-4457-a8ee-b08b068f7ab2 | 1935          | 2020-05-17        | 11 | SUNDAY    | JK    | others     | hand_held_terminal | 25618  | 30000        | 0     |
| 80d67405-d735-471d-a69f-2c592b660458 | 1857          | 2020-01-02        | 12 | THURSDAY  | MP    | domestic   | hand_held_terminal | 4727   | 30000        | 1     |
| 0121e10f-d5bd-456f-9872-c72a47d5a5ac | 2907          | 2020-01-02        | 12 | THURSDAY  | DL    | furniture  | hand_held_terminal | 18167  | 80000        | 1     |
