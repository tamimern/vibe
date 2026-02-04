# Dot Peace Chat - Project Documentation

## 🎯 Project Overview

**Dot Peace Chat** is a WhatsApp monitoring and peace-building application that helps detect potentially harmful relationship patterns in WhatsApp conversations. The app uses AI to analyze messages and provides guidance to users when concerning behaviors are detected.

### Core Purpose
- **Monitor WhatsApp conversations** for signs of unhealthy relationship dynamics
- **Detect warning signs** like gaslighting, excessive jealousy, threats, and social isolation
- **Provide guidance** through private messages to help users recognize concerning patterns
- **Support peace-building** by raising awareness about relationship red flags

## 🏗️ Architecture Overview

### Frontend (React + TypeScript)
- **Framework**: React 18 with TypeScript
- **Styling**: Tailwind CSS + shadcn/ui components
- **State Management**: React Context (AuthContext)
- **Routing**: React Router v6
- **Build Tool**: Vite

### Backend (Node.js + Express)
- **Runtime**: Node.js with TypeScript
- **Framework**: Express.js
- **WhatsApp Integration**: Baileys (WhatsApp Web API)
- **AI Analysis**: OpenAI GPT-4
- **Real-time Communication**: Socket.IO
- **Database**: Supabase (PostgreSQL)

### Key Technologies
- **@whiskeysockets/baileys**: WhatsApp Web API wrapper
- **OpenAI**: Message classification and analysis
- **Socket.IO**: Real-time QR code and status updates
- **Supabase**: Authentication and user management

## 🚀 Getting Started

### Prerequisites
- Node.js 18+ 
- npm, yarn, or pnpm
- Supabase account and project
- OpenAI API key

### Installation
```bash
# Clone the repository
git clone <repository-url>
cd dot-peace-chat

# Install dependencies
npm install

# Set up environment variables
cp .env.example .env
# Fill in your API keys and Supabase credentials
```

### Environment Variables
```env
# Supabase
SUPABASE_URL=your_supabase_url
SUPABASE_ANON_KEY=your_supabase_anon_key
SUPABASE_SERVICE_ROLE_KEY=your_service_role_key

# OpenAI
OPENAI_API_KEY=your_openai_api_key

# Server
PORT=4000
NODE_ENV=development
```

### Running the Application
```bash
# Development mode (frontend + backend)
npm run dev:all

# Frontend only
npm run dev

# Backend only
npm run dev:server

# Production build
npm run build
npm start
```

## 📱 Application Flow

### 1. User Authentication
- Users can register/login through Supabase
- Demo mode available for testing (use any email with 'demo')
- Protected routes require authentication

### 2. WhatsApp Connection
- User clicks "Connect to WhatsApp" button
- Backend generates QR code using Baileys
- Frontend displays QR code in modal
- User scans with WhatsApp mobile app
- Connection status updates in real-time via Socket.IO

### 3. Chat Selection
- Once connected, user sees list of WhatsApp chats/groups
- User selects which conversations to monitor
- Selection is sent to backend for activation

### 4. Message Monitoring
- Backend listens to all messages in selected chats
- Each message is analyzed by OpenAI for concerning patterns
- If red flags detected, guidance message sent to user's WhatsApp

## 🔧 Core Components

### Frontend Components

#### `Dashboard.tsx`
- Main application interface
- WhatsApp connection management
- Chat selection and monitoring activation
- Real-time status updates

#### `AuthContext.tsx`
- User authentication state management
- Supabase integration
- Demo mode support
- Protected route handling

#### `QrModal.tsx`
- WhatsApp QR code display
- Connection status feedback
- QR refresh functionality

### Backend Services

#### `baileysManager.ts`
- WhatsApp client management
- Multi-user session handling
- Connection state management
- Event emission for frontend updates

#### `waBot.ts`
- Message processing and analysis
- OpenAI integration for classification
- Guidance message generation
- User strike tracking

#### `conversationAnalyzer.ts`
- Message classification logic
- Category mapping for different warning signs
- Response generation

## 🧠 AI Analysis System

### Message Classification Categories
The AI analyzes messages for these relationship warning signs:

1. **קנאה מוגזמת** (Excessive Jealousy)
2. **גזלייטינג** (Gaslighting)
3. **אובססיביות ושליטה מוגזמת** (Obsessiveness and Excessive Control)
4. **השפלה במסווה של הומור** (Humiliation Disguised as Humor)
5. **אלימות כלכלית** (Economic Violence)
6. **חרם חברתי** (Social Boycott)
7. **איומים** (Threats)
8. **בידוד חברתי** (Social Isolation)
9. **השפלה** (Humiliation)

### AI Prompt Structure
```typescript
const prompt = `
האם ההודעה הבאה מעידה על אחד מתמרורי האזהרה הבאים במערכות יחסים אלימות?
רשימת תמרורים:
- קנאה מוגזמת
- גזלייטינג
- אובססיביות ושליטה מוגזמת
- השפלה במסווה של הומור
- אלימות כלכלית
- חרם חברתי
- איומים
- אלימות פיזית
תשיב רק בשם הקטגוריה המתאימה (למשל: "איומים").
אם אין תמרור – תשיב רק "ללא".
הודעה: "${text}"
`.trim();
```

## 🔌 API Endpoints

### WhatsApp Routes (`/api/wa`)
- `GET /chats` - Retrieve user's WhatsApp chats
- `POST /monitor-chats` - Activate monitoring for selected chats
- `POST /refresh-qr` - Generate new QR code

### Authentication
- Supabase handles user registration, login, and session management
- JWT tokens for API authentication
- Demo mode bypass for testing

## 📊 Database Schema

### Supabase Tables
- **profiles**: User profile information
- **auth.users**: Supabase authentication users
- **sessions**: User sessions and WhatsApp connections

### Key Fields
```sql
-- Example profile structure
{
  id: string (UUID),
  name: string,
  email: string,
  created_at: timestamp,
  updated_at: timestamp
}
```

## 🚨 Error Handling

### Frontend Error Handling
- Toast notifications for user feedback
- Graceful fallbacks for network issues
- Demo mode activation on authentication failures

### Backend Error Handling
- Comprehensive logging for debugging
- Graceful shutdown procedures
- WhatsApp connection error recovery
- OpenAI API error handling

## 🧪 Testing & Demo Mode

### Demo Mode Features
- Bypass authentication for testing
- Simulated user sessions
- WhatsApp QR functionality testing
- Full application flow without real accounts

### Testing Commands
```bash
# Run linting
npm run lint

# Build for production
npm run build

# Preview production build
npm run preview
```

## 🚀 Deployment

### Production Build
```bash
# Build frontend
npm run build

# Build backend
npm run build:server

# Start production server
npm start
```

### Environment Considerations
- Set `NODE_ENV=production`
- Configure production Supabase instance
- Set up production OpenAI API key
- Configure CORS for production domain

## 🔒 Security Features

### Authentication
- Supabase JWT-based authentication
- Protected API routes
- Session management
- Secure password handling

### WhatsApp Security
- Private message analysis only
- No group warnings (privacy-focused)
- User-selected chat monitoring
- Secure session handling

## 📈 Performance Optimizations

### Frontend
- React.memo for expensive components
- Lazy loading for routes
- Optimized bundle splitting with Vite
- Efficient state management

### Backend
- Connection pooling for WhatsApp clients
- Caching for OpenAI responses
- Efficient message processing
- Graceful error recovery

## 🐛 Common Issues & Solutions

### WhatsApp Connection Issues
- **QR not generating**: Check Baileys configuration and OpenAI API key
- **Connection drops**: Verify network stability and session management
- **Multiple users**: Ensure proper user isolation in baileysManager

### Authentication Problems
- **Demo mode not working**: Check localStorage and demo user creation
- **Supabase errors**: Verify environment variables and project configuration
- **Session persistence**: Check AuthContext implementation

### Development Issues
- **Port conflicts**: Use different ports for frontend/backend
- **TypeScript errors**: Ensure proper type definitions and imports
- **Build failures**: Check dependency versions and Node.js compatibility

## 🔮 Future Enhancements

### Planned Features
- Advanced message analytics dashboard
- Custom warning categories
- Multi-language support
- Mobile app development
- Integration with support services

### Technical Improvements
- Enhanced AI model training
- Better error recovery mechanisms
- Performance monitoring and analytics
- Automated testing suite

## 📚 Documentation index (project .md files)

| File | Purpose |
|------|--------|
| [README.md](README.md) | Project overview and doc links |
| [QUICK_START_ANDROID.md](QUICK_START_ANDROID.md) | Quick Android (Vibe) app setup |
| [ANDROID_SETUP_GUIDE.md](ANDROID_SETUP_GUIDE.md) | Full Android Studio setup for Vibe |
| [HOW_TO_ENABLE_VIBE_NOTIFICATIONS.md](HOW_TO_ENABLE_VIBE_NOTIFICATIONS.md) | Unblock and test Vibe notifications |
| [app/MONITORING_WHATSAPP.md](app/MONITORING_WHATSAPP.md) | WhatsApp monitoring and testing (including “Test violence alert”) |
| [app/README_ACCESSIBILITY_SERVICE.md](app/README_ACCESSIBILITY_SERVICE.md) | VibeAccessibilityService implementation details |
| [HOW_TO_FIND_ACCESSIBILITY.md](HOW_TO_FIND_ACCESSIBILITY.md) | Find Vibe in Accessibility settings |
| [HOW_TO_CHECK_LOGCAT.md](HOW_TO_CHECK_LOGCAT.md) | View Vibe logs in Logcat |

---

## 📚 Additional Resources

### Documentation
- [Baileys Documentation](https://github.com/WhiskeySockets/Baileys)
- [Supabase Documentation](https://supabase.com/docs)
- [OpenAI API Documentation](https://platform.openai.com/docs)
- [Socket.IO Documentation](https://socket.io/docs)

### Code Structure
- **Frontend**: `src/` directory with React components
- **Backend**: `server/` directory with Node.js services
- **Configuration**: Root-level config files
- **Database**: `supabase/` directory with migrations

### Key Files to Understand
1. `src/App.tsx` - Main application routing
2. `src/pages/Dashboard.tsx` - Core user interface
3. `server/index.ts` - Backend server setup
4. `server/waBot.ts` - WhatsApp message processing
5. `server/baileysManager.ts` - WhatsApp client management

---

## 🎉 Getting Help

If you need assistance with the project:

1. **Check the logs** - Both frontend and backend have comprehensive logging
2. **Review this documentation** - Most common issues are covered here
3. **Check environment variables** - Ensure all API keys are properly set
4. **Verify dependencies** - Make sure all packages are installed correctly
5. **Test with demo mode** - Use demo login to isolate authentication issues

The application is designed to be robust and user-friendly, with comprehensive error handling and fallback mechanisms. Most issues can be resolved by checking the configuration and ensuring all services are properly connected.

