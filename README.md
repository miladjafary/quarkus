# Having fun with Quarkus


Stock Application is a simple CRUD application which has been written by
[Quarkus framework](https://quarkus.io/) (https://quarkus.io).

## Running The Stock App
You can running the Stock App through below command:
*  mvn quarkus:dev


### Stock App Errors:
In case of processing the service with errors, Stock App will generate an array of errors object 
which format is followed by below objects 
```
[
  {
    "code": string,
    "description": string
  }
]
```
### Error Object Example:
---

```
[
  {
    "code": "INVALID_VALUE",
    "description": "Price must be only digits",
    "param": "price"
  },
  {
    "code": "INVALID_VALUE",
    "description": "Name is required",
    "param": "name"
  }
]
``` 

There are 4 type of RESTful services in Stock App:

- ``GET`` : `/api/stocks`: Return list of stocks
- ``GET`` : `/api/stocks/{id}`: Find stock by `{id}`.The responses of this service are:
    - http `200`: Return stock data as a json format. Simple result would be: 
``
{
  "id": 1,
  "lastUpdate": "2019-12-01T10:54:00",
  "name": "Milad",
  "price": 1000
}
``
    
    - http `404`: If can not find any stock it would return array of errors.

- ``POST`` : `/api/stocks` : Create new Stock. Accept a json followed by bellow format:
```
{
    "name":string,
    "price":number
}
```
- Responses:
    - http `200`: In case of success processing.
    - http `400`: In case of error processing of service. Array of errors object will be returns.

* ``PUT`` : `/api/stocks/{id}` : Update an Stock object. Accept a json followed by bellow format:
```
{"price":number}
```
- Responses:
    - http `200`: In case of success processing.
    - http `400`: In case of error processing of service. Array of errors object will be returns.

* ``DELETE`` : `/api/stocks/{id}` : Delete an Stock object. 
- Responses:
    - http `200`: In case of success processing.
    - http `404`: In case of missing Stock. Array of errors object will be returns.
