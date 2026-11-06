# CareerPilot | AI Job Search Assistant

A full-stack starter project built with React, Spring Boot, MySQL and the OpenAI API.

## Features
- Register and sign in (passwords are stored using BCrypt hashing).
- Paste resume text and receive an AI-powered review with strengths, skill gaps, ATS keywords and suitable roles.
- Generate role-specific interview questions and answer guidance.
- Generate a prioritized skill roadmap.
- Store resume analyses in MySQL and retrieve a user's analysis history through the API.
- Responsive React interface.

## Requirements
- Java 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8+ (or Docker Desktop)
- OpenAI API key for live AI responses

## 1. Start MySQL

From the project root:

```bash
docker compose up -d
```

This starts MySQL on `localhost:3306`, creates database `careerpilot`, and configures `root` / `root` for local development. Alternatively, create a MySQL database named `careerpilot` and update the backend environment variables.

## 2. Start the backend

Open a terminal in `backend` and set environment variables.

Windows PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="root"
$env:OPENAI_API_KEY="YOUR_OPENAI_API_KEY"
$env:OPENAI_MODEL="gpt-4o-mini"
./mvnw spring-boot:run
```

If Maven Wrapper is not present, use `mvn spring-boot:run` after installing Maven. The API runs at `http://localhost:8080`.

Without an OpenAI key, the AI endpoints return a setup message rather than live generated content.

## 3. Start the frontend

Open another terminal in `frontend`:

```bash
npm install
npm run dev
```

Open the Vite URL, normally `http://localhost:5173`.

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Create account |
| POST | `/api/auth/login` | Sign in |
| POST | `/api/career/analyze` | Analyze resume and save result |
| POST | `/api/career/interview-questions` | Generate interview questions |
| POST | `/api/career/skills` | Generate skill roadmap |
| GET | `/api/career/history/{userId}` | Get saved analyses |

### Example request: resume analysis

```json
{
  "userId": 1,
  "resumeText": "Java developer. Skills: Java, Spring Boot, MySQL..."
}
```

## Important production hardening

This is a learning/portfolio starter, not a production-ready authentication system. The demo authentication endpoints are open and the frontend stores the returned profile locally; add Spring Security authentication with JWT or secure server sessions, authorization checks for every user-owned record, rate limiting, request-size limits, HTTPS, secret management and stronger validation before deploying publicly. Do not commit API keys or production passwords. Resume text is sent to OpenAI when AI features are used; obtain user consent and review applicable data-retention/privacy requirements.
