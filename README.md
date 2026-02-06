# Tron Game

A two-player light-cycle battle game inspired by the classic Tron movie. Players compete on the same keyboard, leaving trails of light behind their motorcycles. The last player standing wins.

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-007396?style=flat&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql&logoColor=white)

## Overview

Two players battle on the same keyboard, each controlling a light motorcycle that leaves a permanent trail. The objective is to survive longer than your opponent by avoiding walls, trails, and boundaries. The game features 10 progressively difficult levels with increasing speed and shrinking arena.

### Features

- **Local Multiplayer** — Two players on one keyboard (WASD vs Arrow keys)
- **Progressive Difficulty** — 10 levels with increasing speed and shrinking arena
- **Self-Collision Rules** — Self-collision disabled after level 7
- **Persistent Leaderboard** — MySQL database tracks wins across sessions
- **Level Timer** — Tracks elapsed time per round

## Controls

| Player | Up | Down | Left | Right |
|--------|-----|------|------|-------|
| Player 1 | W | S | A | D |
| Player 2 | ↑ | ↓ | ← | → |

## Level Progression

| Levels | Arena Size | Speed | Self-Collision |
|--------|------------|-------|----------------|
| 1-3 | 600×600 → 550×550 | 4-5 | Causes loss |
| 4-6 | 525×525 → 475×475 | 5-6 | Causes loss |
| 7-10 | 450×450 → 375×375 | 6-7 | Allowed |

After level 10, the game cycles back to level 1.

## Project Structure

```
tron-game/
├── src/
│   ├── controller/
│   │   ├── GameController.java
│   │   └── InputHandler.java
│   ├── model/
│   │   ├── Game.java
│   │   ├── Level.java
│   │   ├── Motorcycle.java
│   │   ├── Player.java
│   │   └── Trail.java
│   ├── persistence/
│   │   └── Database.java
│   └── view/
│       ├── Board.java
│       └── MainWindow.java
├── db.properties.example
├── mysql-connector-j-x.x.x.jar
└── README.md
```

## Getting Started

### Prerequisites

- Java 17 or higher
- MySQL 8.0

### Database Setup

1. Create the database:
```sql
CREATE DATABASE tron_game;
USE tron_game;

CREATE TABLE leader_board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    score INT DEFAULT 0
);
```

2. Configure database credentials:
```bash
cp db.properties.example db.properties
```

3. Edit `db.properties` with your MySQL credentials:
```properties
db.user=your_username
db.password=your_password
```

> **Note:** `db.properties` is gitignored to prevent committing credentials.

### Running the Game

```bash
# Compile
javac -cp ".:mysql-connector-j-x.x.x.jar" src/**/*.java -d out

# Run
java -cp "out:mysql-connector-j-x.x.x.jar" view.MainWindow
```

Or import the project into your IDE and run `MainWindow.java`.

## How to Play

1. Enter names for both players when prompted
2. Choose trail colors for each player
3. Avoid hitting walls, opponent's trail, and your own trail (levels 1-6)
4. Win rounds to advance levels and increase your score
5. Check the leaderboard via **Game → Show Leaderboard**

## Menu Options

| Option | Description |
|--------|-------------|
| Show Leaderboard | Display top 10 players by wins |
| Restart Game | Reset to level 1 with current players |

## License

This project is licensed under the MIT License.

## Author

**Isroilbek Jamolov** — [GitHub](https://github.com/Jamolov-Isroilbek)

---

<p align="center">
  <i>Originally developed as a university project at Eötvös Loránd University (2023)</i>
</p>