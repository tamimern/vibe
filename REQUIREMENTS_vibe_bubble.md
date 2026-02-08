# Component Specification: Vibe Meter Bubble (Overlay)

## 1. Overview
The **Vibe Meter Bubble** is the core entry point of the "Vibe" accessibility system. It functions as a persistent **floating overlay** positioned above the WhatsApp interface. It provides real-time feedback on the conversation tone and acts as a trigger to access detailed behavioral data.

## 2. Visual Design & Positioning

### 2.1 Container & Position
*   **Type:** Floating Action Button (FAB) / Overlay.
*   **Default Position:** Bottom-Right corner (fixed), `z-index: 9999` to ensure visibility over WhatsApp chat elements.
*   **Dimensions:** 64px x 64px (Default), shrinks to 48px on mobile scroll.
*   **Shape:** Perfect circle (`rounded-full`).

### 2.2 Aesthetic Style (Cyber-Soft)
*   **Background:** Glassmorphism effect (Blur + Semi-transparent gradient).
*   **Border:** Thin, glowing border (1px) matching the current mood color.
*   **Shadow:** Dynamic "Glow" effect (`box-shadow`) that intensifies based on activity.

### 2.3 Visual States (Dynamic Coloring)
The bubble changes color in real-time based on the `currentVibeScore` (0-100):

| State | Score Range | Color Code | Tailwind Class | Animation |
| :--- | :--- | :--- | :--- | :--- |
| **Excellent** | 80-100 | Mint Green | `bg-emerald-400` | Soft breathing glow |
| **Good** | 60-79 | Electric Blue | `bg-blue-400` | Static / Slow pulse |
| **Warning** | 40-59 | Glowing Orange | `bg-orange-400` | Medium pulse |
| **Critical** | 0-39 | Alert Red | `bg-red-500` | Rapid pulse + Shake |

---

## 3. Functional Behavior

### 3.1 Passive Mode (Default)
*   Displays the numeric **Vibe Score** in the center (e.g., "85").
*   Shows a circular progress ring around the edge representing the score.
*   Monitors keystrokes in the background without obstructing the view.

### 3.2 Interaction: Click / Tap
**Action:** Clicking the bubble triggers the `toggleDashboard()` function.
**Transition:** The bubble expands or opens a Popover/Modal anchored to the bubble.

### 3.3 Active Mode (Expanded Dashboard)
When clicked, the overlay reveals the **Vibe Data Card** containing:

1.  **Real-Time Vibe Gauge:**
    *   Visual semi-circle chart showing the current tone.
    *   Text label: "Positive Vibes", "Getting Heated", etc.

2.  **Session Stats:**
    *   **Sparks Earned:** Count of positive reinforcements received in this session.
    *   **Speed Bumps Hit:** Count of toxic messages prevented.

3.  **Quick Actions:**
    *   "Mute Alerts" (for 15 min).
    *   "Switch Language" (HE/EN toggle).

---

## 4. Technical Implementation Details

### 4.1 React Component Structure
```tsx
// Simplified pseudo-code structure
const VibeOverlay = () => {
  const { vibeScore, status } = useVibeAnalysis();
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="fixed bottom-6 right-6 z-50">
      {/* Expanded Data Card */}
      <AnimatePresence>
        {isOpen && <VibeDataCard score={vibeScore} />}
      </AnimatePresence>

      {/* The Bubble Trigger */}
      <motion.button
        onClick={() => setIsOpen(!isOpen)}
        className={`rounded-full shadow-lg ${getColor(vibeScore)}`}
        whileHover={{ scale: 1.1 }}
        whileTap={{ scale: 0.95 }}
      >
        <CircularProgress value={vibeScore} />
        <span className="font-bold">{vibeScore}</span>
      </motion.button>
    </div>
  );
};
```

### 4.2 Animations (Framer Motion)
*   **Entrance:** Spring animation from scale 0 to 1.
*   **Update:** When score changes, the number should "count up/down" smoothly.
*   **Critical Alert:** If score drops rapidly, apply a `shake` animation keyframe to grab attention.

### 4.3 Accessibility (A11y)
*   **Keyboard Nav:** Must be focusable via `Tab` key.
*   **Screen Readers:** `aria-label="Current Vibe Score is 85. Click for details."`
*   **Contrast:** Ensure white text on colored backgrounds meets WCAG AA standards.

---

## 5. Integration with WhatsApp
*   The component is mounted as a sibling to the main App container.
*   It does **not** interfere with WhatsApp's native click events ( `pointer-events: none` on the wrapper, `pointer-events: auto` on the bubble itself).
