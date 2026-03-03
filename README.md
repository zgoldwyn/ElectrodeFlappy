# ElectrodeFlappy

An accessibility-focused game controlled by live EMG (electromyography) signals, built as an IB Computer Science Internal Assessment project.

## Overview

ElectrodeFlappy is a Flappy Bird-style game designed to be playable without traditional input devices. Instead of a keyboard or mouse, the player controls the game using muscle contractions detected by surface electrodes placed on the bicep. This makes the game accessible to users who have limited or no ability to use conventional controls — the electrodes can be placed on any muscle group on the body.

The game was built entirely in Java using JavaFX, and interfaces with a **Backyard Brains Muscle SpikerBox** — a bioamplifier that reads EMG signals and outputs them as an audio waveform through a standard 3.5mm audio jack. The program reads this signal in real time via `javax.sound.sampled` and maps muscle contraction events to in-game movement.

## How It Works

### Hardware
A Backyard Brains Muscle SpikerBox is connected to the user via surface electrodes and jumper cables placed on the bicep. The SpikerBox amplifies the EMG signal produced by muscle contractions and outputs it as audio through a 3.5mm auxiliary cord plugged into the computer's microphone input.

### Signal Processing Pipeline
1. Audio is captured in real time using `javax.sound.sampled` at a sample rate of 44,100 Hz, 16-bit mono
2. Raw audio bytes are read into a 1024-byte buffer on a dedicated background thread
3. Every 5 samples, the program computes RMS (root mean square) amplitude and converts it to a decibel value using `dB = 20 * log10(RMS)`
4. A baseline "quiet" dB value is recorded at game start
5. If the live dB value minus the baseline exceeds a user-defined sensitivity threshold, it registers as a muscle contraction — triggering upward movement of the player
6. When the muscle relaxes (volume drops), gravity pulls the player back down

### Sensitivity Calibration
The settings screen allows the user to calibrate minimum and maximum dB thresholds and set a sensitivity value (0–5). This accounts for differences in electrode placement, muscle size, and ambient noise — making the game adaptable to different users and body placements.

### Gameplay
- The player is a ball that moves up on contraction and falls under simulated gravity when relaxed
- Obstacle pairs scroll from right to left, with increasing difficulty over time (gap narrows progressively)
- Collision with an obstacle ends the game
- Score increases with survival time

## Architecture

Three Java files:

- **IAZGoldwyn.java** — Main JavaFX application. Handles UI (settings screen + game screen), audio thread, signal processing, and game loop
- **IABall.java** — Extends `javafx.scene.shape.Circle`. Handles player movement, gravity simulation, sensitivity logic, and ceiling/floor collision
- **IAObstacle.java** — Extends `javafx.scene.shape.Rectangle`. Handles obstacle movement, reset behavior, and progressive difficulty scaling

## Technical Stack

- **Language:** Java
- **UI Framework:** JavaFX
- **Audio I/O:** `javax.sound.sampled` (44,100 Hz, 16-bit, mono)
- **Signal Processing:** RMS amplitude → dB conversion, threshold detection
- **Hardware:** Backyard Brains Muscle SpikerBox (EMG bioamplifier, aux jack output)
- **Threading:** Dedicated daemon audio thread with `Platform.runLater()` for JavaFX updates

## Accessibility

The original motivation was exercise gamification — holding a dumbbell during bicep curls to increase contraction difficulty. However, the broader implication identified during development is accessibility: since electrodes can be placed on any muscle group, the game is operable by users who cannot use conventional input devices. Any voluntary muscle movement is sufficient to play.

## Documentation

Full IB Internal Assessment documentation is included in the `/Documentation` folder:
- Criterion A: Planning
- Criterion B: Design + Record of Tasks
- Criterion C: Development
- Criterion E: Evaluation

## Demo

See `Electrode Flappy.mp4` for a demonstration of the game in action.
