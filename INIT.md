# Execution
from relevant directory which contains pom.xml, execute app with: `mvn compile exec:java -e` (add -q flag for no info). 
Upon executing the command, you've accessed the CLI.

After execution, you're presented with the following commands:

# MANUAL

- `add`: Add a new task, input the task's description.
    
    Usage: 
    1) `add`
    2) `[task-description]`

- `update`: Change tasks' fields (fields available for alteration include the description and status). Time of update is kept as a timestamp.
    
    Usage:
    1) `update`
    2) `[updated-description/stautus]`
    3) `[task-id]`
    
- `delete`: Delete a task, based on its ID. 
    
    Usage: 
    1) `delete`
    2) `[task-id]`

- `list`  : List tasks based on status criteria or list all tasks.
    
    Usage:
    1) `list`
    2) `[criteria: tbd (to be done), inp (in progress), done or all]` 

- `man`: User manual. Lists help for each command. 

- `exit`: Quit the terminal.    