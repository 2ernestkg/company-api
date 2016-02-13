# COMPANY API
This web application is developed to provide an API for a company repository. Company repository keeps the list of registered companies.
API is developed as RESTFul web service. Below you can find the description of object model you can operate with and API usage description.

## Object Model (Tables)
There is two object models(tables) are provided through this API:

* Company
* Beneficiar

#### Company

| Column				| Type		 			| M	| Description |
| ------------- | ------------- | - | ----------- |
| id          	| int		 				| 	| Identifier given to this company |
| name      		| varchar(50)		| M	| Name of the company |
| address   		| varchar(150)	| M	| Address of the company |
| city					| varchar(50)		| M	| City where company is located |
| country				| varchar(50) 	| M	| Country where company is located |
| email					| varchar(50)		| 	| Email address of the company |
| phone_number 	| varchar(50)   | 	| Contact phone number |
| owners				| array					| 	| List of beneficiars of this company |

#### Beneficiar

| Name      | Type      	| M | Description |
| --------- | ----------- | - | ----------- |
| name      | varchar(50) | M | Name of beneficiar |


## API USAGE

### URLS

| URL | Method | Description |
| --- | ------ | ------ |
| http://&lt;base&gt;/company/ | GET | Returns list of companies |
| http://&lt;base&gt;/company/ | POST | Creates a new company, data should be passed in JSON format |
| http://&lt;base&gt;/company/&lt;id&gt; | GET | Returns the data of company found by given _id_ |
| http://&lt;base&gt;/company/&lt;id&gt; | PUT | Updates the data of company found for given _id_, data should be passed in JSON format |
| http://&lt;base&gt;/company/&lt;id&gt;/owner | PUT | Adds beneficiar to given company, beneficiar data should be in JSON format |

### Return status

For all *URLS*, if api call was successfully completed you get back a json data

| Status | Description |
| ------ | ----------- |
| 200    | Data is retrieved successfully |
| 201    | Data is stored, created |
| 202    | Operation was successfull (update, add) |
| 400    | JSON format is wrong or data validation did not pass |
| 404    | Searched item is not found |
| 409    | Data conflicts (constraint violations) |
| 500    | Server error, something is wrong on server side |

### USAGE
1. Retrieve list of all companies

```bash
$ curl -XGET http://localhost:8080/company
```
```
	[
	  {
	    "id": 1,
	    "name": "Apple",
	    "address": "Baker St. #35",
	    "city": "San Francisco",
	    "country": "USA",
	    "owners": [
	      {
	        "name": "Steve Jobs"
	      }
	    ]
	  },
	  {
	    "id": 2,
	    "name": "Google",
	    "address": "Baker St. #34",
	    "city": "San Francisco",
	    "country": "USA",
	    "email": "contactme@google.com",
	    "owners": [
	      {
	        "name": "Larry Page"
	      },
	      {
	        "name": "Sergey Brin"
	      }
	    ]
	  }
	]
```
2. Add a company
```bash
$ curl -XPOST -H "Content-Type: application/json" http://localhost:8080/company --data-raw '{ "name":"AT&T", "address": "Bayker St. #33", "city":"San Diego", "country":"USA"}'
```
```
{"id":1,"name":"AT&T","address":"Bayker St. #33","city":"San Diego","country":"USA","owners":[]}
```
3. Get the company data
```bash
$ curl -XGET http://localhost:8080/company/1
```
```
{"id":1,"name":"AT&T","address":"Bayker St. #33","city":"San Diego","country":"USA","owners":[]}
```
4. Update company data
```bash
$ curl -XPUT -H "Content-Type: application/json" http://localhost:8080/company/1 --data-raw '{ "name":"AT&T", "address": "Bayker St. #33", "city":"San Jose", "country":"USA"}'
```
```
{"id":1,"name":"AT&T","address":"Bayker St. #33","city":"San Jose","country":"USA","owners":[]}
```
5. Add beneficiar to company
```bash
$ curl -XPUT -H "Content-Type: application/json" http://localhost:8080/company/1/owner --data-raw '{ "name": "Jhon Stephens"}'
```
```
{"id":1,"name":"AT&T","address":"Bayker St. #33","city":"San Jose","country":"USA","owners":[{"name":"Jhon Stephens"}]}
```
