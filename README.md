# addi_hometest
Home Test Exercise : Sales Evaluator for Leads

Functional Requirements
- Suport Lead(Person) evaluation
- Suport multiple Criteria Steps
- Suport connnection to National Registry (a)
- Support connection to Judicial Records (b)
- Suport connection to our internal System (c) - This will output a random number

Non-Functional Requirements
- Support parallel steps => (a) and (b)
- No API Needed - CLI is enough in initial version
- It must be done using a language-programming that executes on JVM(Java, Kotlin or Scala)
- No databases or MQ needed.
- Only libraries relevant to implementation SHOULD be used

Data Modelling:
Lead
    Id (PK)
    PersonId (Person - FK)
    Status (InEvaluation, Approved, Rejected)

Person
    Id (PK)
    IdentificationNumber
    BirthDate
    FirstName
    LastName
    Email
    Age -- (more field can be added later)

API Endpoints:
/evaluate
- request:
  {
      Lead {
         id
         person {
              identification_number,
              birth_date,
              first_name,
              last_name,
              email,
              age
         }
        }
    }

200 OK
        - response:
                {
                    identification_number,
                    first_name,
                    last_name
                    status
                }
500 ERROR
       - response:
               {
                    identification_number,
                    status,
                    error {
                        message,
                        description
                    }
               }

*****************

Program Execution:
1. launch with different port
-Dserver.port=9000 
2. Use MockServer for LocalTesting for the following URLs:
app.national.url=localhost:8081
app.judicial.url=localhost:8082
3. 