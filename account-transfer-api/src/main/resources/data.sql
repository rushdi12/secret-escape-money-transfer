INSERT INTO Account (accountNumber, accountHolderName, email, balance) VALUES ('12345678', 'Mr Cash', 'rushdi12@gmail.com', 19500);
INSERT INTO Account (accountNumber, accountHolderName, email, balance) VALUES ('98765423', 'Mrs Need-Cash', 'rushdi.hoosain@payu.com', 20500);
INSERT INTO Account (accountNumber, accountHolderName, email, balance) VALUES ('82633234', 'Mr Mario Martinez', 'XXX_mario.martinez@secretescapes.com', 20000);
INSERT INTO Account (accountNumber, accountHolderName, email, balance) VALUES ('76543289', 'Mr Efe Mumoglu', 'XXX_efe.mumoglu@secretescapes.com', 20000);

INSERT INTO Transaction (amount, from_account_id, toAccountNumber) VALUES (500, 1, '98765423');
INSERT INTO Transaction (amount, from_account_id, toAccountNumber) VALUES (500, 2, '12345678');