# CathayUnitedBankHW
國泰作業

## SQL語法

> * Create Table
```sql
CREATE TABLE coin_chinese_table(
id char(3) not null primary key,
coin_type char(3),
chinese_name char(3)
);
```
> * Insert Data
```sql
insert into coin_chinese_table values(
'c01',
'USD',
'美金'
);
insert into coin_chinese_table values(
'c02',
'GBP',
'英鎊'
);
insert into coin_chinese_table values(
'c03',
'EUR',
'歐元'
);
```

## 幣別維護功能
> * [GET] Query : http://127.0.0.1:8081/rrj/coinTable/query
> * [POST] Insert : http://127.0.0.1:8081/rrj/coinTable/inset
```json
{
    "id":"c04",
    "coinType":"NTD",
    "chineseName":"新台幣"
}
```
> * [POST] Update : http://127.0.0.1:8081/rrj/coinTable/update
```json
{
    "id":"c04",
    "coinType":"NTD",
    "chineseName":"台幣"
}
```
> * [POST] Delete : http://127.0.0.1:8081/rrj/coinTable/delete
```json
{
    "id":"c04",
    "coinType":"NTD",
    "chineseName":"台幣"
}
```
## coinInfo API
> * [GET] 呼叫coindesk API : http://127.0.0.1:8081/rrj/coin/coindesk
> * coindesk response : 
```json
{
    "time": {
        "updated": "Jun 9, 2022 16:32:00 UTC",
        "updatedISO": "2022-06-09T16:32:00+00:00",
        "updateduk": "Jun 9, 2022 at 17:32 BST"
    },
    "disclaimer": "This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
    "chartName": "Bitcoin",
    "bpi": {
        "USD": {
            "code": "USD",
            "symbol": "&#36;",
            "rate": "30,187.9000",
            "description": "United States Dollar",
            "rate_float": 30187.9
        },
        "GBP": {
            "code": "GBP",
            "symbol": "&pound;",
            "rate": "24,020.9044",
            "description": "British Pound Sterling",
            "rate_float": 24020.9044
        },
        "EUR": {
            "code": "EUR",
            "symbol": "&euro;",
            "rate": "28,255.9347",
            "description": "Euro",
            "rate_float": 28255.9347
        }
    }
} 
```
> * [GET] 轉換API : http://127.0.0.1:8081/rrj/coin/info
> * info response :
```json
{
    "content": {
        "updateTime": "2022/06/9 00:33:00",
        "coinInfos": [
            {
                "coinType": "USD",
                "coinCHNName": "美金 ",
                "coinRate": 30187.3083
            },
            {
                "coinType": "GBP",
                "coinCHNName": "英鎊 ",
                "coinRate": 24020.4336
            },
            {
                "coinType": "EUR",
                "coinCHNName": "歐元 ",
                "coinRate": 28255.3809
            }
        ]
    },
    "msg": null
}
```
