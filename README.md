This is a simple Spring boot application in which we can add user along with their salary using POST /api/users
and get the nth largest salary api/users/getsalary?n=(the salary no you want)
For nth highest salary calculation we have used Java Stream api
ab brief overview is -- .sorted(comparator).. distinct.skip(n-1)
