# Banking API

## Overview

This repository contains simple Java application of the Fintech domain.

## ERD of the domain model

Domain consists of our bank branches, departments of branches, and bank employees that may or may not be attached to a specific branch or department.
Bank has customers of two types: individuals and businesses. Businesses can have multiple officers, that represent their intereses in the bank.
Bank customers can open accounts according to provided bank products (debit/credit card, deposit etc). Accounts can be opened either with (e.g. in one of bank branches) 
or without (e.g. online via application interface) bank employee. Each account may have any number of financial transactions. Transactions may be made through a bank teller or independently.

<div align="center"><img width="613" alt="ER Diagram" src="https://github.com/maksym-panov/banking-api/assets/53259191/672cc238-077e-4db8-8b7c-8f5efa00af97"></div>

