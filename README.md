Coffee Shop
===========

## General instructions

We need to develop a small software to run our Coffee Shop. You can place an order, print the receipt, and we are going to apply some promotions, if it is the case.

This is the list of available products:

| Product Name | Price  |
|:------------:|:------:|
|    Latte     |  $5.3  |
|   Espresso   |   $4   |
|   Sandwich   | $10.10 |
|     Milk     |   $1   |
|  Cake Slice  |   $9   |
|  Cappuccino  |   $8   |
|     Tea      |  $6.1  |

## Coffee Shop Management

### 1) Print the Menu:

   List of all available products and their prices.
   
### 2) Print the order receipt with the following fields:
*    Amount
*    Product Name
*    Unit price
*    Total

### 3) Add the following promotions:
* If you order 2 lattes, you will receive a free espresso.
* If the order has more than 8 products give a 5% discount on the total.
* When the order has food and drinks with total value over $50, each Latte will cost $3.
  * Notes:
    * The final price after the promotion is applied can be under 50.
    * In cases that the order is suitable for more than one promotion, only one should be active (the cheapest for the client).

##   Additional comments

*    Feel free to use any dependency/framework you want and contact us if you have any questions or comments.
*    Do not build a frontend.
*    Do not build user authentication.
*    Using a database is optional. If you want to use it, prefer an "in memory" one.
*    To print information you can use any resource you want like sys.out for example.

## TODO

* Add unit and integration tests to Infrastructure layer.
* Add tests for promotion service to make sure it works in all cases.
