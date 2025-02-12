# Graph-Based Social Network System

## Project Overview

This project implements a graph-based social network where students are represented as nodes, and their connections form directed, weighted edges based on wait days. The system utilizes weighted directed graphs for network visualization, shortest path computation (Dijkstra’s Algorithm), edge removal, and weight adjustments.

## Getting Started

### Prerequisites

- Ensure you have an updated version of the **JDK** installed.
- Install **Visual Studio Code** with the **Extension Pack for Java**.

### Setup Instructions

1. Place the following files in the same folder:
   - `Introduction.java`
   - `SocialNetwork.java`
   - `students.csv`
   - `network.csv`
2. Open **Visual Studio Code**.
3. Ensure the **Extension Pack for Java** is installed and enabled.
4. Click the **Explorer** tab and select **Open Folder**.
5. Navigate to the created folder and open it.
6. Open `Introduction.java` in the editor and select **Run**.

## Program Interaction

The user can perform four operations on an AVL tree containing book orders:

### 1. Print Network List for a Given Student

- Enter the **enrollment number** of a student.
- Input must be an **integer**.
- If input is invalid or does not match an existing student, the system prompts for a valid enrollment number.
- If valid, the system displays the student's network (connections with names and enrollment numbers).

### 2. Find the Quickest Path Between Two Students

- Enter the **enrollment number** of the **source student**.
- Enter the **enrollment number** of the **target student**.
- Both inputs must be **integers**.
- If inputs are invalid or do not match existing students, the system provides relevant prompts.
- If valid, the system displays the **shortest path duration** and the **number of students in the path**.

### 3. Remove a Student from Another Student’s Network

- Enter the **enrollment number** of the student whose network is being modified.
- Enter the **enrollment number** of the student to be removed.
- Both inputs must be **integers**.
- If inputs are invalid or do not match existing students, the system provides relevant prompts.
- If valid and the student exists in the network, the system confirms removal.
- If the student is not in the network, the system notifies the user.

### 4. Update Wait Days for a Student

- Enter the **enrollment number** of the student.
- Enter an **integer value** to update wait days.
- If inputs are invalid or do not match an existing student, the system prompts for a valid input.
- If valid, the system confirms the wait time update.

### 5. Quit

- Exits the program.

## Notes

- The program ensures proper validation and error handling.
- All enrollment number inputs must be **valid integers**.
- Invalid inputs result in **clear prompts** guiding the user to correct them.
