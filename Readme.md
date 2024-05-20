<h1>EVENT-DRIVEN MICROSERVICE</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.o-blue.svg?cacheSeconds=2592000" />
</p>
<br/>

> This project is a demo of a microservices application that leverages the use of events for communication between services.


![Proposed architecture](/Event-Driven%20Transactions%20App%20Architecture.jpeg)

<br/><br/>
The application is in an initial stage, developed based on preliminary research, but improvements to 
be added are discussed below:
1. Security: the application currently has no security implementation, but an IAM auth would be added to simplify the integration
of the security module.
2. Multitenancy: a shared database-shared schema is in use at the moment, but the implementation would be updated to ensure data
isolation of tenants in the system through the implementation of separate database-shared schema multitenant architecture
3. Analytics: next upgrade would include exploring migration from AWS DynamoDB to Snowflake for better monitoring, analytics, and reporting  