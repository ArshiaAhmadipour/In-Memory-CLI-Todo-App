 AP-Project

## Overview

CLI Todo app developed on Java using a In-Memory Database with validation systems and scalability in mind.

## Features

- Add, update, delete, and retrieve tasks & steps
- Saves and loads data so you don't lose progress
- Uses custom validation and serialization
- Keeps entity IDs the same after loading

## Project Structure

- **`db/`** - Handles database stuff like saving and loading
- **`db/exception/`** - Custom errors for when things go wrong
- **`todo/entity/`** - Where Task and Step classes are
- **`todo/serializer/`** - Handles saving/loading tasks and steps
- **`todo/service/`** - Services to manage tasks and steps
- **`todo/validator/`** - Makes sure tasks and steps are valid

## Commands

Here's what you can do in the program:

- **add task** → Create a new task (asks for title, description, and due date)
- **add step** → Add a step to a task (needs Task ID and title)
- **delete task** → Remove a task by ID
- **delete step** → Remove a step by ID
- **update task** → Change task title, description, due date, or status
- **update step** → Change step title, task reference, or status
- **get task** → Find a task by ID
- **get incomplete tasks** → Show tasks that aren’t done yet
- **get all tasks** → Show all tasks
- **save** → Save everything to `src/db/data/db.txt`
- **exit** → Close the program

## Common Issues

- **Database won’t load?** → Try deleting `db.txt` and restarting.
- **Invalid command?** → Make sure you’re typing it exactly as shown.
- **Tasks/Steps missing?** → Check if they were saved before exiting.

## Notes

This project is for learning and fun. Feel free to change stuff and improve it!

