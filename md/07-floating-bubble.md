# Feature Requirements: Floating Bubble (Vibe Meter)

## 🫧 Feature Overview

**Feature Name:** Floating Bubble / Vibe Meter Widget  
**Priority:** P0 (Core UI Feature)  
**Target Release:** MVP (Phase 1)  
**Estimated Effort:** 2-3 weeks  

### Description
An always-visible, minimally intrusive floating widget that displays the current conversation's Harmony Score. The bubble floats over messaging apps, providing real-time feedback on conversation health while offering quick access to Vibe features through a tap-to-expand menu.

### User Value
- **Ambient Awareness:** Constant visual feedback without interrupting conversation flow
- **Accessibility:** One-tap access to all Vibe features
- **Motivation:** Gamified UI encourages checking and improvement
- **Non-Intrusive:** Small, transparent, movable - doesn't block content
- **Real-Time:** Live updates as conversation tone shifts

---

## 🎯 User Stories

### As a Teen User
```
✓ I want to see the current vibe without opening a separate app
  So that I stay aware while chatting

✓ I want the bubble to be small and movable
  So that it doesn't block important content

✓ I want to tap it to access Vibe features quickly
  So that I can send stickers or check my dashboard

✓ I want it to change color based on the vibe
  So that I get instant visual feedback

✓ I want to be able to minimize or hide it temporarily
  So that I have control over my screen space

✓ I want smooth animations
  So that it feels natural and pleasant to use

✓ I want it to work across all my messaging apps
  So that I'm consistently supported
```

---

## 🎨 UI/UX Specifications

### Visual Design

#### Collapsed State (Default)
```
┌─────────────────────────────────────┐
│                                     │
│                                     │
│                              ┌────┐ │
│                              │ 85 │ │ ← Floating bubble
│                              │ 🟢 │ │   80x80px
│                              └────┘ │
│                                     │
│                                     │
└─────────────────────────────────────┘

Visual Properties:
- Size: 80x80px diameter
- Background: Semi-transparent gradient
- Border: 2px solid with color based on score
- Shadow: Soft drop shadow (0 4px 12px rgba(0,0,0,0.15))
- Blur: Backdrop blur for glass effect
- Z-index: 9999 (always on top)
```

#### Expanded State (After Tap)
```
┌─────────────────────────────────────┐
│                       ┌───────────┐ │
│                       │  Vibe     │ │
│                       │  ━━━━━    │ │
│                       │   85%     │ │
│                       │  🟢 Good  │ │
│                       ├───────────┤ │
│                       │ 📊 Stats  │ │
│                       │ 🌟 Today  │ │
│                       │ ✨ Send+  │ │
│                       │ ━━━━━━━   │ │
│                       │ ⚙️ • ✕    │ │
│                       └───────────┘ │
│                                     │
└─────────────────────────────────────┘

Visual Properties:
- Size: 200x280px
- Animation: Scale from bubble center
- Duration: 300ms spring animation
- Menu items: 4 quick actions + controls
```

### Color States

| Score Range | Border Color | Glow Color | Background | Emoji | Status |
|-------------|--------------|------------|------------|-------|--------|
| 90-100 | #10b981 (Green) | #10b981 | Linear gradient | 🟢 | Excellent |
| 70-89 | #06b6d4 (Cyan) | #06b6d4 | Linear gradient | 🔵 | Good |
| 50-69 | #f59e0b (Orange) | #f59e0b | Linear gradient | 🟠 | Okay |
| 30-49 | #ef4444 (Red) | #ef4444 | Linear gradient | 🔴 | Tense |
| 0-29 | #991b1b (Dark Red) | #991b1b | Linear gradient | 🔴 | Critical |

### Animations

#### Idle State
```javascript
// Gentle pulse animation
const idleAnimation = {
  scale: [1, 1.05, 1],
  transition: {
    duration: 3,
    repeat: Infinity,
    ease: "easeInOut"
  }
};
```

#### Score Change
```javascript
// When score increases
const scoreIncreaseAnimation = {
  scale: [1, 1.2, 1],
  borderColor: ['#06b6d4', '#10b981', '#06b6d4'],
  transition: { duration: 0.5 }
};

// When score decreases
const scoreDecreaseAnimation = {
  scale: [1, 0.9, 1],
  x: [-2, 2, -2, 2, 0],
  transition: { duration: 0.4 }
};
```

#### Tap to Expand
```javascript
const expandAnimation = {
  width: [80, 200],
  height: [80, 280],
  borderRadius: [40, 16],
  transition: {
    type: "spring",
    stiffness: 300,
    damping: 25
  }
};
```

#### Drag to Move
```javascript
// User can drag bubble to reposition
const dragConstraints = {
  top: 0,
  left: 0,
  right: windowWidth - 80,
  bottom: windowHeight - 80
};

// Snap to edge on release
const onDragEnd = (event, info) => {
  const { x, y } = info.point;
  const snapToRight = x > windowWidth / 2;
  
  animate(x, snapToRight ? windowWidth - 80 : 0, {
    type: "spring",
    stiffness: 300
  });
};
```

### Expanded Menu Items

```
┌──────────────────────────────┐
│  Vibe                    × │
├──────────────────────────────┤
│  ━━━━━━━━━━━━━━━━━━━━━━━   │
│          85%                 │
│         🟢 Good              │
│  ━━━━━━━━━━━━━━━━━━━━━━━   │
│                              │
│  📊 My Dashboard             │  ← Opens kid dashboard
│     Check stats & progress   │
│                              │
│  🌟 Today's Summary          │  ← Opens today summary
│     See what happened today  │
│                              │
│  ✨ Send Something Cool      │  ← Opens sticker picker
│     Boost the vibe!          │
│                              │
│  ━━━━━━━━━━━━━━━━━━━━━━━   │
│                              │
│  ⚙️ Settings      ━ Minimize │  ← Controls
│                              │
└──────────────────────────────┘
```

---

## ⚙️ Technical Requirements

### Platform Integration

#### Android - Accessibility Service
```kotlin
class VibeBubbleService : AccessibilityService() {
    private lateinit var windowManager: WindowManager
    private lateinit var bubbleView: View
    
    override fun onServiceConnected() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        bubbleView = LayoutInflater.from(this).inflate(R.layout.bubble_layout, null)
        
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        
        params.gravity = Gravity.TOP or Gravity.END
        params.x = 100
        params.y = 100
        
        windowManager.addView(bubbleView, params)
        
        // Make draggable
        bubbleView.setOnTouchListener(BubbleTouchListener(params, windowManager))
    }
    
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Detect active messaging app
        val packageName = event.packageName?.toString()
        if (isSupportedMessagingApp(packageName)) {
            updateBubbleVisibility(true)
            
            // Get conversation ID from current screen
            val conversationId = extractConversationId(event)
            updateBubbleScore(conversationId)
        }
    }
    
    private fun updateBubbleScore(conversationId: String) {
        // Fetch harmony score from API
        lifecycleScope.launch {
            val score = apiService.getHarmonyScore(conversationId)
            updateBubbleUI(score)
        }
    }
}

// Touch listener for dragging
class BubbleTouchListener(
    private val params: WindowManager.LayoutParams,
    private val windowManager: WindowManager
) : View.OnTouchListener {
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = params.x
                initialY = params.y
                initialTouchX = event.rawX
                initialTouchY = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                params.x = initialX + (event.rawX - initialTouchX).toInt()
                params.y = initialY + (event.rawY - initialTouchY).toInt()
                windowManager.updateViewLayout(v, params)
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Snap to edge
                val screenWidth = Resources.getSystem().displayMetrics.widthPixels
                params.x = if (params.x > screenWidth / 2) screenWidth else 0
                windowManager.updateViewLayout(v, params)
                
                // Detect tap vs drag
                val deltaX = abs(event.rawX - initialTouchX)
                val deltaY = abs(event.rawY - initialTouchY)
                if (deltaX < 10 && deltaY < 10) {
                    // Tap - expand bubble
                    expandBubble()
                }
                return true
            }
        }
        return false
    }
}
```

#### iOS - Screen Recording API
```swift
class VibeBubbleOverlay: UIView {
    private let bubbleSize: CGFloat = 80
    private let expandedSize = CGSize(width: 200, height: 280)
    private var isExpanded = false
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setupBubble()
    }
    
    private func setupBubble() {
        // Create bubble view
        self.frame = CGRect(x: 0, y: 0, width: bubbleSize, height: bubbleSize)
        self.layer.cornerRadius = bubbleSize / 2
        self.backgroundColor = UIColor.systemBlue.withAlphaComponent(0.9)
        
        // Add shadow
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.15
        self.layer.shadowOffset = CGSize(width: 0, height: 4)
        self.layer.shadowRadius = 12
        
        // Add backdrop blur
        let blurEffect = UIBlurEffect(style: .systemUltraThinMaterial)
        let blurView = UIVisualEffectView(effect: blurEffect)
        blurView.frame = self.bounds
        blurView.layer.cornerRadius = bubbleSize / 2
        blurView.clipsToBounds = true
        self.addSubview(blurView)
        
        // Add score label
        let scoreLabel = UILabel()
        scoreLabel.text = "85"
        scoreLabel.textAlignment = .center
        scoreLabel.font = UIFont.boldSystemFont(ofSize: 24)
        scoreLabel.textColor = .white
        self.addSubview(scoreLabel)
        
        // Add pan gesture
        let panGesture = UIPanGestureRecognizer(target: self, action: #selector(handlePan))
        self.addGestureRecognizer(panGesture)
        
        // Add tap gesture
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(handleTap))
        self.addGestureRecognizer(tapGesture)
    }
    
    @objc private func handlePan(_ gesture: UIPanGestureRecognizer) {
        let translation = gesture.translation(in: self.superview)
        
        if let view = gesture.view {
            view.center = CGPoint(
                x: view.center.x + translation.x,
                y: view.center.y + translation.y
            )
        }
        
        gesture.setTranslation(.zero, in: self.superview)
        
        if gesture.state == .ended {
            // Snap to edge
            snapToEdge()
        }
    }
    
    @objc private func handleTap() {
        toggleExpanded()
    }
    
    private func toggleExpanded() {
        isExpanded.toggle()
        
        UIView.animate(
            withDuration: 0.3,
            delay: 0,
            usingSpringWithDamping: 0.7,
            initialSpringVelocity: 0.5,
            options: .curveEaseInOut
        ) {
            if self.isExpanded {
                self.frame.size = self.expandedSize
                self.layer.cornerRadius = 16
            } else {
                self.frame.size = CGSize(width: self.bubbleSize, height: self.bubbleSize)
                self.layer.cornerRadius = self.bubbleSize / 2
            }
            self.layoutIfNeeded()
        }
        
        // Show/hide menu items
        showMenuItems(isExpanded)
    }
    
    private func snapToEdge() {
        guard let superview = self.superview else { return }
        
        let screenWidth = superview.bounds.width
        let shouldSnapRight = self.center.x > screenWidth / 2
        
        UIView.animate(withDuration: 0.3, delay: 0, options: .curveEaseOut) {
            self.center.x = shouldSnapRight 
                ? screenWidth - self.bubbleSize / 2 - 10
                : self.bubbleSize / 2 + 10
        }
    }
    
    func updateScore(_ score: Int, status: String) {
        // Update score display
        scoreLabel.text = "\(score)"
        
        // Update color based on score
        let color = getColorForScore(score)
        self.backgroundColor = color.withAlphaComponent(0.9)
        
        // Animate change
        UIView.animate(withDuration: 0.5) {
            self.transform = CGAffineTransform(scaleX: 1.1, y: 1.1)
        } completion: { _ in
            UIView.animate(withDuration: 0.3) {
                self.transform = .identity
            }
        }
    }
}
```

#### Web - React Component
```typescript
import { motion, useDragControls, AnimatePresence } from 'motion/react';
import { useState, useEffect } from 'react';

interface VibeBubbleProps {
  conversationId: string;
  language: 'he' | 'en';
}

export function VibeBubble({ conversationId, language }: VibeBubbleProps) {
  const [isExpanded, setIsExpanded] = useState(false);
  const [position, setPosition] = useState({ x: window.innerWidth - 100, y: 100 });
  const [harmonyScore, setHarmonyScore] = useState(75);
  const [status, setStatus] = useState<'excellent' | 'good' | 'okay' | 'tense' | 'critical'>('good');
  
  const dragControls = useDragControls();
  
  useEffect(() => {
    // Subscribe to harmony score updates via WebSocket
    const socket = connectWebSocket(conversationId);
    
    socket.on('harmony:update', (data) => {
      setHarmonyScore(data.score);
      setStatus(getStatusFromScore(data.score));
    });
    
    return () => socket.disconnect();
  }, [conversationId]);
  
  const handleDragEnd = (event: any, info: any) => {
    // Snap to nearest edge
    const shouldSnapRight = info.point.x > window.innerWidth / 2;
    const newX = shouldSnapRight ? window.innerWidth - 80 : 0;
    
    setPosition({ x: newX, y: info.point.y });
  };
  
  const getColorForScore = (score: number) => {
    if (score >= 90) return { border: '#10b981', glow: '#10b981', bg: 'from-emerald-500 to-green-400' };
    if (score >= 70) return { border: '#06b6d4', glow: '#06b6d4', bg: 'from-cyan-500 to-blue-400' };
    if (score >= 50) return { border: '#f59e0b', glow: '#f59e0b', bg: 'from-orange-500 to-amber-400' };
    if (score >= 30) return { border: '#ef4444', glow: '#ef4444', bg: 'from-red-500 to-orange-500' };
    return { border: '#991b1b', glow: '#991b1b', bg: 'from-red-700 to-red-600' };
  };
  
  const colors = getColorForScore(harmonyScore);
  const emoji = harmonyScore >= 90 ? '🟢' : harmonyScore >= 70 ? '🔵' : harmonyScore >= 50 ? '🟠' : '🔴';
  
  return (
    <motion.div
      drag
      dragControls={dragControls}
      dragMomentum={false}
      dragElastic={0}
      onDragEnd={handleDragEnd}
      animate={position}
      className="fixed z-50"
      style={{
        top: position.y,
        left: position.x,
      }}
    >
      <AnimatePresence mode="wait">
        {!isExpanded ? (
          // Collapsed bubble
          <motion.div
            key="collapsed"
            initial={{ scale: 0.9, opacity: 0 }}
            animate={{ 
              scale: 1, 
              opacity: 1,
            }}
            exit={{ scale: 0.9, opacity: 0 }}
            whileHover={{ scale: 1.05 }}
            onClick={() => setIsExpanded(true)}
            className={`
              w-20 h-20 rounded-full cursor-pointer
              bg-gradient-to-br ${colors.bg}
              backdrop-blur-lg bg-opacity-90
              shadow-xl
              flex flex-col items-center justify-center
              border-2
            `}
            style={{
              borderColor: colors.border,
              boxShadow: `0 4px 12px rgba(0,0,0,0.15), 0 0 20px ${colors.glow}40`
            }}
          >
            <motion.span 
              className="text-2xl font-bold text-white"
              animate={{ 
                scale: [1, 1.05, 1],
              }}
              transition={{ 
                duration: 3, 
                repeat: Infinity,
                ease: "easeInOut"
              }}
            >
              {harmonyScore}
            </motion.span>
            <span className="text-lg">{emoji}</span>
          </motion.div>
        ) : (
          // Expanded menu
          <motion.div
            key="expanded"
            initial={{ scale: 0.9, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            exit={{ scale: 0.9, opacity: 0 }}
            transition={{ type: "spring", stiffness: 300, damping: 25 }}
            className="
              w-52 bg-white/95 backdrop-blur-xl rounded-2xl shadow-2xl
              overflow-hidden border border-gray-200
            "
            dir={language === 'he' ? 'rtl' : 'ltr'}
          >
            {/* Header */}
            <div className={`
              p-4 bg-gradient-to-br ${colors.bg} text-white
            `}>
              <div className="flex justify-between items-center mb-2">
                <span className="font-bold">Vibe</span>
                <button 
                  onClick={() => setIsExpanded(false)}
                  className="text-white/80 hover:text-white"
                >
                  ✕
                </button>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold mb-1">{harmonyScore}%</div>
                <div className="text-sm opacity-90">{emoji} {status}</div>
              </div>
            </div>
            
            {/* Menu Items */}
            <div className="p-2">
              <MenuItem
                icon="📊"
                title={language === 'he' ? 'הדשבורד שלי' : 'My Dashboard'}
                subtitle={language === 'he' ? 'סטטיסטיקות והתקדמות' : 'Stats & progress'}
                onClick={() => {/* Open dashboard */}}
              />
              <MenuItem
                icon="🌟"
                title={language === 'he' ? 'מה היה היום' : "Today's Summary"}
                subtitle={language === 'he' ? 'אירועי היום' : 'What happened today'}
                onClick={() => {/* Open summary */}}
              />
              <MenuItem
                icon="✨"
                title={language === 'he' ? 'שלח משהו מגניב' : 'Send Something Cool'}
                subtitle={language === 'he' ? 'העלה את האווירה' : 'Boost the vibe!'}
                onClick={() => {/* Open sticker picker */}}
              />
            </div>
            
            {/* Footer */}
            <div className="flex justify-between p-2 border-t border-gray-200">
              <button className="text-gray-600 hover:text-gray-900">⚙️</button>
              <button 
                className="text-gray-600 hover:text-gray-900"
                onClick={() => setIsExpanded(false)}
              >
                ━
              </button>
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </motion.div>
  );
}

function MenuItem({ icon, title, subtitle, onClick }: any) {
  return (
    <motion.button
      whileHover={{ backgroundColor: 'rgba(0,0,0,0.05)' }}
      whileTap={{ scale: 0.98 }}
      onClick={onClick}
      className="w-full p-3 rounded-xl flex items-center gap-3 text-left"
    >
      <span className="text-2xl">{icon}</span>
      <div className="flex-1">
        <div className="font-semibold text-gray-900 text-sm">{title}</div>
        <div className="text-xs text-gray-600">{subtitle}</div>
      </div>
    </motion.button>
  );
}
```

---

## 🗄️ Database Schema

```sql
-- User bubble preferences
CREATE TABLE bubble_preferences (
  user_id UUID PRIMARY KEY REFERENCES users(id),
  enabled BOOLEAN DEFAULT true,
  position_x INTEGER DEFAULT 0,
  position_y INTEGER DEFAULT 100,
  auto_hide_after_seconds INTEGER, -- null = never auto-hide
  show_in_apps JSONB DEFAULT '["whatsapp", "telegram", "instagram"]',
  opacity DECIMAL(3,2) DEFAULT 0.90,
  size VARCHAR(10) DEFAULT 'normal', -- small, normal, large
  updated_at TIMESTAMP DEFAULT NOW()
);

-- Bubble interaction analytics
CREATE TABLE bubble_interactions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id),
  action VARCHAR(50), -- tap, drag, expand, collapse, menu_item_click
  conversation_id VARCHAR(255),
  harmony_score_at_interaction INTEGER,
  timestamp TIMESTAMP DEFAULT NOW(),
  INDEX idx_user_actions (user_id, timestamp DESC)
);
```

---

## 🧪 Testing Requirements

### Unit Tests

```javascript
describe('Floating Bubble', () => {
  test('should update score in real-time', async () => {
    const bubble = renderBubble({ conversationId: 'test-123' });
    
    // Simulate WebSocket update
    emitScoreUpdate({ score: 85 });
    
    await waitFor(() => {
      expect(bubble.getByText('85')).toBeInTheDocument();
    });
  });

  test('should expand on tap', () => {
    const bubble = renderBubble();
    
    fireEvent.click(bubble.getByRole('button'));
    
    expect(bubble.getByText('My Dashboard')).toBeVisible();
  });

  test('should snap to edge on drag end', () => {
    const bubble = renderBubble();
    const element = bubble.container.firstChild;
    
    // Simulate drag to middle
    fireDragEnd(element, { x: window.innerWidth / 2, y: 100 });
    
    // Should snap to nearest edge
    expect(element.style.left).toMatch(/^(0px|.*px)$/);
  });
});
```

---

## 🎯 Success Metrics

### Product Metrics

**Engagement:**
- Users interacting with bubble daily: > 80%
- Average taps per session: 3-5
- Menu item usage: > 60%

**UX:**
- Users finding bubble helpful: > 85%
- Users reporting it's not intrusive: > 80%
- Users customizing position: > 40%

### Technical Metrics

- Render performance: 60fps
- Score update latency: < 100ms
- Battery impact: < 2% additional drain
- Memory footprint: < 50MB

---

## ✅ Definition of Done

- [ ] Bubble renders on Android, iOS, Web
- [ ] Real-time score updates working
- [ ] Drag-to-reposition functional
- [ ] Tap-to-expand smooth (60fps)
- [ ] Menu items navigate correctly
- [ ] Auto-hide feature working
- [ ] Snap-to-edge implemented
- [ ] Battery optimized
- [ ] Accessibility compliant
- [ ] Works across messaging apps
- [ ] User preferences saved
- [ ] Analytics tracking interactions

---

**Document Version:** 1.0  
**Author:** Product Team  
**Last Updated:** February 2026  
**Status:** Ready for Development
