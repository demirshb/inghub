# loan service

# Run

to run applicaiton make sure you have docker is installed on your host machine.

clone project into local folder.

go to local folder which is DockerFile is located, make sure docker engine is up and running.

open terminal and create image.

docker build --tag=loan-service:latest .

after image is created. use the docker run command

docker run -p8000:8000 loan-service:latest

# Usage
used technologies, jdk 17, spring boot 3.4.2, spring security, h2
project will be running on port 8000

there are 2 types of user.
ADMIN
CUSTOMER

admin user will be created during application first start.
{
    "email" : "admin@abc.com",
    "password": "admin"
}

there are 3 types of authorized endpoints.

/api/v1/admin
for admin users.

/api/v1/user
for customers

/api/v1/auth
for any user.

normal customer might be saved over related endpoint before start.
http://localhost:8000/api/v1/auth/register
{
    "firstName" : "sihab",
    "lastName" : "demir",
    "email" : "sihab@sihab.com",
    "password": "1234"
}

must login
http://localhost:8000/api/v1/auth/login
{
    "email" : "sihab@sihab.com",
    "password": "1234"
}
login response will have jwt access token. this token must be put Authorization header for all request.

for example, create loan for customer.

curl --location 'http://localhost:8000/api/v1/user/create-loan' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWhhYkBzaWhhYi5jb20iLCJpYXQiOjE3Mzg0MTEwODcsImV4cCI6MTczODQxNDY4N30.bfY6RrA26c6vAgEsYxdQOzCmC8fFNqTBnLNDuFgGWA4' \
--data '{
    "amount" : 500000,
    "numberOfInstallment": 6
}'

admin user can make operation for any customer.
customer can only make operation for him/herself

user endpoints:
/create-loan
/pay-loan
/list-loan
/list-installment

admin endpoints.
/create-loan
/pay-loan
/list-loan
/list-installment

requests for user

curl --location 'http://localhost:8000/api/v1/user/pay-loan?loanId=1&amount=200000' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWhhYkBzaWhhYi5jb20iLCJpYXQiOjE3Mzg0MTAxMzEsImV4cCI6MTczODQxMzczMX0.XK1feJxYZPPZxuz72ESYl9PAf7qbG3zIfCnFrdTTuSs' \
--data '{
    "loanId" : 1,
    "amount": 450000
}'

curl --location 'http://localhost:8000/api/v1/user/list-loan' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWhhYkBzaWhhYi5jb20iLCJpYXQiOjE3Mzg0MjkyMDksImV4cCI6MTczODQzMjgwOX0.PqEDYp4016gssVnRQTlGTVVqZUlAboQyR1FHzPBThVs'

curl --location 'http://localhost:8000/api/v1/user/list-installment?loanId=1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWhhYkBzaWhhYi5jb20iLCJpYXQiOjE3Mzg0MTAxMzEsImV4cCI6MTczODQxMzczMX0.XK1feJxYZPPZxuz72ESYl9PAf7qbG3zIfCnFrdTTuSs'

request for admin

curl --location 'http://localhost:8000/api/v1/admin/create-loan' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhYmMuY29tIiwiaWF0IjoxNzM4NDEwNTMzLCJleHAiOjE3Mzg0MTQxMzN9.QrOXjEXPwICq2XUI0OuOTKLNz5jtQWpc19lYWkjR1TA' \
--data '{
    "amount" : 50000,
    "numberOfInstallment": 6,
    "customerId": 2
}'

curl --location --request POST 'http://localhost:8000/api/v1/admin/list-loan?customerId=1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWhhYkBzaWhhYi5jb20iLCJpYXQiOjE3Mzg0MDg3NjEsImV4cCI6MTczODQxMjM2MX0.Xvh6I8vCSakX_MPjqmrBN2duGarpVwHm87wPaSXV6QM'

curl --location --request POST 'http://localhost:8000/api/v1/admin/list-loan?customerId=1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWhhYkBzaWhhYi5jb20iLCJpYXQiOjE3Mzg0MDg3NjEsImV4cCI6MTczODQxMjM2MX0.Xvh6I8vCSakX_MPjqmrBN2duGarpVwHm87wPaSXV6QM'

customer does not provide customerId. admin user must provide customerId.






