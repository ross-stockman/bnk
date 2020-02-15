# bnk
## Requirements
##### Database Tables
- [ ] Each table must have the following four standard watermark columns: `_key`, `_version`, `_created`, `_updated`
- [ ] The `_key` column must be guaranteed unique.
- [ ] The `_key` column must allow user to specify a value.
- [ ] The system must generate a value for the `_key` column on initial insert if the user does not specify one.
- [ ] The `_key` column value cannot be modified once committed.
- [ ] The `_version` column value must be system generated on initial insert.
- [ ] The system must generate a new `_version` value upon any update to the table.
- [ ] If the `_version` column is included in an update, the system must reject the update if the value does not match the existing value on the record being updated. [See Data Integrity and Concurrency Control](#Data-Integrity-and-Concurrency-Control)
- [ ] The `_created` column value must be system generated on initial insert.
- [ ] The `_created` column value cannot be modified once committed.
- [ ] The `_updated` column value must be system generated on initial insert.
- [ ] The system must generate a new `_updated` value upon any update to the table.
##### Data Integrity and Concurrency Control
