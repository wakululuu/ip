# User Guide

_**botbot**_ :robot: is a desktop app for managing tasks.

- [Quick Start](#quick-start)
- [Features](#features)
  - [Add a deadline: `deadline`](#add-a-deadline-deadline)
  - [Add a to-do: `todo`](#add-a-to-do-todo)
  - [Add an event: `event`](#add-an-event-event)
  - [Delete a task: `delete`](#delete-a-task-delete)
  - [Edit a task: `edit`](#edit-a-task-edit)
  - [Mark a task as done: `done`](#mark-a-task-as-done-done)
  - [Search for a keyphrase: `find`](#search-for-a-keyphrase-find)
  - [View the task list: `list`](#view-the-task-list-list)
  - [Close *botbot*: `bye`](#close-botbot-bye)
  - [Save the task list](#save-the-task-list)
- [Command Summary](#command-summary)
- [Acknowledgements](#acknowledgements)

<br /><br>
## Quick Start
1. Ensure you have Java 11 or above installed.
1. [Download](https://github.com/wakululuu/ip/releases) the latest release of `botbot.jar`.
1. Move `botbot.jar` to the folder you wish to use as the home folder for *botbot*.
1. Open `botbot.jar` to start the app.
1. Refer to the [Command Summary](#command-summary) for the list of commands available.
1. Type a command in the chat box and press *Enter* to execute.

<br /><br>
## Features 

###### Notes on reading the command format:
- Items in `UPPER_CASE` are fields to be specified by the user.
  - e.g. `todo DESCRIPTION`: The user should specify a `DESCRIPTION`. A sample command is `todo send
   mail`.
- Items in `[SQUARE_BRACKETS]` are optional fields.
  - e.g. `event DESCRIPTION /at DATE [TIME]`: The user can specify a `DATE` with or without a `TIME`.

<br /><br>
### Add a deadline: `deadline`
Adds a deadline to the task list.

Format: `deadline DESCRIPTION /by DATE [TIME]`
- `DATE` must follow the `D-M-YYYY` format.
- `TIME` must follow the `HHMM` format.

Examples:
- `deadline call service centre /by 20-9-2020 1800`
- `deadline buy gifts /by 1-12-2020`

<br /><br>
### Add a to-do: `todo`
Adds a to-do to the task list.

Format: `todo DESCRIPTION`

Example: `todo send mail`

<br /><br>
### Add an event: `event`
Adds an event to the task list.

Format: `event DESCRIPTION /at DATE [TIME]`
- `DATE` must follow the `D-M-YYYY` format.
- `TIME` must follow the `HHMM` format.

Examples:
- `event brunch /at 1-10-2020 1100`
- `event tammy's party /at 16-9-2020`

<br /><br>
### Delete a task: `delete`
Deletes a task from the task list.

Format: `delete INDEX`
- Deletes the task at the specified `INDEX` on the task list.

Example: `delete 3`

<br /><br>
### Edit a task: `edit`
Edits a task on the task list.

Format: `edit INDEX [/d DESCRIPTION] [/at DATE_OF_EVENT [TIME_OF_EVENT]] [/by DATE_OF_DEADLINE [TIME_OF_DEADLINE]]`
- Edits the task at the specified `INDEX` on the task list.
- Specifying a field will overwrite the existing value of that field.
- At least one of the optional fields must be provided.
- For a to-do, only the `DESCRIPTION` can be edited.
- For a deadline, only the `DESCRIPTION`, `DATE_OF_DEADLINE` and `TIME_OF_DEADLINE` can be edited.
- For an event, only the `DESCRIPTION`, `DATE_OF_EVENT` and `TIME_OF_EVENT` can be edited.
- `DATE_OF_DEADLINE` and `DATE_OF_EVENT` must follow the `D-M-YYYY` format.
- `TIME_OF_DEADLINE` and `TIME_OF_EVENT` must follow the `HHMM` format.

Examples:
- `edit 2 /d call tammy /at 16-9-2020 0000` edits the description and time of the 2nd task on the task 
list to `call tammy` and `16-9-2020 0000` respectively.
- `edit 5 /by 31-12-2020` edits the deadline of the 5th task on the task list to `31-12-2020`.

<br /><br>
### Mark a task as done: `done`
Marks a task on the task list as done.

Format: `done INDEX`
- Marks the task at the specified `INDEX` on the task list as done.

Example: `done 1`

<br /><br>
### Search for a keyphrase: `find`
Searches for the specified keyphrase in the task list.

Format: `find KEYPHRASE`
- Only searches the task description.
- The search is case-insensitive.
  - e.g. `Buy` will match `buy`.
- Only matches the full keyphrase.
  - e.g. `buy milk` will not match `buy cheese`.

Example: `find Buy coffee` returns `buy Coffee` and `Buy coffee and tea`.

<br /><br>
### View the task list: `list`
Displays the task list.

Format: `list`

<br /><br>
### Close *botbot*: `bye`
Closes *botbot*.

Format: `bye`

<br /><br>
### Save the task list
*botbot* automatically saves your data in `/data/botbot.txt` in the home folder for *botbot* after 
every command that changes the data. There is no need to save manually.

<br /><br>
## Command Summary

Action | Format
------ | ------
Add | `deadline DESCRIPTION /by DATE [TIME]`<br /><br>`event DESCRIPTION /at DATE [TIME]`<br /><br>`todo DESCRIPTION`
Delete | `delete INDEX`
Edit | `edit INDEX [/d DESCRIPTION] [/at DATE_OF_EVENT [TIME_OF_EVENT]] [/by DATE_OF_DEADLINE [TIME_OF_DEADLINE]]`
Exit | `bye`
Mark as done | `done INDEX`
Search | `find KEYPHRASE`
View | `list`

<br /><br>
## Acknowledgements
- User guide adapted from [https://se-education.org/addressbook-level3/UserGuide.html](https://se-education.org/addressbook-level3/UserGuide.html)