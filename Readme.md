# Terizon Reimbursement System
## Juan Gabriel Diangkinay
WebApp that uses js, css, html which run on apache tomcat server and also using the postgres database to store information, for styling bootstrap is also implemented

# User Stories
An Employee can login
An Employee can view the Employee Homepage
An Employee can logout
An Employee can submit a reimbursement request
An Employee can upload an image of his/her receipt as part of the reimbursement request
An Employee can view their pending reimbursement requests
An Employee can view their resolved reimbursement requests
An Employee can view their information
An Employee can update their information
A Manager can login
A Manager can view the Manager Homepage
A Manager can logout
A Manager can approve/deny pending reimbursement requests
A Manager can view all pending requests from all employees
A Manager can view images of the receipts from reimbursement requests
A Manager can view all resolved requests from all employees and see which manager resolved it
A Manager can view all Employees

# Instructions

# Instructions
# Build Postgres projectzero
Change directory into /db and run:
>docker build -t projectone .

Then run a container:
>docker run -d -p 5432:5432 projectone
