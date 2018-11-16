# crypto-price-check
A simple price checking tool that shows the difference between the prices of multiple crypto exchanges.

## Compenents
### Frontend
* React.js for Single Page Application
* Stomp for Websockets
* Material-ui for table, Responsive,
* Recharts for line chart
* Viewable in Mobile Browsers

### Backend
* Java + Spring boot + Spring Data
* Database is DynamoDB
    - faster than relational
* Schema is {
    ticktime: xxxx-xx-xx xx:xx:xx, 
    pair: ETHBTC, 
    exchanges: [
        {price: 50.1, exchange: 'exchange1'},
        {price: 50.1, exchange: 'exchange2'},
        {price: 50.1, exchange: 'exchange3'}, ]
    }
* Data Collector runs every 30 seconds
* Using Dockers
* deployed in AWS ECS

## TODO
* Use TSDB for database (either timeline, OpenTSDB or Quasardb)
* Batch saving using Spring Batch
* improve security groups in AWS
* deploy in AWS Kubernetes