# WhatsApp Business Bot - Setup Guide

## Step 1: Get WhatsApp Business API Access

1. Go to [Meta for Developers](https://developers.facebook.com/)
2. Create a Meta Business Account (if you don't have one)
3. Create a new app and select "Business" type
4. Add "WhatsApp" product to your app
5. Get your credentials:
   - **Phone Number ID**: From WhatsApp > API Setup
   - **Access Token**: Generate a temporary token (or permanent token)
   - **Verify Token**: Create your own random string (e.g., "my-secret-verify-token")
   - **Webhook Secret**: Optional, for webhook security

## Step 2: Configure Environment

1. Copy the example env file:
   ```bash
   cd whatsapp-business-bot
   cp env.example .env
   ```

2. Edit `.env` and fill in your credentials:
   ```
   WHATSAPP_PHONE_NUMBER_ID=your_phone_number_id_here
   WHATSAPP_ACCESS_TOKEN=your_access_token_here
   WHATSAPP_VERIFY_TOKEN=your_random_verify_token
   OPENAI_API_KEY=your_openai_api_key
   PORT=3000
   ```

## Step 3: Install Dependencies

```bash
cd whatsapp-business-bot
npm install
```

## Step 4: Build the Project

```bash
npm run build
```

## Step 5: Set Up Webhook

1. **Local Development (using ngrok or similar):**
   ```bash
   # Install ngrok: https://ngrok.com/
   ngrok http 3000
   # Copy the HTTPS URL (e.g., https://abc123.ngrok.io)
   ```

2. **Configure Webhook in Meta:**
   - Go to Meta for Developers > Your App > WhatsApp > Configuration
   - Set Webhook URL: `https://your-domain.com/webhook`
   - Set Verify Token: (same as in your .env file)
   - Subscribe to: `messages` event

3. **Verify Webhook:**
   - Click "Verify and Save"
   - Meta will send a GET request to verify
   - Your server should respond with the challenge

## Step 6: Run the Bot

```bash
npm start
```

Or for development with auto-reload:
```bash
npm run dev
```

## Step 7: Add Bot to WhatsApp Group

1. Get your bot's WhatsApp number (from Meta Business Manager)
2. Add the bot to your WhatsApp group
3. The bot will start monitoring messages automatically

## Testing

1. Send a test message with violent content to the group
2. The bot should detect it and send a warning
3. Check server logs for activity

## Troubleshooting

- **Webhook not receiving messages**: Check ngrok/domain is accessible, verify webhook URL in Meta
- **Messages not being processed**: Check server logs, verify webhook is subscribed to `messages` event
- **API errors**: Verify access token is valid and has correct permissions

