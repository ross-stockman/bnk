# bnk
## Requirements
##### Database
- [ ] Each table must have the following four standard watermark columns: `_key`, `_version`, `_created`, `_updated`
- [ ] The `_key` column must be guaranteed unique.
- [ ] The `_key` column must allow user to specify a value.
- [ ] The system must generate a unique value if the user does not specify a value for the `_key` column.
