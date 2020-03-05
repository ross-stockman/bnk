# bnk-fullstack
My sandbox project for fullstack development, tutorials, and self training
## Docker
All components in this fullstack application are run by a single `docker-compose up` command
## Stacks
##### bnk-api
Spring boot rest api micro services
##### bnk-data
MySql backend databases, includes table ddl, triggers, stored procedures, and mock data. Used by api and for user auth (TODO)
##### bnk-config
Spring cloud config server reads properties from github as backend source
##### bnk-prometheus
Metrics scraping
##### bnk-grafana
Metrics dashboard
##### bnk-authenticate
TODO: Spring security for rest api access
##### bnk-web
TODO: Some kind of front end view layer
##### bnk-zookeeper
MAYBE: If I find a use case for it
##### bnk-batch
MAYBE: If I find a use case to add some spring batch component. This might be split into multiple components.
##### bnk-messaging
MAYBE: Some kind of queue or pub/sub component, might work with batching component
## TODO
* Automated build - Travis CI
* Junit5
* Service discovery
* Hystrix + Turbine - fault tolerance
* RxJava - asynchronous calls to backend services
* Functional rest api controllers
* Export/Import grafana dashboard
* HTTPS
* Traffic simulation (jmeter?)

## Requirements
##### Database Tables
- [ ] Each table must have the following four standard watermark columns: `_key`, `_version`, `_created`, `_updated`
- [ ] The `_key` column must be guaranteed unique.
- [ ] The `_key` column must allow user to specify a value.
- [ ] The system must generate a value for the `_key` column on initial insert if the user does not specify one.
- [ ] The `_key` column value cannot be modified once committed.
- [ ] The `_version` column value must be system generated on initial insert.
- [ ] The system must generate a new `_version` value upon any update to the table.
- [ ] The `_version` column value must be regenerated on any records affected by a cascading update.
- [ ] If the `_version` column is included in an update, the system must reject the update if the value does not match the existing value on the record being updated. [See Data Integrity and Concurrency Control](#Data-Integrity-and-Concurrency-Control)
- [ ] The `_created` column value must be system generated on initial insert.
- [ ] The `_created` column value cannot be modified once committed.
- [ ] The `_updated` column value must be system generated on initial insert.
- [ ] The system must generate a new `_updated` value upon any update to the table.
- [ ] The `_updated` column value must be regenerated on any records affected by a cascading update.
- [ ] Cascading deletes must not be allowed. [See Data Integrity and Concurrency Control](#Data-Integrity-and-Concurrency-Control)

##### Data Integrity and Concurrency Control
- [ ] The system must prevent concurrent updates from overwriting each other.
- [ ] The system must prevent deletions of resources that are still linked to or referenced by other resources.
