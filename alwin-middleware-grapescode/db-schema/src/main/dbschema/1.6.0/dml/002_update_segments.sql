update ISSUE_TYPE_CONFIGURATION
set segment = 'C'
where segment = 'B';

update ISSUE_TYPE_CONFIGURATION
set segment = 'B'
where segment = 'A';

update ISSUE_TYPE_CONFIGURATION
set segment = 'A'
where segment = 'C';

update COMPANY
set segment = 'C'
where segment = 'B';

update COMPANY
set segment = 'B'
where segment = 'A';

update COMPANY
set segment = 'A'
where segment = 'C';