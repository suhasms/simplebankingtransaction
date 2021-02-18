# simplebankingtransaction
A simple banking transaction within same bank and across different bank.

Refer design from https://github.com/suhasms/simplebankingtransaction/blob/main/design/design.png
[<img src="https://github.com/suhasms/simplebankingtransaction/blob/main/design/design.png">](Design)


This is a banking transaction stimulating transciton within same bank and transaction outside the bank.

Build process:

1. Gradle is used for building and create jar file.
2. To create tomcat embeded jar file run `gradle bootJar` from command line.
3. This has been already done and stored in `https://github.com/suhasms/simplebankingtransaction/tree/main/build/libs` folder for your convenience.
t

Prerequisites:
This application requires 
1. Java 8
2. Mysql 5.7 or higher
3. gradle to build.
4. Create 2 empty databases, `mybank` and `samplebank`. `mybank` stores accounts of `mybank` and `samplebank` stores accounts of `samplebank`.

To view database schema check this https://github.com/suhasms/simplebankingtransaction/blob/main/src/main/resources/db/migration/V1.0__createschema.sql

Run application
1. This application uses flyway migrator, so users and accounts are added as part of the application, once the application runs it creates required users and accounts.
1. Run application as 2 different instances, one in port 8050(mybank) and one in port 8070(samplebank). This is to stimulate 2 different banks. 
2. To run application specific to mybank in port 8050 use this command.
`java -jar simplebankingtransaction-0.0.1-SNAPSHOT.jar --bank.name=mybank --server.port=8050`
3. To run application specific to samplebank in port 8070 use this command. Run this from other terminal
`java -jar simplebankingtransaction-0.0.1-SNAPSHOT.jar --bank.name=samplebank --server.port=8070`


This application uses token based authentication for users. And token based authentication for interbank transactions.

1. Send money within same bank.
    1. Login
        `curl --location --request POST 'localhost:8050/authentication/login?username=first_user&password=pass'`
        You will return a json response `{"code":200,"token":"tVaYQoSZAK","message":"Login successful"}` with token.
        If netbanking access doesn't exist then you will get error.`{"code":401,"message":"Invalid login"}`
        Use token for further transactions.
    2. Send money to account within same bank.
        `curl --location --request POST 'http://localhost:8050/transaction/sendMoney?from_account_no=9999&to_account_no=8888&to_bank=mybank&amount=250&token=eRYZJEfBkm'`
        On success you will get `{"success":true,"message":"Succesfully sent money"}`
        Here you will be able to send money on these conditions
        1.Make sure account exist from which you send money, and account for which you send money.
        2.Sufficient balance exist in the account.
        3.Token is not expired, or user is not logged out.
           In all the above case you will get suitable error code with message. 
        
2. Send money outside the bank
    1. Login
        `curl --location --request POST 'localhost:8050/authentication/login?username=first_user&password=pass'`
        You will return a json response `{"code":200,"token":"tVaYQoSZAK","message":"Login successful"}` with token.
        If netbanking access doesn't exist then you will get error.`{"code":401,"message":"Invalid login"}`
        Use token for further transactions.
    2. Send money to account from other bank.
        `curl --location --request POST 'http://localhost:8050/transaction/sendMoney?from_account_no=9999&to_account_no=8888&to_bank=samplebank&amount=250&token=eRYZJEfBkm'`
        On success you will get `{"success":true,"message":"Succesfully sent money"}`
        Here you will be able to send money on these conditions
        1.Make sure account exist from which you send money, and account from other bank exist.
        2.Sufficient balance exist in the account.
        3.Token is not expired, or user is not logged out.
           In all the above case you will get suitable error code with message.   
           
3. To view account balance.
Make sure account exist and accountNo belongs to user.
`curl --location --request GET 'http://localhost:8050/transaction/viewAccountDetails?account_no=9999&token=eRYZJEfBkm'`
