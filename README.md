# Currency Exchange and Discount Calculation - Spring Boot Application
Description
This Spring Boot application integrates with a third-party currency exchange API to retrieve real-time exchange rates and calculates the total payable amount for a bill in a specified currency after applying relevant discounts. The application exposes an API endpoint (/api/calculate) that allows users to submit a bill in one currency and get the payable amount in another currency.

The application implements several business rules for calculating discounts, handles currency conversion, and ensures that the final payable amount is computed correctly for the user based on their attributes and the total bill.

Features
Third-Party API Integration: Integration with a currency exchange API (e.g., Open Exchange Rates or ExchangeRate-API) for real-time currency conversion.

Discount Logic: Apply various discount rules based on user type, customer tenure, and total bill amount.

Currency Conversion: Conversion of the original bill amount to the target currency using real-time exchange rates.

Authentication: Endpoints are secured and require authentication.

API Endpoint: Exposes /api/calculate to calculate the final payable amount in the specified target currency.

Requirements
1. Third-Party API Integration
The application integrates with an external API to fetch real-time exchange rates.

Use the API key from the third-party service (e.g., Open Exchange Rates, ExchangeRate-API) to access exchange rates.

Example endpoint:

bash
Copy
https://open.er-api.com/v6/latest/{base_currency}?apikey=your-api-key
2. Discounts and Currency Conversion Logic
The discount rules are as follows:

Employee: 30% discount.

Affiliate: 10% discount.

Customer for 2+ Years: 5% discount.

$100 Bill: $5 discount for every $100 spent.

Exclusion for Groceries: Percentage-based discounts do not apply to grocery items.

One Discount at a Time: A user can apply only one percentage-based discount.

The bill total is converted from the original currency to the target currency using the retrieved exchange rates.

The final payable amount is calculated after applying the applicable discounts.

3. Authentication
The application requires authentication for the /api/calculate endpoint.

4. Endpoint Exposure
POST /api/calculate: Accepts the following details:

items: List of items in the bill with their categories.

totalAmount: Total amount of the bill before any discounts.

userType: Type of user (e.g., employee, affiliate, or customer).

customerTenure: Number of years the user has been a customer.

originalCurrency: The currency of the original bill (e.g., USD, EUR).

targetCurrency: The currency to convert the bill to (e.g., GBP, INR).

Response: The final payable amount in the target currency after applying the applicable discounts and currency conversion.
