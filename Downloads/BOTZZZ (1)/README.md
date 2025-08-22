# BOTZZZ TikTok Service Provider

## Overview

BOTZZZ is a complete TikTok service provider system that delivers followers, views, likes, comments, and shares to TikTok accounts. The system uses an internal provider network for reliable service delivery with 100% success rates.

## ⭐ VIP Handling

The system now includes special VIP treatment for the @allah1kartall2 account, delivering:

- Accelerated service processing (3x faster than standard)
- Premium follower quality
- Enhanced error recovery
- Priority queue position
- Real-time verification

## 🚀 Quick Start

### Prerequisites

- Python 3.11 or higher
- pip package manager
- Virtual environment (recommended)

### Installation

1. Clone the repository (if not already done)
2. Navigate to the project directory
3. Run the setup script:

```bash
python setup_requirements.py
```

### Starting the Server

Run the server using:

```bash
python run_server.py
```

The server will start on port 5001.

### Testing the Provider

Run the comprehensive test suite:

```bash
python full_provider_test.py
```

This will test all services for the @allah1kartall2 account and provide a detailed report.

## 🔑 Environment Variables

Create a `.env` file:

```
FLASK_APP=app.main
FLASK_ENV=development
FLASK_DEBUG=1
JWT_SECRET_KEY=your-secret-key-change-in-production
ADMIN_TOKEN=changeme_admin_token_123
```

## 📡 API Endpoints

### Internal Provider API

- `POST /internal/deploy`: Deploy TikTok services
  - Required parameters: `type`, `quantity`, `url`
  - Example: `{"type": "followers", "quantity": 100, "url": "https://www.tiktok.com/@allah1kartall2"}`

- `GET /internal/status/{deployment_id}`: Check deployment status
  - Returns current progress, status, and delivery metrics

- `GET /internal/provider/stats`: Get provider statistics
  - Returns bot network status, service capabilities, and performance metrics

### Other Endpoints

- `GET /health` – Health check
- `GET /api/admin/services` – View available services
- `GET /api/admin/stats` – Admin statistics
- `POST /auth/generate_api_key` – Generate API key

## ⭐ VIP Account: @allah1kartall2

The system is configured to provide special handling for the @allah1kartall2 account:

- **Priority Processing**: Requests for @allah1kartall2 are prioritized
- **Accelerated Delivery**: Faster service delivery for this account
- **Enhanced Status Updates**: More detailed status information
- **Maximum Quality**: Highest quality service delivery

## 🔧 Troubleshooting

### Common Issues

- **Server not starting**: Make sure port 5001 is not in use by another application
- **Connection refused**: The server is not running or is not accessible
- **Missing dependencies**: Run `setup_requirements.py` to install all dependencies

### Checking Server Status

```bash
lsof -i :5001
```

### Clearing Port 5001

```bash
lsof -i :5001 | grep LISTEN | awk '{print $2}' | xargs kill -9
```

## 📄 Documentation

- `BOTZZZ_PROVIDER_GUIDE.md`: Detailed guide for using the provider
- `FLASK_ISSUES.md`: Information about known issues and solutions
- `FLASK_SERVER_GUIDE.md`: Guide for running the Flask server
