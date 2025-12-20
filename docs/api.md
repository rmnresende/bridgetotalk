## 📡 API Examples (cURL)

A collection of real examples to help quickly test the API.

## 🔍 1. Get Company by ID



```curl
curl --location 'http://localhost:8080/api/v1/companies/2b28e96b-e73b-4ff2-98bb-898de3ecdd55'
```


## 🏗️ 2. Create Company


```curl
curl --location 'http://localhost:8080/api/v1/companies' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": "668301bb-f6be-49bd-a315-d08a1491a186",
"name": "Cool company",
"slug": "coolcompany",
"email": "contato@startcompany.com",
"phone": "+5511999999999",
"document": "coolcompany",
"status": "ACTIVE",
"plan": "PRO",
"settings": {
"maxAgents": 20,
"maxQueues": 20,
"timezone": "America/Sao_Paulo",
"language": "pt",
"createdAt": "2025-11-21T18:05:16.756701Z",
"updatedAt": "2025-11-21T18:05:16.756706Z",
"plan": "PRO"
},
"createdAt": "2025-11-21T18:05:16.747453Z",
"updatedAt": "2025-11-21T18:05:16.747456Z"
}'
```


## 🏗️ 3. Update Company (General Info)


```curl
curl --location --request PUT 'http://localhost:8080/api/v1/companies/cb031bf8-d0c7-421a-b23e-0eeff82c825b' \
--header 'Content-Type: application/json' \
--data-raw '{
    "phone": "+5511955661111",
    "email": "newemail@startcompany.com"
}'
```


## ⚙️ 4. Update Company Settings


```curl
curl --location --request PUT 'http://localhost:8080/api/v1/companies/cb031bf8-d0c7-421a-b23e-0eeff82c825b/settings' \
--header 'Content-Type: application/json' \
--data '{
"maxAgents": 57,
"maxQueues": 57,
"timezone": "America/Sao_Paulo",
"language": "pt",
"plan": "ENTERPRISE"
}'
```
