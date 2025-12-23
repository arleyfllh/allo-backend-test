# Allo Bank Backend Developer Take-Home Test

## Setup/Run Instructions

1. Clone the repository
```
git clone https://github.com/your-github-username/allo-bank-test.git
cd allo-bank-test
```
2. Build the application:
```
mvn clean install
```

3. Run the application:
```
mvn spring-boot:run
```

4. Run tests:
```
mvn test
```

## Endpoint Usage
Single endpoint: ```GET /api/finance/data/{resourceType}```

With resourceType as:
* ```latest_idr_rates```: Returns the latest exchange rates relative to IDR, including the calculated Buy spread of USD to IDR.
* ```historical_idr_usd```: Returns historical IDR to USD rates for the date range of 2024-01-01 to 2024-01-05.
* ```supported_currencies```: Returns all list of supported currency symbols.

### Example cURL commands
1. Latest IDR Rates:
```
curl -X GET "http://localhost:8080/api/finance/data/latest_idr_rates"
```
2. Historical IDR USD:
```
curl -X GET "http://localhost:8080/api/finance/data/historical_idr_usd"
```
3. Supported Currencies:
```
curl -X GET "http://localhost:8080/api/finance/data/supported_currencies"
```

## Personalization Note
* GitHub Username: arleyfllh
* Spread Factor
    * Lowercase
    * Sum of Unicode Values: a=97, r=114, l=108, e=101, y=121, f=102, l=108, l=108, h=104
    * Total: 963
    * Spread Factor Calculation: (963 % 1000) / 100000.0 = 0.00963
