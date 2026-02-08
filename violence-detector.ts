/**
 * Level 1: Content Analysis Layer
 * Violence Detection Service
 * 
 * Platform-agnostic service for detecting violence in messages
 */

import OpenAI from 'openai';

export interface GenericMessage {
  platform: 'whatsapp' | 'telegram' | string;
  messageId: string;
  chatId: string;
  senderId: string;
  senderName?: string;
  text: string;
  timestamp: number;
  metadata?: Record<string, any>;
}

export interface ViolenceDetectionResult {
  hasViolence: boolean;
  isClearViolence: boolean; // Clear/keyword-based violence
  category?: string;
  confidence: number; // 0.0 to 1.0
  matches: string[];
  explanation?: string;
}

export class ViolenceDetector {
  private openai: OpenAI | null = null;

  constructor(openaiApiKey?: string) {
    if (openaiApiKey) {
      this.openai = new OpenAI({ apiKey: openaiApiKey });
    }
  }

  /**
   * Detect violence in a message
   * Uses AI only - every message is analyzed by AI if OpenAI is configured
   */
  async detectViolence(message: GenericMessage): Promise<ViolenceDetectionResult> {
    console.log(`\n${'='.repeat(80)}`);
    console.log(`[ViolenceDetector] 🔍 ANALYZING MESSAGE FOR VIOLENCE`);
    console.log(`${'='.repeat(80)}`);
    console.log(`[ViolenceDetector] 📝 Text: "${message.text}"`);
    console.log(`[ViolenceDetector] 👤 Sender: ${message.senderName || message.senderId}`);
    console.log(`[ViolenceDetector] 💬 Group: ${message.chatId}`);
    console.log(`[ViolenceDetector] 🕐 Timestamp: ${new Date(message.timestamp).toISOString()}`);
    
    // Always use AI if configured (for every message)
    if (this.openai) {
      try {
        const aiAnalysis = await this.analyzeWithAIRetry(message.text);
        
        // Flag if AI detected violence (lower threshold for better detection)
        // Single words like "hate" and "kill" should be flagged
        const hasViolence = aiAnalysis.hasViolence === true && (aiAnalysis.confidence || 0) >= 0.5;
        const confidenceThreshold = 0.5;
        
        console.log(`[ViolenceDetector] ${'─'.repeat(80)}`);
        console.log(`[ViolenceDetector] 🤖 AI ANALYSIS RESULTS:`);
        console.log(`[ViolenceDetector]   • Raw hasViolence: ${aiAnalysis.hasViolence}`);
        console.log(`[ViolenceDetector]   • Confidence: ${aiAnalysis.confidence} (threshold: ${confidenceThreshold})`);
        console.log(`[ViolenceDetector]   • Category: ${aiAnalysis.category || 'none'}`);
        console.log(`[ViolenceDetector]   • Explanation: ${aiAnalysis.explanation || 'No explanation'}`);
        console.log(`[ViolenceDetector] ${'─'.repeat(80)}`);
        
        if (hasViolence) {
          console.log(`[ViolenceDetector] ⚠️  ⚠️  ⚠️  VIOLENCE DETECTED! ⚠️  ⚠️  ⚠️`);
          console.log(`[ViolenceDetector] ✅ Decision: FLAG MESSAGE (confidence ${aiAnalysis.confidence} >= ${confidenceThreshold})`);
        } else {
          if (aiAnalysis.hasViolence === true && (aiAnalysis.confidence || 0) < confidenceThreshold) {
            console.log(`[ViolenceDetector] ⚠️  AI detected violence but confidence too low (${aiAnalysis.confidence} < ${confidenceThreshold})`);
            console.log(`[ViolenceDetector] ✅ Decision: IGNORE (low confidence)`);
          } else {
            console.log(`[ViolenceDetector] ✅ Decision: NO VIOLENCE DETECTED`);
          }
        }
        console.log(`${'='.repeat(80)}\n`);
        
        return {
          ...aiAnalysis,
          hasViolence, // Use threshold-filtered result
          isClearViolence: false, // No keyword matching, so not "clear" violence
          matches: [], // No keyword matches
        };
      } catch (error: any) {
        console.error(`[ViolenceDetector] ${'─'.repeat(80)}`);
        console.error('[ViolenceDetector] ❌ AI ANALYSIS FAILED (after retries)!');
        
        const errorType = this.detectErrorType(error);
        console.error(`[ViolenceDetector] 🔍 Error Type: ${errorType.type}`);
        console.error('[ViolenceDetector] Error:', error instanceof Error ? error.message : String(error));
        
        let explanation = 'AI analysis failed';
        if (errorType.type === 'INSUFFICIENT_CREDITS') {
          explanation = 'AI analysis failed: Insufficient OpenAI credits. Please add credits to your account.';
        } else if (errorType.type === 'AUTHENTICATION_ERROR') {
          explanation = 'AI analysis failed: Invalid OpenAI API key. Check your OPENAI_API_KEY.';
        } else if (errorType.type === 'NETWORK_ERROR' || errorType.type === 'TIMEOUT') {
          explanation = 'AI analysis failed: Network issue. Check your internet connection.';
        } else if (errorType.type === 'RATE_LIMIT') {
          explanation = 'AI analysis failed: Rate limit exceeded. Please wait a moment.';
        }
        
        console.error(`[ViolenceDetector] ${'─'.repeat(80)}\n`);
        
        // If AI fails, return no violence (can't detect without AI)
        return {
          hasViolence: false,
          isClearViolence: false,
          confidence: 0.0,
          matches: [],
          explanation,
        };
      }
    }

    // If OpenAI not configured, return no violence
    console.warn('[ViolenceDetector] ⚠️ OpenAI not configured - cannot detect violence');
    return {
      hasViolence: false,
      isClearViolence: false,
      confidence: 0.0,
      matches: [],
      explanation: 'OpenAI not configured',
    };
  }

  /**
   * Analyze text with OpenAI with retry logic for network issues
   */
  private async analyzeWithAIRetry(
    text: string,
    maxRetries: number = 3
  ): Promise<Omit<ViolenceDetectionResult, 'isClearViolence' | 'matches'>> {
    let lastError: any = null;
    
    for (let attempt = 1; attempt <= maxRetries; attempt++) {
      try {
        if (attempt > 1) {
          console.log(`[ViolenceDetector] 🔄 Retry attempt ${attempt}/${maxRetries}...`);
          // Exponential backoff: 1s, 2s, 4s
          const delay = Math.min(1000 * Math.pow(2, attempt - 2), 5000);
          await new Promise(resolve => setTimeout(resolve, delay));
        }
        
        return await this.analyzeWithAI(text);
      } catch (error: any) {
        lastError = error;
        const errorType = this.detectErrorType(error);
        
        // Don't retry if it's not a retryable error
        if (!errorType.retryable) {
          console.error(`[ViolenceDetector] ❌ Non-retryable error (${errorType.type}), stopping retries`);
          throw error;
        }
        
        // If it's the last attempt, throw the error
        if (attempt >= maxRetries) {
          console.error(`[ViolenceDetector] ❌ All ${maxRetries} retry attempts failed`);
          throw error;
        }
        
        console.warn(`[ViolenceDetector] ⚠️ Retryable error (${errorType.type}), will retry...`);
      }
    }
    
    throw lastError || new Error('Unknown error in retry logic');
  }

  /**
   * Analyze text with OpenAI for violence detection
   * General implementation at Level 1 (Content Analysis Layer)
   */
  private async analyzeWithAI(
    text: string
  ): Promise<Omit<ViolenceDetectionResult, 'isClearViolence' | 'matches'>> {
    if (!this.openai) {
      throw new Error('OpenAI not configured');
    }

    const prompt = `You are a content moderation system. Analyze the following message for violent content.

Message: "${text}"

IMPORTANT GUIDELINES:
- Violent words like "hate", "kill", "murder", "attack" ARE violence, even as single words → hasViolence: true
- Full sentences with clear intent (like "I will kill you") ARE threats → hasViolence: true
- Flag the following as violence:
  * Single violent words: "hate", "kill", "murder", "attack", "violence", etc. → hasViolence: true
  * Direct threats: "I will kill you", "I hate you", etc. → hasViolence: true
  * Calls to violence: "Let's attack", "Kill them", etc. → hasViolence: true
- Do NOT flag:
  * Discussions about violence in news/stories (if clearly discussing, not threatening)
  * Educational content about violence
- Support multiple languages: English, Hebrew (עברית), Arabic, etc.
- For Hebrew: "אני אהרוג אותך" = "I will kill you" = threat
- For Hebrew: "שנאה" = "hate" = violence
- Be sensitive: It's better to flag potentially violent content than miss it

Determine:
1. Does this message contain actual violent content or threats? (true/false)
2. What category? Options: "physical_violence", "verbal_threat", "hate_speech", "none"
3. Confidence level (0.0 to 1.0):
   - Single violent words ("hate", "kill"): confidence 0.6-0.8
   - Clear threats ("I will kill you"): confidence 0.8-1.0
   - Ambiguous cases: confidence 0.5-0.7
4. Brief explanation of your decision (mention if it's a single word, language detected, etc.)

Respond ONLY in valid JSON format (no markdown, no code blocks):
{
  "hasViolence": boolean,
  "category": "string",
  "confidence": number,
  "explanation": "string"
}`;

    console.log(`[ViolenceDetector] 🤖 Sending to OpenAI (model: gpt-3.5-turbo, temp: 0.2)...`);
    const startTime = Date.now();
    
    try {
      // Add timeout to prevent hanging
      const timeoutPromise = new Promise((_, reject) => {
        setTimeout(() => reject(new Error('OpenAI API timeout after 30 seconds')), 30000);
      });
      
      const apiPromise = this.openai.chat.completions.create({
        model: 'gpt-3.5-turbo',
        messages: [
          {
            role: 'system',
            content: 'You are a content moderation system. Analyze messages for violent content. Flag violent words like "hate", "kill", "murder", "attack" even as single words. Be sensitive to violence - it\'s better to flag potentially violent content. Support multiple languages including Hebrew (עברית).',
          },
          {
            role: 'user',
            content: prompt,
          },
        ],
        temperature: 0.2, // Lower temperature for more consistent results
        max_tokens: 250,
      });
      
      const completion = await Promise.race([apiPromise, timeoutPromise]) as any;
      
      const responseTime = Date.now() - startTime;
      console.log(`[ViolenceDetector] 📥 OpenAI response received (${responseTime}ms)`);
      
      if (!completion || !completion.choices || !completion.choices[0]) {
        throw new Error('Invalid response from OpenAI: missing choices');
      }

      const responseText = completion.choices[0]?.message?.content || '{}';
      
      if (!responseText || responseText === '{}') {
        console.error(`[ViolenceDetector] ❌ Empty or invalid response from OpenAI!`);
        console.error(`[ViolenceDetector] Full completion object:`, JSON.stringify(completion, null, 2));
        throw new Error('OpenAI returned empty response');
      }
      
      console.log(`[ViolenceDetector] 📄 Raw AI response (${responseText.length} chars):`);
      console.log(`[ViolenceDetector] ${responseText.substring(0, 300)}${responseText.length > 300 ? '...' : ''}`);
      
      // Try to extract JSON from response (in case it's wrapped in markdown)
      let jsonText = responseText.trim();
      if (jsonText.startsWith('```json')) {
        jsonText = jsonText.replace(/```json\n?/g, '').replace(/```\n?/g, '').trim();
        console.log('[ViolenceDetector] 🔧 Removed markdown wrapper (```json)');
      } else if (jsonText.startsWith('```')) {
        jsonText = jsonText.replace(/```\n?/g, '').trim();
        console.log('[ViolenceDetector] 🔧 Removed markdown wrapper (```)');
      }
      
      const result = JSON.parse(jsonText);
      console.log(`[ViolenceDetector] ✅ Successfully parsed JSON response`);
      
      return {
        hasViolence: result.hasViolence || false,
        category: result.category || 'none',
        confidence: result.confidence || 0.5,
        explanation: result.explanation || 'No explanation provided',
      };
    } catch (error: any) {
      const responseTime = Date.now() - startTime;
      console.error(`[ViolenceDetector] ${'─'.repeat(80)}`);
      console.error(`[ViolenceDetector] ❌ OpenAI API call failed after ${responseTime}ms`);
      
      // Detect error type
      const errorType = this.detectErrorType(error);
      console.error(`[ViolenceDetector] 🔍 Error Type: ${errorType.type}`);
      console.error(`[ViolenceDetector] 📋 Error Message: ${error.message || String(error)}`);
      
      if (error.response) {
        const status = error.response.status;
        const statusText = error.response.statusText;
        const errorData = error.response.data;
        
        console.error(`[ViolenceDetector] 📊 HTTP Status: ${status} ${statusText}`);
        console.error(`[ViolenceDetector] 📄 Error Data:`, JSON.stringify(errorData, null, 2));
        
        // Specific error messages based on status
        if (status === 401) {
          console.error(`[ViolenceDetector] 🔑 ISSUE: Invalid API Key`);
          console.error(`[ViolenceDetector] 💡 Solution: Check your OPENAI_API_KEY in .env file`);
        } else if (status === 403) {
          console.error(`[ViolenceDetector] 🚫 ISSUE: API Key doesn't have access or account suspended`);
          console.error(`[ViolenceDetector] 💡 Solution: Check OpenAI account status and API key permissions`);
        } else if (status === 429) {
          const retryAfter = error.response.headers['retry-after'] || errorData?.retry_after;
          console.error(`[ViolenceDetector] ⏱️  ISSUE: Rate limit exceeded`);
          if (errorData?.error?.message?.includes('quota') || errorData?.error?.message?.includes('billing')) {
            console.error(`[ViolenceDetector] 💳 ISSUE: Insufficient credits/quota`);
            console.error(`[ViolenceDetector] 💡 Solution: Add credits to your OpenAI account`);
          } else {
            console.error(`[ViolenceDetector] 💡 Solution: Wait ${retryAfter || 'a few'} seconds and try again`);
          }
        } else if (status === 402) {
          console.error(`[ViolenceDetector] 💳 ISSUE: Payment required - No credits remaining`);
          console.error(`[ViolenceDetector] 💡 Solution: Add payment method or credits to OpenAI account`);
        } else if (status >= 500) {
          console.error(`[ViolenceDetector] 🌐 ISSUE: OpenAI server error (${status})`);
          console.error(`[ViolenceDetector] 💡 Solution: This is OpenAI's issue, wait and retry`);
        }
      } else if (error.code === 'ECONNREFUSED' || error.code === 'ENOTFOUND' || error.code === 'ETIMEDOUT') {
        console.error(`[ViolenceDetector] 🌐 ISSUE: Network connection problem`);
        console.error(`[ViolenceDetector] 💡 Solution: Check your internet connection`);
      } else if (error.message?.includes('timeout')) {
        console.error(`[ViolenceDetector] ⏱️  ISSUE: Request timeout`);
        console.error(`[ViolenceDetector] 💡 Solution: Network might be slow, or OpenAI is overloaded`);
      } else if (error instanceof SyntaxError) {
        console.error(`[ViolenceDetector] 📝 ISSUE: JSON parsing error`);
        console.error(`[ViolenceDetector] 💡 Solution: Check raw response above`);
      }
      
      console.error(`[ViolenceDetector] ${'─'.repeat(80)}`);
      throw error;
    }
  }

  /**
   * Detect the type of OpenAI API error
   */
  private detectErrorType(error: any): { type: string; retryable: boolean } {
    // Network errors - retryable
    if (error.code === 'ECONNREFUSED' || error.code === 'ENOTFOUND') {
      return { type: 'NETWORK_ERROR', retryable: true };
    }
    if (error.code === 'ETIMEDOUT' || error.message?.includes('timeout')) {
      return { type: 'TIMEOUT', retryable: true };
    }
    
    // API errors
    if (error.response) {
      const status = error.response.status;
      const errorData = error.response.data;
      
      // Authentication errors - NOT retryable
      if (status === 401 || status === 403) {
        return { type: 'AUTHENTICATION_ERROR', retryable: false };
      }
      
      // Payment/credit errors - NOT retryable
      if (status === 402) {
        return { type: 'INSUFFICIENT_CREDITS', retryable: false };
      }
      if (status === 429) {
        const message = errorData?.error?.message?.toLowerCase() || '';
        if (message.includes('quota') || message.includes('billing') || message.includes('credit')) {
          return { type: 'INSUFFICIENT_CREDITS', retryable: false };
        }
        return { type: 'RATE_LIMIT', retryable: true };
      }
      
      // Server errors - retryable
      if (status >= 500) {
        return { type: 'SERVER_ERROR', retryable: true };
      }
      
      // Other API errors
      return { type: `API_ERROR_${status}`, retryable: false };
    }
    
    // Unknown errors
    return { type: 'UNKNOWN_ERROR', retryable: false };
  }
}
