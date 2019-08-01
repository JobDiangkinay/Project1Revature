# Terizon Reimbursement System
## Juan Gabriel Diangkinay
Terizon is a WebApp that uses JavaScript, CSS, and HTML for the front end. 
Terizon runs on Apache Tomcat Server and stores information using the postgres database.

# User Stories
- An Employee can login
- An Employee can view the Employee Homepage
- An Employee can logout
- An Employee can submit a reimbursement request
- An Employee can upload an image of his/her receipt
- An Employee can view their pending reimbursement requests
- An Employee can view their resolved reimbursement requests
- An Employee can view their information
- An Employee can update their information
- A Manager can login
- A Manager can view the Manager Homepage
- A Manager can logout
- A Manager can approve/deny pending reimbursement requests
- A Manager can view all pending requests from all employees
- A Manager can view images of the receipts from reimbursement requests
- A Manager can view all resolved requests from all employees and see which manager resolved it
- A Manager can view all Employees

# Instructions
# Build Postgres projectone
Change directory into /db and run:
>docker build -t projectone .

Then run a container:
>docker run -d -p 5432:5432 projectone
