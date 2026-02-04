# WhatsApp Business API Setup Guide

## Step-by-Step Setup

### Step 1: Create Meta Business Account

1. Go to https://business.facebook.com/
2. Create a business account
3. Verify your business (may require business documents)

### Step 2: Create Meta Developer Account

1. Go to https://developers.facebook.com/
2. Click "Get Started"
3. Complete developer verification

### Step 3: Create Meta App

1. Go to https://developers.facebook.com/apps/
2. Click "Create App"
3. Select "Business" type
4. Fill in app details
5. Add "WhatsApp" product to your app

### Step 4: Get WhatsApp Business Number

**Option A: Use Meta's Test Number (Free, for testing)**
- In WhatsApp product → API Setup
- Meta provides a test number
- Limited to 5 test numbers

**Option B: Use Your Own Number (Production)**
- Need to verify your business
- Apply for WhatsApp Business API access
- May require approval process

### Step 5: Get API Credentials

In your Meta App → WhatsApp → API Setup:

1. **Phone Number ID**
   - Found in "From" field
   - Format: `123456789012345`

2. **Temporary Access Token**
   - Click "Generate access token"
   - Valid for 24 hours (for testing)
   - For production: Create System User and get permanent token

3. **App Secret**
   - Found in App Settings → Basic
   - Click "Show" to reveal

4. **Business Account ID**
   - Found in Business Settings
   - Format: `123456789012345`

### Step 6: Configure Webhook

1. In WhatsApp → Configuration:
   - **Webhook URL**: `https://your-domain.com/webhook`
   - **Verify Token**: Create a random string (e.g., `my_secure_token_123`)
   - Click "Verify and Save"

2. Subscribe to webhook fields:
   - ✅ `messages` (required)
   - ✅ `message_status` (optional, for delivery status)

### Step 7: Test Locally with ngrok

```bash
# Install ngrok
# Download from: https://ngrok.com/

# Start your bot
npm start

# In another terminal, start ngrok
ngrok http 3000

# Copy the https URL (e.g., https://abc123.ngrok.io)
# Use this in Meta webhook configuration: https://abc123.ngrok.io/webhook
```

### Step 8: Add Bot to Group

1. Open WhatsApp on your phone
2. Create or open a group
3. Add your WhatsApp Business number to the group
4. (Optional) Make it admin to enable message deletion

## Cost Information

- **Test Numbers**: Free (limited to 5 numbers)
- **Production**: Pay per conversation
  - User-initiated: Free for first 1,000/month, then paid
  - Business-initiated: Paid per message
  - See: https://developers.facebook.com/docs/whatsapp/pricing

## API Limits

- Rate limits apply
- Check current limits in Meta Dashboard
- Use webhook efficiently to avoid rate limits

## Support

- Meta Developer Docs: https://developers.facebook.com/docs/whatsapp
- WhatsApp Business API Docs: https://developers.facebook.com/docs/whatsapp/cloud-api

